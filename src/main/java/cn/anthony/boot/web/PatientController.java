package cn.anthony.boot.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerMapping;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.PatientPO;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.util.ControllerUtil;

@Controller
@RequestMapping(value = "/patient")
@SessionAttributes(value = { "pageRequest" })
public class PatientController extends GenericController<Patient> {
    @Resource
    PatientService service;

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
    PageRequest initPageRquest() {
	return new PatientSearch();
    }

    @RequestMapping(value = { "/list", "/listPage" })
    public String listPage(@ModelAttribute("pageRequest") PatientSearch pageRequest, Model m, HttpServletRequest request) {
	String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	if (path.endsWith("list")) {
	    pageRequest = new PatientSearch();
	}
	Page<Patient> page = service.findPage(pageRequest);
	if (page.getContent().size() == 1) {
	    return "redirect:" + getIndexView() + "?id=" + page.getContent().get(0).getId();
	}
	ControllerUtil.setPageVariables(m, page);
	m.addAttribute("pageRequest", pageRequest);
	return getListView();
    }

    @RequestMapping(value = { "/dataTableList" })
    @ResponseBody
    private DatatablePageResponse<PatientPO> dataTableList(@RequestParam Map<String, String> q, Model m) {
	System.out.println(q);
	int start = Integer.parseInt(q.get("start"));
	int length = Integer.parseInt(q.get("length"));
	int pageNumber = start % length + 1;
	PatientSearch ps = new PatientSearch(pageNumber, length, q.get("search[value]"));
	Page<Patient> page = service.findPage(ps);
	List<PatientPO> l = new ArrayList<PatientPO>();
	for (Patient p : page.getContent())
	    l.add(new PatientPO(p.getId(), p.getName(), p.frontRecords.size(), p.inRecords.size(), p.operations.size(), p.outRecords.size()));
	ControllerUtil.setPageVariables(m, page);
	DatatablePageResponse<PatientPO> dResp = new DatatablePageResponse<PatientPO>(Integer.parseInt(q.get("draw")), page.getTotalElements(),
		page.getTotalElements(), l, "");
	return dResp;
    }
}
