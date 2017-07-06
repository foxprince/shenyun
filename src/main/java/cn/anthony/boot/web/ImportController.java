package cn.anthony.boot.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.ExtendObject;
import cn.anthony.boot.domain.FrontPage;
import cn.anthony.boot.domain.ImportModel;
import cn.anthony.boot.domain.Operation;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.service.GenericService;
import cn.anthony.boot.service.ImportService;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.util.Constant;
import cn.anthony.boot.util.ControllerUtil;
import cn.anthony.util.DateUtil;
import cn.anthony.util.ExcelUtil;
import cn.anthony.util.StringTools;
import lombok.Data;

@Controller
@RequestMapping(value = "/import")
public class ImportController extends GenericController<ImportModel> {
	@Resource
	ImportService service;
	@Resource
	PatientService patientServ;
	@Autowired
	private Environment env;

	@RequestMapping(value = { "/list", "/listPage" })
	public String listPage(@RequestParam String type, @QuerydslPredicate(root = ImportModel.class) Predicate predicate,
			@PageableDefault Pageable pageable, Model m) {
		Page<ImportModel> page = service.find(predicate, pageable);
		ControllerUtil.setPageVariables(m, page);
		m.addAttribute("type", type);
		m.addAttribute("typeDesc", Constant.getExtendTypeDesc(type));
		return getListView();
	}

	@RequestMapping(value = { "/list.json", "/listPage.json" })
	public @ResponseBody Page<ImportModel> ajaxList(@RequestParam String type, @PageableDefault Pageable pageable, Model m) {
		Page<ImportModel> page = service.find(pageable);
		ControllerUtil.setPageVariables(m, page);
		m.addAttribute("recordsTotal", page.getTotalElements());
		m.addAttribute("recordsFiltered", page.getTotalElements());
		m.addAttribute("type", type);
		m.addAttribute("typeDesc", Constant.getExtendTypeDesc(type));
		return page;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ImportModel uploadFile(@RequestParam String type, @RequestParam("uploadfile") MultipartFile uploadfile) {
		try {
			// 1： Get the filename and build the local file path
			String filename = uploadfile.getOriginalFilename();
			String directory = env.getProperty("paths.uploadedFiles");
			String dstPath = Paths
					.get(directory,
							type + "_" + DateUtil.format(Calendar.getInstance().getTime(), "yyyyMMddHHmmssSSS") + "_" + filename)
					.toString();
			Files.copy(uploadfile.getInputStream(), Paths.get(dstPath), StandardCopyOption.REPLACE_EXISTING);
			// 2：解析文件
			ImportResult ir = parseFile(type, dstPath);
			// 3：生成纪录
			int operator = 0;
			ImportModel model = new ImportModel(type, filename, dstPath, ir.getTotal(), ir.getSuccess(), ir.getInsertTotal(),
					ir.getInsertList(), ir.getUpdateTotal(), ir.getUpdateList(), operator);
			service.create(model);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Data
	public static class ImportResult {
		int total = 0;
		int success = 0;
		int insertTotal = 0;
		List<String> insertList = new ArrayList<String>();
		int updateTotal = 0;
		List<String> updateList = new ArrayList<String>();
		int operator = 0;
	}

	private ImportResult parseFile(String type, String dstPath) {
		int total = 0;
		int success = 0;
		int insertTotal = 0;
		List<String> insertList = new ArrayList<String>();
		int updateTotal = 0;
		ImportResult ir = new ImportResult();
		List<String> updateList = new ArrayList<String>();
		try {
			if (type.equals("cxz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(12);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(l.get(12));
								p.setSource(type);
								p.setName(l.get(0));
								p.setSex(l.get(2).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(3)));
								p.setCertNo(l.get(4));
								p.setDateOfBirth(DateUtil.parse(l.get(8)));
								FrontPage fp = new FrontPage();
								fp.setAdmissionDept(l.get(9));
								fp.setMainDiag(l.get(5));
								fp.setAdmissionCondition(l.get(6));
								fp.setContactName(l.get(15));
								fp.setContactphone(l.get(16));
								fp.setHomeAddress(l.get(21));
								p.addFront(fp);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type, new ExtendObject.出血组(l.get(5), l.get(6), l.get(7), l.get(10), l.get(15),
									l.get(16), l.get(17), l.get(21)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				is.close();
			} else if (type.equalsIgnoreCase("icu")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(4);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(1));
								p.setSex(l.get(2).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(3)));
								p.setDateOfBirth(DateUtil.parse(l.get(7)));
								FrontPage fp = new FrontPage();
								fp.setMainDiag(l.get(12));
								fp.setAdmissionCondition(l.get(6));
								fp.setContactName(l.get(8));
								fp.setContactphone(l.get(9));
								fp.setHomeAddress(l.get(10));
								p.addFront(fp);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type,
									new ExtendObject.ICU(l.get(1), l.get(2), l.get(3), pId, l.get(5), l.get(6), l.get(7), l.get(8),
											l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15), l.get(16),
											l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23), l.get(24),
											l.get(25), l.get(26), l.get(27), l.get(28), l.get(29), l.get(30)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				is.close();
			} else if (type.equalsIgnoreCase("ldz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(4);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(1));
								p.setSex(l.get(2).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(3)));
								FrontPage fp = new FrontPage();
								fp.setREGISTER_DIAGNOSIS(l.get(5));
								fp.setAdmissionTime(DateUtil.parse(l.get(7)));
								fp.setDischargeTime(DateUtil.parse(l.get(11)));
								fp.setMainDiag(l.get(12));
								fp.setContactphone(l.get(23));
								fp.setHomeAddress(l.get(19));
								p.addFront(fp);
								Operation op = new Operation();
								op.setOperationTitle(l.get(9));
								op.setDoctor(l.get(10));
								op.setBeginTime(DateUtil.parse(l.get(8)));
								p.addOperation(op);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type,
									new ExtendObject.Ldz(l.get(1), l.get(2), l.get(3), pId, l.get(5), l.get(6), l.get(7), l.get(8),
											l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15), l.get(16),
											l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			} else if (type.equalsIgnoreCase("xrz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(1);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(2));
								p.setSex(l.get(3).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(4)));
								FrontPage fp = new FrontPage();
								fp.setREGISTER_DIAGNOSIS(l.get(13));
								fp.setAdmissionTime(DateUtil.parse(l.get(5)));
								fp.setDischargeTime(DateUtil.parse(l.get(6)));
								fp.setMainDiag(l.get(14));
								fp.setContactphone(l.get(24));
								fp.setHomeAddress(l.get(23));
								p.addFront(fp);
								Operation op = new Operation();
								op.setOperationTitle(l.get(8));
								op.setDoctor(l.get(9));
								op.setBeginTime(DateUtil.parse(l.get(7)));
								p.addOperation(op);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type,
									new ExtendObject.Xrz(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6), l.get(7),
											l.get(8), l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15),
											l.get(16), l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23),
											l.get(24), l.get(25), l.get(26)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			} else if (type.equalsIgnoreCase("gnz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(1);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(2));
								p.setSex(l.get(3).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(4)));
								FrontPage fp = new FrontPage();
								fp.setREGISTER_DIAGNOSIS(l.get(13));
								fp.setAdmissionTime(DateUtil.parse(l.get(5)));
								fp.setDischargeTime(DateUtil.parse(l.get(6)));
								fp.setMainDiag(l.get(14));
								fp.setContactphone(l.get(26));
								fp.setHomeAddress(l.get(25));
								p.addFront(fp);
								Operation op = new Operation();
								op.setOperationTitle(l.get(8));
								op.setDoctor(l.get(9));
								op.setBeginTime(DateUtil.parse(l.get(7)));
								p.addOperation(op);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type,
									new ExtendObject.Gnz(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6), l.get(7),
											l.get(8), l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15),
											l.get(16), l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23),
											l.get(24), l.get(25), l.get(26), l.get(27), l.get(28)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			} else if (type.equalsIgnoreCase("zlz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(1);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(2));
								p.setSex(l.get(3).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(4)));
								FrontPage fp = new FrontPage();
								fp.setREGISTER_DIAGNOSIS(l.get(17));
								fp.setAdmissionTime(DateUtil.parse(l.get(5)));
								fp.setDischargeTime(DateUtil.parse(l.get(6)));
								fp.setMainDiag(l.get(18));
								fp.setContactphone(l.get(27));
								fp.setHomeAddress(l.get(26));
								p.addFront(fp);
								Operation op = new Operation();
								op.setOperationTitle(l.get(8));
								op.setDoctor(l.get(9));
								op.setBeginTime(DateUtil.parse(l.get(7)));
								p.addOperation(op);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type,
									new ExtendObject.Zlz(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6), l.get(7),
											l.get(8), l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15),
											l.get(16), l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23),
											l.get(24), l.get(25), l.get(26), l.get(27), l.get(28), l.get(29)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			} else if (type.equalsIgnoreCase("jzz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(2);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(1));
								p.setSex(l.get(3).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(4)));
								FrontPage fp = new FrontPage();
								fp.setAdmissionTime(DateUtil.parse(l.get(8)));
								fp.setDischargeTime(DateUtil.parse(l.get(10)));
								fp.setMainDiag(l.get(11));
								fp.setContactphone(l.get(7));
								fp.setHomeAddress(l.get(5));
								p.addFront(fp);
								Operation op = new Operation();
								op.setOperationTitle(l.get(8));
								op.setDoctor(l.get(18));
								op.setBeginTime(DateUtil.parse(l.get(9)));
								p.addOperation(op);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type, new ExtendObject.Jzz(l.get(1), l.get(2), l.get(3), pId, l.get(5), l.get(6),
									l.get(7), l.get(8), l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15),
									l.get(16), l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23), l.get(24),
									l.get(25), l.get(26), l.get(27), l.get(28), l.get(29), l.get(30), l.get(31), l.get(32), l.get(33)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			} else if (type.equalsIgnoreCase("bflz")) {
				InputStream is = new FileInputStream(dstPath);
				Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is);
				total = map.size();
				for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
					try {
						List<String> l = entry.getValue();
						String pId = l.get(4);
						if (StringTools.checkNull(pId) != null) {
							Patient p = patientServ.findByPid(pId);
							if (p == null) {
								p = new Patient();
								p.setpId(pId);
								p.setSource(type);
								p.setName(l.get(1));
								p.setSex(l.get(7).equals("男") ? "1" : "2");
								p.setAge(Integer.parseInt(l.get(8)));
								FrontPage fp = new FrontPage();
								fp.setAdmissionTime(DateUtil.parse(l.get(9)));
								fp.setDischargeTime(DateUtil.parse(l.get(11)));
								fp.setMainDiag(l.get(16));
								fp.setContactphone(l.get(13));
								fp.setHomeAddress(l.get(12));
								p.addFront(fp);
								insertTotal++;
								insertList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							} else {
								updateTotal++;
								updateList.add("<a href='/patient/?pId=" + pId + "'>" + p.getName() + "</a>");
							}
							p.getExtendMap().put(type, new ExtendObject.Bflz(l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6),
									l.get(7), l.get(8), l.get(9), l.get(10), l.get(11), l.get(12), l.get(13), l.get(14), l.get(15),
									l.get(16), l.get(17), l.get(18), l.get(19), l.get(20), l.get(21), l.get(22), l.get(23), l.get(24),
									l.get(25), l.get(26), l.get(27), l.get(28), l.get(29), l.get(30), l.get(31), l.get(32), l.get(33),
									l.get(34), l.get(35), l.get(36), l.get(37), l.get(38), l.get(39), l.get(40), l.get(41), l.get(42),
									l.get(43), l.get(44), l.get(45), l.get(46), l.get(47), l.get(48), l.get(49), l.get(50), l.get(51),
									l.get(52), l.get(53), l.get(54), l.get(55), l.get(56), l.get(57), l.get(58), l.get(59), l.get(60),
									l.get(61), l.get(62), l.get(63), l.get(64), l.get(65), l.get(66), l.get(67), l.get(68), l.get(69),
									l.get(70), l.get(71), l.get(72), l.get(73), l.get(74), l.get(75), l.get(76), l.get(77), l.get(78),
									l.get(79), l.get(80), l.get(81), l.get(82), l.get(83), l.get(84), l.get(85), l.get(86), l.get(87),
									l.get(88), l.get(89), l.get(90), l.get(91), l.get(92), l.get(93), l.get(94), l.get(95), l.get(96),
									l.get(97), l.get(98), l.get(99), l.get(100), l.get(101), l.get(102), l.get(103), l.get(104),
									l.get(105), l.get(106), l.get(107), l.get(108), l.get(109), l.get(110), l.get(111), l.get(112),
									l.get(113), l.get(114), l.get(115), l.get(116), l.get(117), l.get(118), l.get(119), l.get(120),
									l.get(121), l.get(122), l.get(123), l.get(124), l.get(125), l.get(126), l.get(127), l.get(128),
									l.get(129), l.get(130), l.get(131), l.get(132), l.get(133), l.get(134), l.get(135), l.get(136),
									l.get(137), l.get(138), l.get(139), l.get(140), l.get(141)));
							patientServ.create(p);
							success++;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					is.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ir.setTotal(total);
		ir.setInsertList(insertList);
		ir.setInsertTotal(insertTotal);
		ir.setSuccess(success);
		ir.setUpdateList(updateList);
		ir.setUpdateTotal(updateTotal);
		return ir;
	}

	@Override
	public ImportModel init(Model m) {
		return new ImportModel();
	}

	// 分页需要用
	@ModelAttribute("pageRequest")
	PageRequest initPageRquest() {
		return new PageRequest();
	}

	@Override
	GenericService<ImportModel> getService() {
		return this.service;
	}

	@Override
	protected String getListView() {
		return "/patient/import";
	}

	@Override
	protected String getIndexView() {
		return "/patient/import";
	}

	@Override
	protected String getFormView() {
		return "/patient/import";
	}
}
