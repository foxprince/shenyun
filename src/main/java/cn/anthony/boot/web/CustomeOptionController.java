package cn.anthony.boot.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.querydsl.core.types.Predicate;

import cn.anthony.boot.domain.CustomeOption;
import cn.anthony.boot.service.CustomeOptionService;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.util.Constant;
import cn.anthony.boot.util.ControllerUtil;

@Controller
@RequestMapping(value = "/customeOption")
public class CustomeOptionController extends GenericController<CustomeOption> {
	@Resource
	CustomeOptionService service;

	@Override
	public CustomeOption init(Model m) {
		return new CustomeOption();
	}

	@Override
	GenericService<CustomeOption> getService() {
		return this.service;
	}

	@Override
	protected String getListView() {
		return "/patient/optionList";
	}

	@Override
	protected String getIndexView() {
		return "/patient/optionList";
	}

	@Override
	protected String getFormView() {
		return "/patient/optionForm";
	}

	@ModelAttribute
	public void fullSearchForm(Model m) {
		m.addAttribute("patientOptions", Constant.patientKeyMap.values());
		m.addAttribute("frontPageOptions", Constant.frontPageKeyMap.values());
		m.addAttribute("inOptions", Constant.inKeyMap.values());
		m.addAttribute("operationOptions", Constant.operKeyMap.values());
		m.addAttribute("outOptions", Constant.outKeyMap.values());
	}

	@Override
	String deleteHint(CustomeOption t) {
		return "成功删除：" + t.toString();
	}

	@RequestMapping(value = { "/list", "/listPage" })
	public String listPage(@QuerydslPredicate(root = CustomeOption.class) Predicate predicate, @PageableDefault Pageable pageable,
			Model m, @RequestParam MultiValueMap<String, String> parameters, HttpServletRequest request) {
		Page<CustomeOption> page = service.find(predicate, pageable);
		ControllerUtil.setPageVariables(m, page);
		return getListView();
	}
}
