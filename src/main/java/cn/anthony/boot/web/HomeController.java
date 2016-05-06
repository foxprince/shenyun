package cn.anthony.boot.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.anthony.boot.service.PatientService;

@Controller
public class HomeController {
    @Resource
    PatientService service;

    @RequestMapping(value = { "/", "/index", "/main" }, method = RequestMethod.GET)
    public String index(@ModelAttribute HomeVO vo) {
	vo.setTotalPatients(service.total());
	return "main/index";
    }

    @RequestMapping(value = { "/tindex" }, method = RequestMethod.GET)
    public String thymeLeafIndex() {
	return "index";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login() {
	return "main/login";
    }

}
