package cn.anthony.boot.web;

import java.lang.reflect.Field;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.service.SearchModelService;
import cn.anthony.boot.util.ControllerUtil;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.StringTools;

@Controller
@RequestMapping(value = "/patient")
@SessionAttributes(value = { "pageRequest" })
public class PatientController extends GenericController<Patient> {
    @Resource
    PatientService service;
    @Resource
    SearchModelService smService;
    @Override
    public Patient init(Model m) {
	return new Patient();
    }

    @Override
    GenericService<Patient> getService() {
	return this.service;
    }

    @Override
    protected String getListView() {
	return "/patient/list";
    }

    @Override
    protected String getIndexView() {
	return "/patient/index";
    }

    @Override
    protected String getFormView() {
	return "/patient/form";
    }

    @Override
    String updateHint(Patient t) {
	return "成功：" + t.getName();
    }

    @Override
    String deleteHint(Patient t) {
	return "成功删除：" + t.getName();
    }

    @ModelAttribute("pageRequest")
    PatientSearch initPageRquest() {
	return new PatientSearch();
    }

    @RequestMapping(value = { "/search", "/list", "/listPage" })
    public String listPage(@ModelAttribute("pageRequest") PatientSearch ps, @QuerydslPredicate(root = Patient.class) Predicate predicate,
	    @PageableDefault Pageable pageable, Model m, @RequestParam MultiValueMap<String, String> parameters, HttpServletRequest request) {
	String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	if (path.endsWith("list"))// 清空搜索历史
	    ps = new PatientSearch();
	if (ps.minAge != null)
	    predicate = QPatient.patient.age.goe(ps.minAge).and(predicate);
	if (ps.maxAge != null)
	    predicate = QPatient.patient.age.lt(ps.maxAge).and(predicate);
	if (ps.inDateBegin != null)
	    predicate = QPatient.patient.inRecords.any().inDate.after(ps.inDateBegin).and(predicate);
	if (ps.inDateEnd != null)
	    predicate = QPatient.patient.inRecords.any().inDate.before(ps.inDateEnd).and(predicate);
	predicate = queryBinding(QPatient.patient.inRecords.any(), "admissionDept", ps.admissionDept, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "marriageStatus", ps.marriageStatus, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "mainDiag", ps.mainDiag, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "KZR_DOCTOR_NAME", ps.KZR_DOCTOR_NAME, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "dischargeDept", ps.dischargeDept, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "ZY_DOCTOR_NAME", ps.ZY_DOCTOR_NAME, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "ZZHEN_DOCTOR_NAME", ps.ZZHEN_DOCTOR_NAME, predicate);
	predicate = queryBinding(QPatient.patient.frontRecords.any(), "company", ps.company, predicate);
	System.out.println(ps.name);
	System.out.println(predicate);
	Page<Patient> page = service.find(predicate, pageable);
	if (page.getContent().size() == 1)
	    return "redirect:" + getIndexView() + "?id=" + page.getContent().get(0).getId();
	ControllerUtil.setPageVariables(m, page);
	m.addAttribute("pageRequest", ps);
	if (path.endsWith("search") && ps.isNeedSave()) {
	    smService.create(new SearchModel(ps, page));
	}
	return getListView();
    }

    private Predicate queryBinding(Object o, String key, String value, Predicate predicate) {
	if (StringTools.checkNull(value) != null) {
	    Field f = RefactorUtil.getFieldByName(o, key);
	    if (f.getType().getCanonicalName().equals("com.mysema.query.types.path.StringPath"))
		try {
		    predicate = ((com.mysema.query.types.path.StringPath) f.get(o)).containsIgnoreCase(value).and(predicate);
		} catch (IllegalArgumentException | IllegalAccessException e) {
		    e.printStackTrace();
		}
	}
	return predicate;
    }

    @RequestMapping(value = { "/initSearch" })
    public String searchForm() {
	return "/patient/search";
    }

    @RequestMapping(value = { "/reSearch" })
    public String reSearchForm(@ModelAttribute("pageRequest") PatientSearch ps, @RequestParam String searchId) {
	RefactorUtil.setObjectValue(ps, smService.findById(searchId).getKeyValueMap());
	return "/patient/search";
    }

}
