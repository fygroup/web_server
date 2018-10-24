package com.jeeplus.modules.oa.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.DeviceApply;
import com.jeeplus.modules.oa.entity.DevicePurchase;
import com.jeeplus.modules.oa.mapper.DeviceApplyMapper;
import com.jeeplus.modules.oa.mapper.DevicePurchaseMapper;
import com.jeeplus.modules.oa.service.DeviceApplyService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 设备List Controller
 * @author dzy
 * @version 2018-4-8
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/deviceApply")
public class DeviceApplyController extends BaseController {
    @Autowired
    private DeviceApplyService deviceApplyService;

    @Autowired
    private DeviceApplyMapper deviceApplyMapper;

    @Autowired
    private DevicePurchaseMapper devicePurchaseMapper;

    @ModelAttribute
    public DeviceApply get(@RequestParam(required=false) String id) {
        DeviceApply entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = deviceApplyService.get(id);
        }
        if (entity == null){
            entity = new DeviceApply();
        }
        return entity;
    }

    /**
     * 知识库页面
     */
    @RequiresPermissions("oa:deviceApply:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/oa/deviceApply/deviceApplyList";
    }

    /**
     * 知识库数据
     */
    @ResponseBody
    @RequiresPermissions("oa:deviceApply:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(DeviceApply deviceApply, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<DeviceApply> page = deviceApplyService.findPage(new Page<DeviceApply>(request, response), deviceApply);
        return getBootstrapData(page);
    }

    /**
     * 增加，编辑报告表单页面
     */
    @RequiresPermissions(value={"oa:deviceApply:add","oa:deviceApply:edit"},logical= Logical.OR)
    @RequestMapping(value = "form")
    public String form(DeviceApply deviceApply, Model model) {
        model.addAttribute("deviceApply", deviceApply);
        return "modules/oa/deviceApply/deviceApplyForm";
    }

    //查看
    @RequiresPermissions(value={"oa:deviceApply:view"},logical= Logical.OR)
    @RequestMapping(value = "view")
    public String view(DeviceApply deviceApply, Model model) {
        model.addAttribute("deviceApply", deviceApply);
        return "modules/oa/deviceApply/deviceApplyViewForm";
    }

    //修改
    @RequiresPermissions("oa:deviceApply:edit")
    @RequestMapping(value = "save")
    public String save(DeviceApply deviceApply, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, deviceApply)){
            return form(deviceApply, model);
        }
        if (!StringUtils.isEmpty(deviceApply.getId())){
            deviceApplyMapper.update(deviceApply);
        }else {
            deviceApply.setId(IdGen.uuid());//主键
            Date appTime = new Date(); //获取当前系统时间
            deviceApply.setAppTime(appTime);//当前时间
            deviceApplyMapper.insert(deviceApply);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:"+ Global.getAdminPath()+"/oa/deviceApply/?repage";
    }

    //删除单条
    @ResponseBody
    @RequiresPermissions("oa:deviceApply:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(DeviceApply deviceApply, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        deviceApplyService.delete(deviceApply);
        j.setMsg("删除操作成功");
        return j;
    }

    //删除多条
    @ResponseBody
    @RequiresPermissions("oa:deviceApply:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] =ids.split(",");
        for(String id : idArray){
            deviceApplyService.delete(deviceApplyService.get(id));
        }
        j.setMsg("删除操作成功");
        return j;
    }

    //审核
    @RequiresPermissions(value={"oa:deviceApply:audit"},logical= Logical.OR)
    @RequestMapping(value = "audit")
    public String audit(DeviceApply deviceApply, Model model, RedirectAttributes redirectAttributes) {
        String audUser = UserUtils.getUser().getId();//获得当前用户id
        deviceApply.setAudUser(audUser);
        deviceApply.setIsaudit("1");
        deviceApplyMapper.update(deviceApply);

        DevicePurchase devicePurchase = new DevicePurchase();
        devicePurchase.setId(IdGen.uuid());//主键
        devicePurchase.setDevName(deviceApply.getDeviceId());//设备名称
        devicePurchaseMapper.insert(devicePurchase);

        addMessage(redirectAttributes, "操作成功");
        return "redirect:"+ Global.getAdminPath()+"/oa/deviceApply/?repage";
    }

    //交付
    @RequiresPermissions(value={"oa:deviceApply:delivery"},logical= Logical.OR)
    @RequestMapping(value = "delivery")
    public String delivery(DeviceApply deviceApply, Model model, RedirectAttributes redirectAttributes) {
        deviceApply.setIsdelivery("1");
        deviceApplyMapper.update(deviceApply);
        addMessage(redirectAttributes, "操作成功");
        return "redirect:"+ Global.getAdminPath()+"/oa/deviceApply/?repage";
    }

}
