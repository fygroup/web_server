
package com.jeeplus.modules.sys.web;


import com.jeeplus.core.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/sys/templates")
public class TemplatesController extends BaseController {




	@RequestMapping(value = "topoEditor")
	public String topoEditor( HttpServletRequest request, Model model) {

		return "modules/sys/templates/templatesTopoEditor";
	}

	@RequestMapping(value = "singleIndicator")
	public String singleIndicator( HttpServletRequest request, Model model) {

		return "modules/sys/templates/editSingleIndicator";
	}


	@RequestMapping(value = "addTemplate")
	public String addTemplate( HttpServletRequest request, Model model) {

		return "modules/sys/templates/templatesAddTemplate";
	}


}