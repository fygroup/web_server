
package com.jeeplus.modules.topo.web;

import com.jeeplus.core.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拓扑图Controller
 */
@Controller
public class TopoController extends BaseController{

	


	/**
	 * 拓扑图列表首页
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/topo")
	public String statePerception(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		return "modules/topo/topoIndex";


	}

	/**
	 * 拓扑图首页
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/topoIndex")
	public String topoIndex(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		return "modules/topo/topoList";

	}

	/**
	 * 拓扑图首页
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/topoEdit")
	public String topoEdit(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		return "modules/topo/topoEdit";

	}

	/**
	 * 拓扑图首页
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/addTopo")
	public String addTopo(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		return "modules/topo/addTopo";

	}


}
