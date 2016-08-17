package cn.anthony.boot.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.anthony.boot.service.PatientService;

@Controller
public class HomeController {
    @Resource
    PatientService service;

    @RequestMapping(value = { "/", "/index", "/main" })
    public String index(@ModelAttribute HomeVO vo) {
	vo.setTotalPatients(service.total());
	return "main/index";
    }
    
    @RequestMapping(value = { "/auth/index.php/admin/login/index.html" })
    public String oldIndex(@ModelAttribute HomeVO vo) {
	return "redirect:/login";
    }

    @RequestMapping(value = { "/tindex" })
    public String thymeLeafIndex() {
	return "index";
    }

    @RequestMapping(value = { "/login","/logout" })
    public String login() {
	return "main/login";
    }

}
