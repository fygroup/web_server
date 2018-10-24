package com.jeeplus.modules.oa.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.beanvalidator.BeanValidators;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
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
import com.jeeplus.modules.oa.service.DevicePurchaseService;
import com.jeeplus.modules.sys.utils.UserUtils;
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
 * 设备采购 Controller
 *
 * @author dzy
 * @version 2018-4-11
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/devicePurchase")
public class DevicePurchaseController extends BaseController {
    @Autowired
    private DevicePurchaseService devicePurchaseService;

    @Autowired
    private DevicePurchaseMapper devicePurchaseMapper;

    @Autowired
    private DeviceListMapper deviceListMapper;


    @ModelAttribute
    public DevicePurchase get(@RequestParam(required = false) String id) {
        DevicePurchase entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = devicePurchaseService.get(id);
        }
        if (entity == null) {
            entity = new DevicePurchase();
        }
        return entity;
    }

    /**
     * 设备List页面
     */
    @RequiresPermissions("oa:devicePurchase:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/oa/devicePurchase/devicePurchaseList";
    }

    /**
     * 设备数据
     */
    @ResponseBody
    @RequiresPermissions("oa:devicePurchase:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DevicePurchase devicePurchase, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DevicePurchase> page = devicePurchaseService.findPage(new Page<DevicePurchase>(request, response), devicePurchase);
        return getBootstrapData(page);
    }

    /**
     * 增加，编辑表单页面
     */
    @RequiresPermissions(value = {"oa:devicePurchase:add", "oa:devicePurchase:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(DevicePurchase devicePurchase, Model model) {
        model.addAttribute("devicePurchase", devicePurchase);
        return "modules/oa/devicePurchase/devicePurchaseForm";
    }

    //查看
    @RequiresPermissions(value = {"oa:devicePurchase:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String view(DevicePurchase devicePurchase, Model model) {
        model.addAttribute("devicePurchase", devicePurchase);
        return "modules/oa/devicePurchase/devicePurchaseViewForm";
    }

    @RequiresPermissions("oa:devicePurchase:edit")
    @RequestMapping(value = "save")
    public String save(DevicePurchase devicePurchase, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, devicePurchase)) {
            return form(devicePurchase, model);
        }
        if (!StringUtils.isEmpty(devicePurchase.getId())) {
            devicePurchaseMapper.update(devicePurchase);
        } else {
            devicePurchase.setId(IdGen.uuid());//主键
            devicePurchaseMapper.insert(devicePurchase);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

    //删除单条
    @ResponseBody
    @RequiresPermissions("oa:devicePurchase:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(DevicePurchase devicePurchase, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        devicePurchaseService.delete(devicePurchase);
        j.setMsg("删除操作成功");
        return j;
    }

    //删除多条
    @ResponseBody
    @RequiresPermissions("oa:devicePurchase:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            devicePurchaseService.delete(devicePurchaseService.get(id));
        }
        j.setMsg("删除操作成功");
        return j;
    }

    //审核
    @RequiresPermissions(value = {"oa:devicePurchase:audit"}, logical = Logical.OR)
    @RequestMapping(value = "audit")
    public String audit(DevicePurchase devicePurchase, Model model, RedirectAttributes redirectAttributes) {
        String audUser = UserUtils.getUser().getId();//获得当前用户id
        devicePurchase.setAudUser(audUser);
        devicePurchase.setIsflag("1");
        devicePurchaseMapper.update(devicePurchase);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

    //下单
    @RequiresPermissions(value = {"oa:devicePurchase:order"}, logical = Logical.OR)
    @RequestMapping(value = "order")
    public String order(DevicePurchase devicePurchase, Model model, RedirectAttributes redirectAttributes) {
        devicePurchase.setIsorder("1");
        Date ordTime = new Date(); //获取当前系统时间
        devicePurchase.setOrdTime(ordTime);//当前时间
        devicePurchaseMapper.update(devicePurchase);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

    //到货
    @RequiresPermissions(value = {"oa:devicePurchase:arrival"}, logical = Logical.OR)
    @RequestMapping(value = "arrival")
    public String arrival(DevicePurchase devicePurchase, Model model, RedirectAttributes redirectAttributes) {
        devicePurchase.setIsarrival("1");
        Date arriTime = new Date(); //获取当前系统时间
        devicePurchase.setArriTime(arriTime);//当前时间
        devicePurchaseMapper.update(devicePurchase);

        DeviceList deviceList = new DeviceList();
        deviceList.setId(IdGen.uuid());//主键
        deviceList.setDevName(devicePurchase.getDevName());//设备名称
        deviceList.setDevMoney(devicePurchase.getPurMoney());//设备价格
        deviceList.setDevSource(devicePurchase.getDevSource());//设备来源
        deviceList.setIsUse("0");
        deviceList.setState("0");
        deviceListMapper.insert(deviceList);//添加到设备清单表

        addMessage(redirectAttributes, "操作成功");
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

    /**
     * 导入设备数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:devicePurchase:import")
    @RequestMapping(value = "importIn", method = RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
        }
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<DevicePurchase> list = ei.getDataList(DevicePurchase.class);
            for (DevicePurchase devicePurchase : list) {
                try {
                    devicePurchaseService.save(devicePurchase);
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
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

    /**
     * 下载导入设备数据模板
     *
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("oa:devicePurchase:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "设备采购导入模板.xlsx";
            List<DevicePurchase> list = Lists.newArrayList();
            new ExportExcel("设备采购清单", DevicePurchase.class, 1).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + Global.getAdminPath() + "/oa/devicePurchase/?repage";
    }

}
