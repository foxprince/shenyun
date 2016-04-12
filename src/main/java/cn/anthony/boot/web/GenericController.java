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

    /**
     * 在所有方法执行前执行，根据id构造实例
     * 
     * @param id
     * @param m
     * @return
     */
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

    /**
     * 在列表页上方直接编辑form，适用于字段较少的对象
     * 
     * @param id
     * @param m
     * @param status
     * @return
     */
    @RequestMapping(value = "/editInList", method = RequestMethod.GET)
    public String editInList(@RequestParam String id, Model m, SessionStatus status) {
	m.addAttribute(getService().findById(id));
	return list(m);
    }

    /**
     * 添加或删除的提交操作
     * 
     * @param t
     * @param redirectAttributes
     * @param request
     * @param result
     * @return
     * @throws EntityNotFound
     */
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

    /**
     * 用于添加/删除表单的ajax验证
     * 
     * @param t
     * @param result
     * @return
     * @throws EntityNotFound
     */
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

    /**
     * 适用于首页就是列表页
     * 
     * @param m
     * @param status
     * @return
     */
    @RequestMapping(value = { "/index" })
    public String index(Model m, SessionStatus status) {
	status.setComplete();
	return getIndexView();
    }

    @RequestMapping(value = { "/", "/list" })
    public String list(Model m) {
	m.addAttribute("itemList", getService().findAll());
	return getListView();
    }

    /**
     * 根据关联id列出列表
     * 
     * @param m
     * @param relateId
     * @return
     */
    @RequestMapping(value = { "/", "/list" }, params = { "relateId" })
    public String list(Model m, @RequestParam(required = false) String... relateId) {
	listByRelate(m, relateId);
	return getListView();
    }

    protected void listByRelate(Model m, String... relateId) {
    }

    /**
     * 
     * @return jsp的页面地址
     */
    protected abstract String getListView();

    protected abstract String getIndexView();

    protected abstract String getFormView();

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
