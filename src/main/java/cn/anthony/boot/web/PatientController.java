package cn.anthony.boot.web;

import java.lang.reflect.Field;
import java.util.Date;

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
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.service.SearchModelService;
import cn.anthony.boot.util.ControllerUtil;
import cn.anthony.util.DateUtil;
import cn.anthony.util.QueryOption;
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
	// if (ps.minAge != null)
	// predicate = QPatient.patient.age.goe(ps.minAge).and(predicate);
	// if (ps.maxAge != null)
	// predicate = QPatient.patient.age.lt(ps.maxAge).and(predicate);
	// if (ps.inDateBegin != null)
	// predicate =
	// QPatient.patient.inRecords.any().inDate.after(ps.inDateBegin).and(predicate);
	// if (ps.inDateEnd != null)
	// predicate =
	// QPatient.patient.inRecords.any().inDate.before(ps.inDateEnd).and(predicate);
	// predicate = queryBinding(QPatient.patient.inRecords.any(),
	// "admissionDept", ps.getFrontPage().admissionDept, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "marriageStatus", ps.getFrontPage().marriageStatus, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "mainDiag", ps.getFrontPage().mainDiag, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "KZR_DOCTOR_NAME", ps.getFrontPage().KZR_DOCTOR_NAME, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "dischargeDept", ps.getFrontPage().dischargeDept, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "ZY_DOCTOR_NAME", ps.getFrontPage().ZY_DOCTOR_NAME, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "ZZHEN_DOCTOR_NAME", ps.getFrontPage().ZZHEN_DOCTOR_NAME, predicate);
	// predicate = queryBinding(QPatient.patient.frontRecords.any(),
	// "company", ps.getFrontPage().company, predicate);
	predicate = patientBinding(QPatient.patient, "", ps, parameters, predicate);
	predicate = patientBinding(QPatient.patient.inRecords.any(), "inHospital.", ps.getInHospital(), parameters, predicate);
	predicate = patientBinding(QPatient.patient.frontRecords.any(), "frontPage.", ps.getFrontPage(), parameters, predicate);
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

    /**
     * 根据key自动绑定value的值，加到查询条件上
     * 
     * @param o
     * @param key
     * @param value
     * @param predicate
     * @return
     */
    private Predicate patientBinding(Object dslo, String reqPre, Object queryo, MultiValueMap<String, String> paramMap, Predicate predicate) {
	for (QueryOption qp : RefactorUtil.getNotNullValueMap(queryo, reqPre, paramMap)) {
	    predicate = customerBinding(dslo, qp.getKey(), qp.getValue(), qp.getAndOr(), qp.getOption(), predicate);
	}
	return predicate;
    }

    private Predicate queryBinding(Object o, String key, String value, Predicate predicate) {
	if (o != null && StringTools.checkNull(value) != null) {
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

    /**
     * @param o
     * @param key
     * @param value
     * @param andOr
     * @param option
     * @param predicate
     * @return
     */
    private Predicate customerBinding(Object o, String key, String value, String andOr, String option, Predicate predicate) {
	if (o != null && StringTools.checkNull(value) != null) {
	    Field f = RefactorUtil.getFieldByName(o, key);
	    try {
		BooleanExpression expression = null;// .containsIgnoreCase(value);
		if (f.getType().getCanonicalName().equals("com.mysema.query.types.path.NumberPath")) {
		    NumberPath<Integer> path = ((NumberPath<Integer>) f.get(o));
		    Integer ivalue = Integer.parseInt(value);
		    if (option.equalsIgnoreCase("eq"))
			expression = path.eq(ivalue);
		    else if (option.equalsIgnoreCase("contains"))
			expression = path.eq(ivalue);
		    else if (option.equalsIgnoreCase("ge"))
			expression = path.goe(ivalue);
		    else if (option.equalsIgnoreCase("le"))
			expression = path.loe(ivalue);
		    else if (option.equalsIgnoreCase("ne"))
			expression = path.notIn(ivalue);
		} else if (f.getType().getCanonicalName().equals("com.mysema.query.types.path.DateTimePath")) {
		    DateTimePath<Date> path = ((DateTimePath<Date>) f.get(o));
		    Date dvalue = DateUtil.parse(value);
		    if (option.equalsIgnoreCase("eq"))
			expression = path.eq(dvalue);
		    else if (option.equalsIgnoreCase("contains"))
			expression = path.eq(dvalue);
		    else if (option.equalsIgnoreCase("ge"))
			expression = path.after(dvalue);
		    else if (option.equalsIgnoreCase("le"))
			expression = path.before(dvalue);
		    else if (option.equalsIgnoreCase("ne"))
			expression = path.ne(dvalue);
		} else if (f.getType().getCanonicalName().equals("com.mysema.query.types.path.StringPath")) {
		    StringPath path = ((com.mysema.query.types.path.StringPath) f.get(o));
		    if (option.equalsIgnoreCase("eq"))
			expression = path.equalsIgnoreCase(value);
		    else if (option.equalsIgnoreCase("contains"))
			expression = path.containsIgnoreCase(value);
		    else if (option.equalsIgnoreCase("ge"))
			expression = path.goe(value);
		    else if (option.equalsIgnoreCase("le"))
			expression = path.loe(value);
		    else if (option.equalsIgnoreCase("ne"))
			expression = path.notLike(value);
		}
		if (andOr.equalsIgnoreCase("and"))
		    predicate = expression.and(predicate);
		else if (andOr.equalsIgnoreCase("or"))
		    predicate = expression.or(predicate);
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
