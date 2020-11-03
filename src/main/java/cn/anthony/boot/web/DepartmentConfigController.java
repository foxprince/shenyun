package cn.anthony.boot.web;

import cn.anthony.boot.domain.DepartmentConfig;
import cn.anthony.boot.service.DepartmentConfigService;
import cn.anthony.boot.service.GenericService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/departmentConfig")
public class DepartmentConfigController extends GenericController<DepartmentConfig> {
	@Resource
	DepartmentConfigService service;
	@Override
	public DepartmentConfig init(Model m) {
		return null;
	}

	@Override
	GenericService getService() {
		return service;
	}

	/**
	 * @return jsp的页面地址
	 */
	@Override
	protected String getListView() {
		return "/patient/departmentConfig";
	}

	@Override
	protected String getIndexView() {
		return "/patient/departmentConfig";
	}

	@Override
	protected String getFormView() {
		return "/patient/departmentConfig";
	}
}
