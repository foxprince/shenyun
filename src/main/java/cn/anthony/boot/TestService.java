package cn.anthony.boot;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mysema.query.types.Predicate;

import cn.anthony.boot.domain.FrontPage;
import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QInHospital;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.service.TotalService;
import cn.anthony.boot.util.PatientUtil;
import cn.anthony.util.ExcelUtil;
import cn.anthony.util.RefactorUtil;

@SpringBootApplication
public class TestService implements CommandLineRunner {
	private static final String MOVE_DIR = "E:\\project\\神云系统\\data\\已处理\\";
	private static final String MUL_DIR = "E:\\project\\神云系统\\data\\待处理\\";
	@Autowired
	private PatientRepository repository;
	@Autowired
	private PatientService service;
	@Autowired
	private TotalService tservice;
	private Set<String> s = new HashSet<String>();

	public static void main(String[] args) {
		//System.setProperty("DB.TRACE", "true");
		//System.setProperty("DEBUG.MONGO", "true");
		SpringApplication.run(TestService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("run test");
		try {
			long t1 = System.currentTimeMillis();
			InputStream is2 = new FileInputStream("/Users/zj/Documents/project/shenyun/2015患者信息（高清玲）.xlsx");
			Map<Integer, List<String>> map = ExcelUtil.readExcelContent(is2);
			int t = 0;
			for (int i = 1; i <= map.size(); i++) {
				List<String> l = (map.get(i));
				Patient p = new Patient();
				p.setSource("gql");
				p.setName(l.get(3));
				p.setpId(l.get(4));
				FrontPage fp = new FrontPage();
				try {
					fp.setAdmissionTime(DateUtils.parseDate(l.get(0), "MM/dd/yy"));
					fp.setDischargeTime(DateUtils.parseDate(l.get(10), "MM/dd/yy"));
				} catch (Exception e) {
				}
				fp.setREGISTER_DIAGNOSIS(l.get(6));
				fp.setMainDiag(l.get(7));
				fp.setZY_DOCTOR_NAME(l.get(5));
				p.addFront(fp);
				service.create(p);
				t++;
				System.out.println(p.getName()+","+l.get(0)+":"+fp.getAdmissionTimeDesc()+","+l.get(10)+":"+fp.getDischargeTimeDesc());
			}
			System.out.println(t);
			long t2 = System.currentTimeMillis();
			System.out.println(t2 - t1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Predicate bind(String key, String value, Predicate pre) {
		Field f = RefactorUtil.getFieldByName(QInHospital.inHospital, key);
		if (f.getType().getCanonicalName().equals("com.mysema.query.types.path.StringPath")) {
			try {
				pre = ((com.mysema.query.types.path.StringPath) f.get(QPatient.patient.inRecords.any())).eq(value).and(pre);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return pre;
	}

	private void processTool() throws ParseException {
		String srcDir = "E:\\project\\神云系统\\data\\待处理";
		File dir = new File(srcDir);
		long t1 = System.currentTimeMillis();
		// 先处理首页文件，处理完移走
		process(dir, (File f) -> {
			return f.isDirectory() ? true : f.getName().startsWith("FrontSheet");
		});
		// 再处理其他文件
		process(dir, (File f) -> {
			return true;
		});
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1) + "\t" + s.size());
	}

	public void process(File dir, FileFilter filter) throws ParseException {
		// File[] fs = dir.listFiles((File f) -> {
		// return f.isDirectory() ? true : f.getName().startsWith("FrontSheet");
		// });
		File[] fs = dir.listFiles(filter);
		for (int i = 0; i < fs.length; i++) {
			File file = fs[i];
			if (file.isDirectory()) {
				process(file, filter);
			} else {
				// try {
				pp(file);
				// } catch (StringIndexOutOfBoundsException e) {
				// System.out.println("==============================================================================");
				// file.renameTo(new File(MUL_DIR + file.getName()));
				// }
			}
		}
	}

	private void pp(File file) throws ParseException {
		String pId = PatientUtil.extractPIdTag(file);
		System.out.println(pId + "\t" + file.getAbsolutePath());
		if (pId != null) {
			String fileName = file.getName();
			List<Patient> list = repository.findByPId(pId);
			if (list.size() == 0) {
				if (fileName.startsWith("FrontSheet")) {
					repository.save(PatientUtil.extractPatientFromFile(file));
					file.renameTo(new File(MOVE_DIR + file.getName()));
				} else
					System.out.println("no ffffff\t" + file.getName());
			} else {
				Patient p = list.get(0);
				if (fileName.startsWith("FrontSheet")) {
					// 首页信息
					p.addFront(PatientUtil.extractFrontPage(file));
				} else if (fileName.startsWith("HospitalRecord")) {
					// 入院信息
					p.addIn(PatientUtil.extractInFromFile(file));
				} else if (fileName.startsWith("OperationInfoRecord")) {
					// 手术信息
					p.addOperation(PatientUtil.extractOperationFromFile(file));
				} else if (fileName.startsWith("Discharge")) {
					// 出院信息
					p.addOut(PatientUtil.extractOutFromFile(file));
				} else
					System.out.println("errrrrr\t" + file.getName());
				repository.save(p);
				file.renameTo(new File(MOVE_DIR + file.getName()));
			}
			s.add(file.getName() + ":" + pId);
		}
	}
}
