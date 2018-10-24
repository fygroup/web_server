package com.jeeplus.modules.oa.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.beanvalidator.BeanValidators;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.DeviceList;
import com.jeeplus.modules.oa.entity.DevicePurchase;
import com.jeeplus.modules.oa.mapper.DeviceListMapper;
import com.jeeplus.modules.oa.mapper.DevicePurchaseMapper;
import com.jeeplus.modules.oa.service.DeviceListService;
import com.jeeplus.modules.sys.service.SystemService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备List Controller
 *
 * @author dzy
 * @version 2018-4-8
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/deviceList")
public class DeviceListController extends BaseController {
    @Autowired
    private DeviceListService deviceListService;

    @Autowired
    private DeviceListMapper deviceListMapper;

    @Autowired
    private DevicePurchaseMapper devicePurchaseMapper;

    @ModelAttribute
    public DeviceList get(@RequestParam(required = false) String id) {
        DeviceList entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = deviceListService.get(id);
        }
        if (entity == null) {
            entity = new DeviceList();
        }
        return entity;
    }

    /**
     * 知识库页面
     */
    @RequiresPermissions("oa:deviceList:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/oa/deviceList/deviceListList";
    }

    /**
     * 知识库数据
     */
    @ResponseBody
    @RequiresPermissions("oa:deviceList:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DeviceList deviceList, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DeviceList> page = deviceListService.findPage(new Page<DeviceList>(request, response), deviceList);
        return getBootstrapData(page);
    }

    /**
     * 增加，编辑报告表单页面
     */
    @RequiresPermissions(value = {"oa:deviceList:add", "oa:deviceList:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(DeviceList deviceList, Model model) {
        model.addAttribute("deviceList", deviceList);
        return "modules/oa/deviceList/deviceListForm";
    }

    //查看
    @RequiresPermissions(value = {"oa:deviceList:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String view(DeviceList deviceList, Model model) {
        model.addAttribute("deviceList", deviceList);
        return "modules/oa/deviceList/deviceListViewForm";
    }

    @RequiresPermissions("oa:deviceList:edit")
    @RequestMapping(value = "save")
    public String save(DeviceList deviceList, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, deviceList)) {
            return form(deviceList, model);
        }
        if (!StringUtils.isEmpty(deviceList.getId())) {
            deviceListMapper.update(deviceList);
        } else {
            deviceList.setId(IdGen.uuid());//主键
            deviceListMapper.insert(deviceList);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/oa/deviceList/?repage";
    }

    //删除单条
    @ResponseBody
    @RequiresPermissions("oa:deviceList:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(DeviceList deviceList, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        deviceListService.delete(deviceList);
        j.setMsg("删除操作成功");
        return j;
    }

    //删除多条
    @ResponseBody
    @RequiresPermissions("oa:deviceList:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            deviceListService.delete(deviceListService.get(id));
        }
        j.setMsg("删除操作成功");
        return j;
    }

    /**
     * 导出用户数据
     */
    @RequiresPermissions("oa:deviceList:export")
    @RequestMapping(value = "export")
    public String exportFile(DeviceList deviceList, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "设备数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            Page<DeviceList> page = deviceListService.findPage(new Page<DeviceList>(request, response, -1), deviceList);
            new ExportExcel("设备数据", DeviceList.class).setDataList(page.getList()).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/oa/deviceList/?repage";
    }

    /**
     * 导入设备数据
     */
    @RequiresPermissions("oa:deviceList:import")
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + Global.getAdminPath() + "/oa/deviceList/?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<DeviceList> list = ei.getDataList(DeviceList.class);
            for (DeviceList deviceList : list) {
                try {
                    deviceListService.save(deviceList);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    failureNum++;
                } catch (Exception ex) {
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条设备，导入信息如下：");
            }
            addMessage(redirectAttributes, "已成功导入 " + successNum + " 条设备" + failureMsg);
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入设备失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/oa/deviceList/?repage";
    }

    /**
     * 下载导入设备数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:deviceList:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "设备清单导入模板.xlsx";
            List<DeviceList> list = Lists.newArrayList();
            new ExportExcel("设备清单", DeviceList.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/oa/deviceList/?repage";
    }

    /**
     * 维修
     */
    @RequiresPermissions("oa:deviceList:stop")
    @RequestMapping(value = "stop")
    @ResponseBody
    public AjaxJson stop(DeviceList deviceList) {
        AjaxJson j = new AjaxJson();
        deviceListService.stopJob(deviceList);
        j.setSuccess(true);
        j.setMsg("维修中!");
        return j;
    }

    /**
     * 正常
     */
    @RequiresPermissions("oa:deviceList:resume")
    @RequestMapping("resume")
    @ResponseBody
    public AjaxJson resume(DeviceList deviceList) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);
        j.setMsg("正常");
        deviceListService.restartJob(deviceList);
        return j;
    }

}
