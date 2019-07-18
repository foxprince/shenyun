package cn.anthony.boot.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.domain.QPatient_动眼神经;
import cn.anthony.boot.domain.QPatient_反射;
import cn.anthony.boot.domain.QPatient_听力;
import cn.anthony.boot.domain.QPatient_头部反射;
import cn.anthony.boot.domain.QPatient_痛触觉;
import cn.anthony.boot.domain.QPatient_眼底;
import cn.anthony.boot.domain.QPatient_视力;
import cn.anthony.boot.domain.QPatient_颅神经;
import cn.anthony.boot.domain.QPatient_高级皮层功能;
import cn.anthony.boot.domain.QSomatoscopy;
import cn.anthony.boot.domain.Remark;
import cn.anthony.boot.domain.SearchModel;
import cn.anthony.boot.exception.EntityNotFound;
import cn.anthony.boot.service.CustomeOptionService;
import cn.anthony.boot.service.FileAssetService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.service.SearchModelService;
import cn.anthony.boot.util.Constant;
import cn.anthony.boot.util.ControllerUtil;
import cn.anthony.boot.util.PageUtil;
import cn.anthony.util.DateUtil;
import cn.anthony.util.ExcelUtil;
import cn.anthony.util.QueryOption;
import cn.anthony.util.RefactorUtil;
import cn.anthony.util.StringTools;

@Controller
@RequestMapping(value = "/patient")
// @SessionAttributes(value = { "pageRequest" })
public class PatientController extends GenericController<Patient> {
	@Resource
	PatientService service;
	@Resource
	SearchModelService searchModelService;
	@Resource
	CustomeOptionService customeOptionService;
	@Resource
	FileAssetService fileAssetService;

	@Override
	public Patient init(Model m) {
		return new Patient();
	}

	@Override
	PatientService getService() {
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
	PatientSearch initPageRquest() {
		return new PatientSearch();
	}

	@RequestMapping(value = { "/", "/index" }, params = "id")
	public String index(String id, Model m) {
		Patient p = service.findById(id);
		m.addAttribute("patient", p);
		m.addAttribute("assets", fileAssetService.findByNr(p.getName()));
		return getIndexView();
	}
	
	@RequestMapping(value = { "/query" })
	public String query(@QuerydslPredicate(root = Patient.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "ctime" }, direction = Sort.Direction.ASC) Pageable pageable,Model m
			) {
		Page<Patient> page = service.find(predicate, pageable);
		if (page.getContent().size() == 1)
			return "redirect:" + getIndexView() + "?id=" + page.getContent().get(0).getId();
		else {
			ControllerUtil.setPageVariables(m, page);
			return getListView();
		}
	}
	
	@RequestMapping(value = { "/distinctName" })
	@ResponseBody
	public List<String> distinctName(
			@PageableDefault(value = 10, sort = { "ctime" }, direction = Sort.Direction.ASC) Pageable pageable
			) {
		return service.findDistinctName(pageable);
	}
	
	@RequestMapping(value = { "/queryAssetNr" })
	public String queryAsset(String name,@PageableDefault(value = 100, sort = { "ctime" }, direction = Sort.Direction.ASC) Pageable pageable , Model m) {
		//m.addAttribute("totalNames", fileAssetService.totalDistinctNr());
		if(name!=null&&fileAssetService.findByNr(name).size()>0) {
			List<String> l = Arrays.asList(name);
			ControllerUtil.setPageVariables(m, new PageImpl<String>(l,pageable, l.size()));
		}
		else
			ControllerUtil.setPageVariables(m, fileAssetService.findDistinctNr(pageable));
		//m.addAttribute("assetNames", .getContent());
		return "/patient/assetAll";
	}
	
	@RequestMapping(value = { "/search", "/list", "/listPage", "/fullSearch" })
	public String listPage(@ModelAttribute("pageRequest") PatientSearch ps,
			@QuerydslPredicate(root = Patient.class) Predicate predicate,
			@PageableDefault(value = 10, sort = { "ctime" }, direction = Sort.Direction.ASC) Pageable pageable, Model m,
			@RequestParam MultiValueMap<String, String> parametersMap, HttpServletRequest request, HttpSession session)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Map<String, List<String>> parameters = RefactorUtil.filterEmpty(parametersMap);
		System.out.println(parameters);
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		if (path.endsWith("list"))// 清空搜索历史
			ps = new PatientSearch();
		if (ps.minAge != null)
			predicate = QPatient.patient.age.goe(ps.minAge).and(predicate);
		if (ps.maxAge != null)
			predicate = QPatient.patient.age.lt(ps.maxAge).and(predicate);
		if (ps.inDateBegin != null)
			predicate = QPatient.patient.inRecords.any().inDate.after(ps.inDateBegin).and(predicate);
		if (ps.inDateEnd != null)
			predicate = QPatient.patient.inRecords.any().inDate.before(ps.inDateEnd).and(predicate);
		if (ps.outDateBegin != null)
			predicate = QPatient.patient.outRecords.any().outDate.after(ps.outDateBegin).and(predicate);
		if (ps.outDateEnd != null)
			predicate = QPatient.patient.outRecords.any().outDate.before(ps.outDateEnd).and(predicate);
		
		predicate = patientBinding(QPatient.patient, "patient.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.inRecords.any(), "inHospital.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.inRecords.any().firstDiag, "inHospital.firstDiag", parameters, predicate);
		predicate = patientBinding(QPatient.patient.inRecords.any().confirmDiag, "inHospital.confirmDiag", parameters, predicate);
		predicate = patientBinding(QPatient.patient.inRecords.any().correctDiag, "inHospital.correctDiag", parameters, predicate);
		predicate = patientBinding(QPatient.patient.inRecords.any().supplyDiags.any(), "inHospital.supplyDiags", parameters, predicate);
		predicate = patientBinding(QSomatoscopy.somatoscopy, "inHospital", parameters, predicate);
		predicate = patientBinding(QSomatoscopy.somatoscopy.sExamination, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_高级皮层功能.高级皮层功能, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_颅神经.颅神经, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_反射.反射, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_动眼神经.动眼神经, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_听力.听力, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_头部反射.头部反射, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_痛触觉.痛触觉, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_眼底.眼底, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient_视力.视力, "inHospital", parameters, predicate);
		predicate = patientBinding(QPatient.patient.frontRecords.any(), "frontPage.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.frontRecords.any().outDiags.any(), "frontPage.outDiag.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.frontRecords.any().operationDetails.any(), "frontPage.operationDetail.", parameters,
				predicate);
		predicate = patientBinding(QPatient.patient.frontRecords.any().severeDetails.any(), "frontPage.severeDetail.", parameters,
				predicate);
		predicate = patientBinding(QPatient.patient.operations.any(), "operation.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.outRecords.any(), "outHospital.", parameters, predicate);
		predicate = patientBinding(QPatient.patient.remarks.any(), "remark.", parameters, predicate);
		session.setAttribute("predicate", predicate);
		QPatient.patient.age.asc();
		System.out.println("predecate:" + predicate);
		Page<Patient> page = service.find(predicate, pageable);
		if (page.getContent().size() == 1)
			return "redirect:" + getIndexView() + "?id=" + page.getContent().get(0).getId();
		ControllerUtil.setPageVariables(m, page);
		m.addAttribute("pageRequest", ps);
		if (ps.isNeedSave()) {
			searchModelService.create(new SearchModel(path.endsWith("fullSearch") ? "2" : "1", parameters, page));
		}
		// 导出exel的选项
		m.addAttribute("patientOptions", Constant.patientKeyMap.values());
		m.addAttribute("frontPageOptions", Constant.frontPageKeyMap.values());
		m.addAttribute("inOptions", Constant.inKeyMap.values());
		m.addAttribute("operationOptions", Constant.operKeyMap.values());
		m.addAttribute("outOptions", Constant.outKeyMap.values());
		m.addAttribute("bloodOptions", Constant.bloodKeyMap.values());
		m.addAttribute("customeOptions", customeOptionService.findAll());
		if(parameters.get("source")!=null)
			m.addAttribute("source", parameters.get("source").get(0));
		return getListView();
	}
	@RequestMapping(value = {"/listWithAsset"})
	public String listWithAsset(@PageableDefault(value = 10, sort = { "ctime" }, direction = Sort.Direction.ASC) Pageable pageable,Model m){
		ControllerUtil.setPageVariables(m, service.getRepository().findWithAsset(pageable));
		return getListView();
	}
	/**
	 * 试验成功，正式方法
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping(value = "/export")
	public ModelAndView getExcel(String id, String[] fields, HttpSession session) {
		String columnNames[] = { "名称" };// 列名
		Predicate predicate = (Predicate) session.getAttribute("predicate");
		System.out.println("session predicate:" + predicate);
		List<Map<String, Object>> list = createExcel(service.getRepository().findAll(predicate), Arrays.asList(fields));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("keys", fields);
		m.put("columnNames", Constant.toNames(fields));
		m.put("result", list);
		return new ModelAndView(new PatientExcelView(), m);
	}

	@RequestMapping(value = "/zip")
	public ModelAndView getZip(String id, String patientNo) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("id", id);
		m.put("patientNo", service.findById(id).getName() + "_" + patientNo);
		m.put("file", new File(PageUtil.generateZip(id, patientNo)));
		return new ModelAndView(new ZipView(), m);
	}

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	@ResponseBody
	public String getPdf(String id) {
		Map<String, Patient> m = new HashMap<String, Patient>();
		m.put("patient", service.findById(id));
		// return new ModelAndView(new PatientPdfView(), m);
		return "ok";
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public String print() {
		return "/patient/index-print";
	}

	/**
	 * 停用此方法，只作为测试用。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/download")
	public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = "excel文件";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();// createExcel();
		String columnNames[] = { "项目名" };// 列名
		String keys[] = { "name" };// map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	private List<Map<String, Object>> createExcel(Iterable<Patient> iterable, List<String> fields) {
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sheetName", "sheet1");
		listmap.add(map);
		for (Patient patient : iterable) {
			Map<String, Object> mapValue = new HashMap<String, Object>();
			mapValue.putAll(RefactorUtil.getKeyValueMap(patient, fields, "patient."));
			// TODO 此处只取第一条纪录，没有考虑一个病人多个入院纪录的情况
			if (!ObjectUtils.isEmpty(patient.getFrontRecords()))
				mapValue.putAll(RefactorUtil.getKeyValueMap(patient.getFrontRecords().get(0), fields, "frontPage."));
			if (!ObjectUtils.isEmpty(patient.getInRecords()))
				mapValue.putAll(RefactorUtil.getKeyValueMap(patient.getInRecords().get(0), fields, "inHospital."));
			if (!ObjectUtils.isEmpty(patient.getOutRecords()))
				mapValue.putAll(RefactorUtil.getKeyValueMap(patient.getOutRecords().get(0), fields, "outHospital."));
			if (!ObjectUtils.isEmpty(patient.getOperations()))
				mapValue.putAll(RefactorUtil.getKeyValueMap(patient.getOperations().get(0), fields, "operation."));
			listmap.add(mapValue);
		}
		return listmap;
	}

	@RequestMapping(value = { "/initSearch" })
	public String searchForm(Model m) {
		m.addAttribute("changedeptList", service.getChangedeptList());
		m.addAttribute("dischargeWardList", service.getDischargeWardList());
		m.addAttribute("admissionWardList", service.getAdmissionWardList());
		m.addAttribute("sourceList", service.ditinctSource());
		return "/patient/search";
	}

	@RequestMapping(value = { "/addRemark" })
	@ResponseBody
	public Remark addRemark(String patientId, Remark remark, Model m) {
		Patient p = service.findById(patientId);
		remark.setId(UUID.randomUUID().toString());
		if (p.getRemarks() == null)
			p.setRemarks(new ArrayList<Remark>());
		p.getRemarks().add(remark);
		try {
			service.update(p);
		} catch (EntityNotFound e) {
			e.printStackTrace();
		}
		return remark;
	}

	@RequestMapping(value = { "/delRemark" })
	@ResponseBody
	public boolean delRemark(String patientId, String remarkId) {
		Patient p = service.findById(patientId);
		p.delRemark(remarkId);
		try {
			service.update(p);
		} catch (EntityNotFound e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@RequestMapping(value = { "/updateAsset" })
	@ResponseBody
	public String updateAsset(String pId) {
		try {
			return service.updateAssets("haitai",pId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "find fail";
	}
	
	@RequestMapping(value = { "/updateAssetByTime" })
	@ResponseBody
	public String updateAssetByTime(@DateTimeFormat(pattern="yyyyMMdd")Date begin,@DateTimeFormat(pattern="yyyyMMdd")Date end) {
		try {
			System.out.println("begin:"+begin+"---end:"+end);
			return service.updateAssetsByInTime("haitai",begin,end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "find fail";
	}

	/**
	 * 根据key自动绑定value的值，加到查询条件上
	 * 
	 * @param o
	 * @param key
	 * @param value
	 * @param predicate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private Predicate patientBinding(BeanPath dslo, String reqPre, Map<String, List<String>> paramMap, Predicate predicate)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object o = RefactorUtil.initPathClass(dslo);
		// Class.forName(dslo.getAnnotatedElement().toString().substring(6));
		for (QueryOption qp : RefactorUtil.getNotNullValueMap(o, reqPre, paramMap)) {
			predicate = customerBinding(dslo, qp.getKey(), qp.getValue(), qp.getAndOr(), qp.getOption(), predicate);
		}
		return predicate;
	}

	/**
	 * @param o
	 * @param key
	 * @param value
	 * @param andOr
	 * @param option
	 * @param predicate
	 * @return
	 */
	private Predicate customerBinding(Object o, String key, String value, String andOr, String option, Predicate predicate) {
		if (o != null && StringTools.checkNull(value) != null) {
			Field f = RefactorUtil.getFieldByName(o, key);
			if (f != null)
				try {
					BooleanExpression expression = null;// .containsIgnoreCase(value);
					if (f.getType().getCanonicalName().equals("com.querydsl.core.types.dsl.ListPath")) {
						ListPath path = (ListPath) f.get(o);
						System.out.println(path);
					} else if (f.getType().getCanonicalName().equals("com.querydsl.core.types.dsl.NumberPath")) {
						NumberPath<Integer> path = ((NumberPath<Integer>) f.get(o));
						Integer ivalue = Integer.parseInt(value);
						if (option.equalsIgnoreCase("eq"))
							expression = path.eq(ivalue);
						else if (option.equalsIgnoreCase("contains"))
							expression = path.eq(ivalue);
						else if (option.equalsIgnoreCase("ge"))
							expression = path.goe(ivalue);
						else if (option.equalsIgnoreCase("le"))
							expression = path.loe(ivalue);
						else if (option.equalsIgnoreCase("notIn"))
							expression = path.notIn(ivalue);
						else if (option.equalsIgnoreCase("ne"))
							expression = path.notIn(ivalue);
					} else if (f.getType().getCanonicalName().equals("com.querydsl.core.types.dsl.DateTimePath")) {
						DateTimePath<Date> path = ((DateTimePath<Date>) f.get(o));
						Date dvalue = DateUtil.parse(value);
						if (option.equalsIgnoreCase("eq"))
							expression = path.eq(dvalue);
						else if (option.equalsIgnoreCase("contains"))
							expression = path.eq(dvalue);
						else if (option.equalsIgnoreCase("ge"))
							expression = path.after(dvalue);
						else if (option.equalsIgnoreCase("le"))
							expression = path.before(dvalue);
						else if (option.equalsIgnoreCase("ne"))
							expression = path.ne(dvalue);
						else if (option.equalsIgnoreCase("notIn"))
							expression = path.notIn(dvalue);
					} else if (f.getType().getCanonicalName().equals("com.querydsl.core.types.dsl.StringPath")) {
						StringPath path = ((StringPath) f.get(o));
						if (option.equalsIgnoreCase("eq"))
							expression = path.equalsIgnoreCase(value);
						else if (option.equalsIgnoreCase("contains"))
							expression = path.containsIgnoreCase(value);
						else if (option.equalsIgnoreCase("ge"))
							expression = path.goe(value);
						else if (option.equalsIgnoreCase("le"))
							expression = path.loe(value);
						else if (option.equalsIgnoreCase("ne"))
							expression = path.notEqualsIgnoreCase(value);
						else if (option.equalsIgnoreCase("notIn"))
							expression = path.containsIgnoreCase(value).not();
					}
					if (andOr.equalsIgnoreCase("and"))
						predicate = expression.and(predicate);
					else if (andOr.equalsIgnoreCase("or"))
						predicate = expression.or(predicate);
					else if (andOr.equalsIgnoreCase("not"))
						predicate = expression.ne(predicate);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		return predicate;
	}

	private Predicate queryBinding(Object o, String key, String value, Predicate predicate) {
		if (o != null && StringTools.checkNull(value) != null) {
			Field f = RefactorUtil.getFieldByName(o, key);
			if (f.getType().getCanonicalName().equals("com.querydsl.core.types.dsl.StringPath"))
				try {
					predicate = ((StringPath) f.get(o)).containsIgnoreCase(value).and(predicate);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		return predicate;
	}
}
