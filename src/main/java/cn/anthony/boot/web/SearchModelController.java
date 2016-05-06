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

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.SearchModelService;
import cn.anthony.boot.util.ControllerUtil;

@Controller
@RequestMapping(value = "/searchModel")
public class SearchModelController extends GenericController<SearchModel> {
    @Resource
    SearchModelService service;

    @Override
    public SearchModel init(Model m) {
	return new SearchModel();
    }

    @Override
    GenericService<SearchModel> getService() {
	return this.service;
    }

    @Override
    protected String getListView() {
	return "/patient/searchList";
    }

    @Override
    protected String getIndexView() {
	return "/patient/searchList";
    }

    @Override
    protected String getFormView() {
	return "/searchModel/form";
    }

    @Override
    String deleteHint(SearchModel t) {
	return "成功删除：" + t.toString();
    }

    @ModelAttribute("pageRequest")
    SearchModelSearch initPageRquest() {
	return new SearchModelSearch();
    }

    @RequestMapping(value = { "/search", "/list", "/listPage" })
    public String listPage(@ModelAttribute("pageRequest") SearchModelSearch pr, @QuerydslPredicate(root = SearchModel.class) Predicate predicate,
	    @PageableDefault Pageable pageable, Model m, @RequestParam MultiValueMap<String, String> parameters, HttpServletRequest request) {
	System.out.println(predicate);
	Page<SearchModel> page = service.find(predicate, pageable);
	ControllerUtil.setPageVariables(m, page);
	m.addAttribute("pageRequest", pr);
	return getListView();
    }

    @RequestMapping(value = { "/initSearch" })
    public String searchForm() {
	return "/searchModel/search";
    }


}
