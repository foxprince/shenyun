package cn.anthony.boot.web;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.util.ControllerUtil;

@Controller
@RequestMapping(value = "/patient")
@SessionAttributes("patient")
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

    @RequestMapping(value = { "/list", "/listPage" })
    public String listPage(@ModelAttribute("pageRequest") PatientSearch pageRequest, Model m) {
	Page<Patient> page = service.findPage(pageRequest);
	ControllerUtil.setPageVariables(m, page);
	return getListView();
    }
}
