/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.applicationindicator.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.applicationindicator.entity.ApplicationIndicator;
import com.jeeplus.modules.applicationindicator.service.ApplicationIndicatorService;

/**
 * 应用指标Controller
 * @author le
 * @version 2017-12-12
 */
@Controller
@RequestMapping(value = "${adminPath}/applicationindicator/applicationIndicator")
public class ApplicationIndicatorController extends BaseController {

    @Autowired
    private ApplicationIndicatorService applicationIndicatorService;

    @ModelAttribute
    public ApplicationIndicator get(@RequestParam(required = false) String id) {
        ApplicationIndicator entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = applicationIndicatorService.get(id);
        }
        if (entity == null) {
            entity = new ApplicationIndicator();
        }
        return entity;
    }

    /**
     * 应用指标列表页面
     */
    @RequiresPermissions("applicationindicator:applicationIndicator:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/applicationindicator/applicationIndicatorList";
    }

    /**
     * 应用指标列表数据
     */
    @ResponseBody
    @RequiresPermissions("applicationindicator:applicationIndicator:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(ApplicationIndicator applicationIndicator, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<ApplicationIndicator> page = applicationIndicatorService.findPage(new Page<ApplicationIndicator>(request, response), applicationIndicator);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑应用指标表单页面
     */
    @RequiresPermissions(value = {"applicationindicator:applicationIndicator:view", "applicationindicator:applicationIndicator:add", "applicationindicator:applicationIndicator:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(ApplicationIndicator applicationIndicator, Model model) {
        model.addAttribute("applicationIndicator", applicationIndicator);
        if (StringUtils.isBlank(applicationIndicator.getId())) {//如果ID是空为添加
            model.addAttribute("isAdd", true);
        }
        return "modules/applicationindicator/applicationIndicatorForm";
    }

    /**
     * 保存应用指标
     */
    @RequiresPermissions(value = {"applicationindicator:applicationIndicator:add", "applicationindicator:applicationIndicator:edit"}, logical = Logical.OR)
    @RequestMapping(value = "save")
    public String save(ApplicationIndicator applicationIndicator, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (!beanValidator(model, applicationIndicator)) {
            return form(applicationIndicator, model);
        }
        //新增或编辑表单保存
        applicationIndicatorService.save(applicationIndicator);//保存
        addMessage(redirectAttributes, "保存应用指标成功");
        return "redirect:" + Global.getAdminPath() + "/applicationindicator/applicationIndicator/?repage";
    }

    /**
     * 删除应用指标
     */
    @ResponseBody
    @RequiresPermissions("applicationindicator:applicationIndicator:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(ApplicationIndicator applicationIndicator, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        applicationIndicatorService.delete(applicationIndicator);
        j.setMsg("删除应用指标成功");
        return j;
    }

    /**
     * 批量删除应用指标
     */
    @ResponseBody
    @RequiresPermissions("applicationindicator:applicationIndicator:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            applicationIndicatorService.delete(applicationIndicatorService.get(id));
        }
        j.setMsg("删除应用指标成功");
        return j;
    }

    /**
     * 导出excel文件
     */
    @ResponseBody
    @RequiresPermissions("applicationindicator:applicationIndicator:export")
    @RequestMapping(value = "export", method = RequestMethod.POST)
    public AjaxJson exportFile(ApplicationIndicator applicationIndicator, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        try {
            String fileName = "应用指标" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<ApplicationIndicator> page = applicationIndicatorService.findPage(new Page<ApplicationIndicator>(request, response, -1), applicationIndicator);
            new ExportExcel("应用指标", ApplicationIndicator.class).setDataList(page.getList()).write(response, fileName).dispose();
            j.setSuccess(true);
            j.setMsg("导出成功！");
            return j;
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("导出应用指标记录失败！失败信息：" + e.getMessage());
        }
        return j;
    }

    /**
     * 导入Excel数据
     */
    @RequiresPermissions("applicationindicator:applicationIndicator:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<ApplicationIndicator> list = ei.getDataList(ApplicationIndicator.class);
            for (ApplicationIndicator applicationIndicator : list) {
                try {
                    applicationIndicatorService.save(applicationIndicator);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条应用指标记录。");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条应用指标记录" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入应用指标失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/applicationindicator/applicationIndicator/?repage";
    }

    /**
     * 下载导入应用指标数据模板
     */
    @RequiresPermissions("applicationindicator:applicationIndicator:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "应用指标数据导入模板.xlsx";
            List<ApplicationIndicator> list = Lists.newArrayList();
            new ExportExcel("应用指标数据", ApplicationIndicator.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/applicationindicator/applicationIndicator/?repage";
    }

}