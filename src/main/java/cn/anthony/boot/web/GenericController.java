package cn.anthony.boot.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.anthony.boot.exception.EntityNotFound;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.util.ControllerUtil;

@Controller
public abstract class GenericController<T> {
    public abstract T init(Model m);

    abstract GenericService<T> getService();

    String getFormView() {
	return "redirect:list";
    }

    @ModelAttribute
    public T setUpForm(@RequestParam(value = "id", required = false) String id, Model m) {
	if (id == null) {
	    return init(m);
	} else {
	    return getService().findById(id);
	}
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model m) {
	m.addAttribute(init(m));
	return getFormView();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam String id, Model m, SessionStatus status) {
	m.addAttribute(getService().findById(id));
	return getFormView();
    }

    @RequestMapping(value = "/editInList", method = RequestMethod.GET)
    public String editInList(@RequestParam String id, Model m, SessionStatus status) {
	m.addAttribute(getService().findById(id));
	return list(m);
    }

    @RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.POST)
    public String edit(@ModelAttribute T t, final RedirectAttributes redirectAttributes, HttpServletRequest request, BindingResult result)
	    throws EntityNotFound {
	if (result.hasErrors()) {
	    return getFormView();
	} else {
	    getService().update(t);
	    redirectAttributes.addFlashAttribute("message", (request.getServletPath().endsWith("add") ? "添加" : "修改") + updateHint(t));
	    return "redirect:list";
	}
    }

    @RequestMapping(value = { "/edit.json", "/add.json" }, method = RequestMethod.POST)
    public @ResponseBody ValidationResponse processFormAjaxJson(@ModelAttribute @Valid T t, BindingResult result) throws EntityNotFound {
	validate(t, result);
	ValidationResponse res = new ValidationResponse();
	if (!result.hasErrors()) {
	    res.setStatus("SUCCESS");
	} else {
	    res.setStatus("FAIL");
	    List<FieldError> allErrors = result.getFieldErrors();
	    List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
	    for (FieldError objectError : allErrors) {
		errorMesages.add(new ErrorMessage(objectError.getField(), objectError.getDefaultMessage()));
	    }
	    res.setErrorMessageList(errorMesages);
	}
	return res;
    }

    @RequestMapping(value = { "/" })
    public String index(Model m, SessionStatus status) {
	status.setComplete();
	return list(m);
    }

    @RequestMapping(value = { "/index", "/list" })
    public String list(Model m) {
	m.addAttribute("itemList", getService().findAll());
	return getPageView();
    }

    @RequestMapping(value = { "/", "/index", "/list" }, params = { "relateId" })
    public String list(Model m, @RequestParam(required = false) Long... relateId) {
	listByRelate(m, relateId);
	return getPageView();
    }

    protected void listByRelate(Model m, Long... relateId) {
    }

    /**
     * 
     * @return jsp的页面地址
     */
    protected abstract String getPageView();

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(@RequestParam String id, final RedirectAttributes redirectAttributes, SessionStatus status) throws EntityNotFound {
	ModelAndView mav = new ModelAndView("redirect:list");
	T t = getService().delete(id);
	status.setComplete();
	redirectAttributes.addFlashAttribute("message", deleteHint(t));
	return mav;
    }

    public void validate(T t, Errors errors) {

    }

    String updateHint(T t) {
	return "成功";
    }

    String deleteHint(T t) {
	return "成功删除";
    }

}
