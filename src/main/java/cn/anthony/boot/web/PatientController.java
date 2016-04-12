package cn.anthony.boot.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.PatientService;

@Controller
@RequestMapping(value = "/patient")
// @SessionAttributes("patient")
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
    protected String getPageView() {
	return "/patient/list";
    }

    @Override
    String updateHint(Patient t) {
	return "成功：" + t.getName();
    }

    @Override
    String deleteHint(Patient t) {
	return "成功删除：" + t.getName();
    }

}
