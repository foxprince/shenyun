package cn.anthony.boot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.anthony.boot.domain.Remark;
import cn.anthony.boot.service.GenericService;

@Controller
@RequestMapping(value = "/remark")
public class RemarkController extends GenericController<Remark> {

	@Override
	public Remark init(Model m) {
		return new Remark();
	}

	@Override
	GenericService<Remark> getService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getListView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getIndexView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFormView() {
		// TODO Auto-generated method stub
		return null;
	}

}
