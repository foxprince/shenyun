package cn.anthony.boot.web;

import cn.anthony.boot.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

	@GetMapping(value = { "/test" })
	public String thymeLeafIndex() {
		return "test";
	}

	@RequestMapping(value = { "/login", "/logout" })
	public String login() {
		return "main/login";
	}
}
