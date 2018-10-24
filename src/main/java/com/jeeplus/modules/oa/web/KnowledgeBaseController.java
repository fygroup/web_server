package com.jeeplus.modules.oa.web;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.oa.entity.KnowledgeBase;
import com.jeeplus.modules.oa.mapper.KnowledgeBaseMapper;
import com.jeeplus.modules.oa.service.KnowledgeBaseService;
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
import java.util.Map;

/**
 * 知识库Controller
 * @author dzy
 * @version 2018-4-8
 */

@Controller
@RequestMapping(value = "${adminPath}/oa/knowledgeBase")
public class KnowledgeBaseController extends BaseController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @ModelAttribute
    public KnowledgeBase get(@RequestParam(required=false) String id) {
        KnowledgeBase entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = knowledgeBaseService.get(id);
        }
        if (entity == null){
            entity = new KnowledgeBase();
        }
        return entity;
    }

    /**
     * 知识库页面
     */
    @RequiresPermissions("oa:knowledgeBase:list")
    @RequestMapping(value = {"list", ""})
    public String list() {
        return "modules/oa/knowledgeBase/knowledgeBaseList";
    }

    /**
     * 知识库数据
     */
    @ResponseBody
    @RequiresPermissions("oa:knowledgeBase:list")
    @RequestMapping(value = "data")
    public Map<String, Object> data(KnowledgeBase knowledgeBase, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<KnowledgeBase> page = knowledgeBaseService.findPage(new Page<KnowledgeBase>(request, response), knowledgeBase);
        return getBootstrapData(page);
    }

    /**
     * 增加，编辑报告表单页面
     */
    @RequiresPermissions(value={"oa:knowledgeBase:add","oa:knowledgeBase:edit"},logical= Logical.OR)
    @RequestMapping(value = "form")
    public String form(KnowledgeBase knowledgeBase,Model model) {
        model.addAttribute("knowledgeBase", knowledgeBase);
        return "modules/oa/knowledgeBase/knowledgeBaseForm";
    }

    //查看
    @RequiresPermissions(value={"oa:knowledgeBase:view"},logical= Logical.OR)
    @RequestMapping(value = "view")
    public String view(KnowledgeBase knowledgeBase,Model model) {
        model.addAttribute("knowledgeBase", knowledgeBase);
        return "modules/oa/knowledgeBase/knowledgeBaseViewForm";
    }

    @RequiresPermissions("oa:knowledgeBase:edit")
    @RequestMapping(value = "save")
    public String save(KnowledgeBase knowledgeBase, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, knowledgeBase)){
            return form(knowledgeBase, model);
        }
        if (!StringUtils.isEmpty(knowledgeBase.getId())){
            knowledgeBaseMapper.update(knowledgeBase);
        }else {
            knowledgeBase.setId(IdGen.uuid());//主键
            String userId = UserUtils.getUser().getId();//获得当前用户id
            knowledgeBase.setUserId(userId);
            knowledgeBaseMapper.insert(knowledgeBase);
        }
        addMessage(redirectAttributes, "操作成功");
        return "redirect:"+ Global.getAdminPath()+"/oa/knowledgeBase/?repage";
    }

    //删除单条
    @ResponseBody
    @RequiresPermissions("oa:knowledgeBase:del")
    @RequestMapping(value = "delete")
    public AjaxJson delete(KnowledgeBase knowledgeBase, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        knowledgeBaseService.delete(knowledgeBase);
        j.setMsg("删除操作成功");
        return j;
    }

    //删除多条
    @ResponseBody
    @RequiresPermissions("oa:knowledgeBase:del")
    @RequestMapping(value = "deleteAll")
    public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
        AjaxJson j = new AjaxJson();
        String idArray[] =ids.split(",");
        for(String id : idArray){
            knowledgeBaseService.delete(knowledgeBaseService.get(id));
        }
        j.setMsg("删除操作成功");
        return j;
    }

}
