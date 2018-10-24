package com.jeeplus.modules.oa.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.MailList;;
import com.jeeplus.modules.oa.mapper.MailListMapper;
import com.jeeplus.modules.oa.service.MailListService;
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


@Controller
@RequestMapping(value = "${adminPath}/oa/mailList")
public class MailListController extends BaseController {
    @Autowired
    private MailListService mailListService;

    @Autowired
    private MailListMapper mailListMapper;

    @ModelAttribute
    public MailList get(@RequestParam(required = false) String id) {
        MailList entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = mailListService.get(id);
        }
        if (entity == null) {
            entity = new MailList();
        }
        return entity;
    }

    /**
     * 通讯录页面
     */
    @RequiresPermissions("oa:mailList:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/oa/mailList/mailListList";
    }

    /**
     * 通讯录数据
     */
    @ResponseBody
    @RequiresPermissions("oa:mailList:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(MailList mailList, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<MailList> page = mailListService.findPage(new Page<MailList>(request, response), mailList);
        return getBootstrapData(page);
    }

    /**
     * 增加，编辑表单页面
     */
    @RequiresPermissions(value = {"oa:mailList:add", "oa:mailList:edit"}, logical = Logical.OR)
    @RequestMapping(value = "form")
    public String form(MailList mailList, Model model) {
        model.addAttribute("mailList", mailList);
        return "modules/oa/mailList/mailListForm";
    }

    //查看
    @RequiresPermissions(value = {"oa:mailList:view"}, logical = Logical.OR)
    @RequestMapping(value = "view")
    public String view(MailList mailList, Model model) {
        model.addAttribute("mailList", mailList);
        return "modules/oa/mailList/mailListViewForm";
    }

    //新增，修改
    @RequiresPermissions(value={"oa:mailList:add", "oa:mailList:edit"},logical=Logical.OR)
    @ResponseBody
    @RequestMapping(value = "save")
    public AjaxJson save(MailList mailList, Model model, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        if(Global.isDemoMode()){
            j.setSuccess(false);
            j.setMsg("演示模式，不允许操作!");
            return j;
        }

        if (!beanValidator(model, mailList)){
            j.setSuccess(false);
            j.setMsg("非法参数！");
            return j;
        }
        if (!StringUtils.isEmpty(mailList.getId())) {
            mailListMapper.update(mailList);
        } else {
            mailList.setId(IdGen.uuid());//主键
            String audUser = UserUtils.getUser().getId();//获得当前用户id
            mailList.setEstablish(audUser);
            Date appTime = new Date(); //获取当前系统时间
            mailList.setEstablishTime(appTime);//当前时间
            mailListMapper.insert(mailList);
        }
        addMessage(redirectAttributes, "操作成功");
        j.setSuccess(true);
        j.setMsg("操作成功");
        return j;
    }

    //删除单条
    @ResponseBody
    @RequiresPermissions("oa:mailList:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(MailList mailList, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        mailListService.delete(mailList);
        j.setMsg("删除操作成功");
        return j;
    }

    //删除多条
    @ResponseBody
    @RequiresPermissions("oa:mailList:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            mailListService.delete(mailListService.get(id));
        }
        j.setMsg("删除操作成功");
        return j;
    }


}
