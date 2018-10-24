
package com.jeeplus.modules.resourcetype.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Pie;
import com.google.gson.Gson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.modules.echarts.entity.other.TestPieClass;
import com.jeeplus.modules.echarts.service.other.TestPieClassService;
import com.jeeplus.modules.resourcetype.entity.ResponseTypeNumStatistics;
import com.jeeplus.modules.sys.entity.Office;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.config.Global;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.resourcetype.entity.ResourceType;
import com.jeeplus.modules.resourcetype.service.ResourceTypeService;

/**
 * 资源类型Controller
 * @author le
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/resourcetype/resourceType")
public class ResourceTypeController extends BaseController {

	@Autowired
	private ResourceTypeService resourceTypeService;

	@Autowired
	private TestPieClassService testPieClassService;
	
	@ModelAttribute
	public ResourceType get(@RequestParam(required=false) String id) {
		ResourceType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = resourceTypeService.get(id);
		}
		if (entity == null){
			entity = new ResourceType();
		}
		return entity;
	}



	/**
	 * 资源类型列表页面
	 */
	@RequiresPermissions("resourcetype:resourceType:list")
	@RequestMapping(value = {"list", ""})
	public String list(ResourceType resourceType, @ModelAttribute("parentIds") String parentIds, HttpServletRequest request, HttpServletResponse response, Model model) {

		if(StringUtils.isNotBlank(parentIds)){
			model.addAttribute("parentIds", parentIds);
		}

		return "modules/resourcetype/resourceTypeList";
	}


	@ResponseBody
	@RequestMapping("option")
	public GsonOption getOption(){
		GsonOption option = new GsonOption();
		option.title().text("资源数量").subtext("");
		//option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage, new MagicType(Magic.pie, Magic.funnel)
		option.toolbox().show(true).feature(Tool.mark, new MagicType(Magic.pie, Magic.funnel)
				.option(new MagicType.Option().funnel(
						new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548))));

		List<ResponseTypeNumStatistics> list =resourceTypeService.numStatistics();
		ArrayList arrayList = new ArrayList();
		for(ResponseTypeNumStatistics p:list){
			if(p.getResourceType()!=null) {
				arrayList.add(new PieData(p.getResourceType().getName(), p.getNum()));
			}
		}
		Pie pie = new Pie().name("资源类型比");
		pie.setData(arrayList);
		option.series(pie);
		return option;
	}



	@RequestMapping(value = {"statisticsList", ""})
	public String list() {
		return "modules/resourcetype/typeStatistics";
	}


	@ResponseBody
	@RequestMapping(value = "statisticsData")
	public Map<String, Object> data(ResponseTypeNumStatistics responseTypeNumStatistics, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ResponseTypeNumStatistics> page = testPieClassService.findPage(new Page<ResponseTypeNumStatistics>(request, response), responseTypeNumStatistics);
		return getBootstrapData(page);
	}









	/**
	 * 查看，增加，编辑资源类型表单页面
	 */
	@RequiresPermissions(value={"resourcetype:resourceType:view","resourcetype:resourceType:add","resourcetype:resourceType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ResourceType resourceType, Model model) {
		if (resourceType.getParent()!=null && StringUtils.isNotBlank(resourceType.getParent().getId())){
			resourceType.setParent(resourceTypeService.get(resourceType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(resourceType.getId())){
				ResourceType resourceTypeChild = new ResourceType();
				resourceTypeChild.setParent(new ResourceType(resourceType.getParent().getId()));
				List<ResourceType> list = resourceTypeService.findList(resourceType); 
				if (list.size() > 0){
					resourceType.setSort(list.get(list.size()-1).getSort());
					if (resourceType.getSort() != null){
						resourceType.setSort(resourceType.getSort() + 30);
					}
				}
			}
		}
		if (resourceType.getSort() == null){
			resourceType.setSort(30);
		}
		model.addAttribute("resourceType", resourceType);
		return "modules/resourcetype/resourceTypeForm";
	}

	/**
	 * 保存资源类型
	 */
	@RequiresPermissions(value={"resourcetype:resourceType:add","resourcetype:resourceType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ResourceType resourceType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, resourceType)){
			return form(resourceType, model);
		}

		//新增或编辑表单保存
		resourceTypeService.save(resourceType);//保存
		redirectAttributes.addFlashAttribute("parentIds", resourceType.getParentIds());
		addMessage(redirectAttributes, "保存资源类型成功");
		return "redirect:"+Global.getAdminPath()+"/resourcetype/resourceType/list?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<ResourceType> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return resourceTypeService.getChildren(parentId);
	}
	
	/**
	 * 删除资源类型
	 */
	@ResponseBody
	@RequiresPermissions("resourcetype:resourceType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ResourceType resourceType, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		resourceTypeService.delete(resourceType);
		j.setSuccess(true);
		j.setMsg("删除资源类型成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataSelect")
	public List<Map<String, Object>> treeDataSelect(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> list = resourceTypeService.findList(new ResourceType());
		for (int i=0; i<list.size(); i++){
			ResourceType e = list.get(i);
			if(!e.getParentId().equals("0")){
				if(e.getParent().getCode().equals("4")||e.getParent().getCode().equals("6")||e.getParent().getCode().equals("7")){
					continue;
				}
			}
			if(e.getCode().equals("4")||e.getCode().equals("6")||e.getCode().equals("7")){
                continue;
			}
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", false);
					map.put("isParent", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}

				mapList.add(map);
			}
		}
		return mapList;
	}




	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> list = resourceTypeService.findList(new ResourceType());
		for (int i=0; i<list.size(); i++){
			ResourceType e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", false);
					map.put("isParent", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}

				mapList.add(map);
			}
		}
		return mapList;
	}


	/**
	 * 中间件
	 * @param extId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataByCode")
	public List<Map<String, Object>> treeDataMiddlewareForm(@RequestParam(required=false) String extId, HttpServletResponse response,String code) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> list = resourceTypeService.findListByCode(code);
		for (int i=0; i<list.size(); i++){
			ResourceType e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", false);
					map.put("isParent", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}

				mapList.add(map);
			}
		}
		return mapList;
	}





	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "bootstrapTreeData")
	public List<Map<String, Object>> bootstrapTreeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
													   @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> roots = resourceTypeService.getChildren("0");
		for(ResourceType root:roots){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", root.getId());
			map.put("name", root.getName());
			map.put("level", 1);
			deepTree(map, root);
			mapList.add(map);
		}
		return mapList;
	}


	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "bootstrapTreeDataBySelect")
	public List<Map<String, Object>> bootstrapTreeDataBySelect(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
													   @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> roots = resourceTypeService.getChildrenOfSelect("0");
		for(ResourceType root:roots){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", root.getId());
			map.put("name", root.getName());
			map.put("level", 1);
			deepTree(map, root);
			mapList.add(map);
		}
		return mapList;
	}


	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "bootstrapTreeDataByCode")
	public List<Map<String, Object>> bootstrapTreeDataByCode(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,String code,
													   @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> roots = resourceTypeService.findParentListByCode(code);
		for(ResourceType root:roots){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", root.getId());
			map.put("name", root.getName());
			map.put("level", 1);
			deepTree(map, root);
			mapList.add(map);
		}
		return mapList;
	}


	public void deepTree(Map<String, Object> map, ResourceType resourceType){

		map.put("text", resourceType.getName());
		List<Map<String, Object>> arra = new ArrayList<Map<String, Object>>();
		for(ResourceType child:resourceTypeService.getChildren(resourceType.getId())){
			Map<String, Object> childMap = Maps.newHashMap();
			childMap.put("id", child.getId());
			childMap.put("name", child.getName());
			arra.add(childMap);
			deepTree(childMap, child);
		}
		if(arra.size() >0){
			map.put("children", arra);
		}
	}
}