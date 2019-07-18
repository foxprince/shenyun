package cn.anthony.boot;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.domain.QInHospital;
import cn.anthony.boot.domain.QPatient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.service.PatientService;
import cn.anthony.boot.service.TotalService;
import cn.anthony.boot.util.PatientUtil;
import cn.anthony.util.RefactorUtil;

@SpringBootApplication
public class TestService implements CommandLineRunner {
	private static final String srcDir = "/Users/zj/tmp/KYBLSJ/";
	private static final String MOVE_DIR = "/Users/zj/tmp/已处理/";
	private static final String ERR_DIR = "/Users/zj/tmp/格式错误/";
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
			processTool();
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
				pre = ((StringPath) f.get(QPatient.patient.inRecords.any())).eq(value).and(pre);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return pre;
	}

	private void processTool() throws ParseException, IOException {
		File dir = new File(srcDir);
		long t1 = System.currentTimeMillis();
		// 先处理首页文件，处理完移走
		process(dir, (File f) -> {
			return f.isDirectory() ? true : f.getName().startsWith("FrontSheet");
		});
		// 再处理其他文件
		process(dir, (File f) -> {
			return !f.getName().endsWith(".DS_Store");
		});
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1) + "\t" + s.size());
	}

	public void process(File dir, FileFilter filter) throws ParseException, IOException {
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

	private void pp(File file) throws ParseException, IOException {
		String pId = PatientUtil.extractPIdTag(file);
		Patient p = null;
		if(pId==null) {
			String s = file.getName().substring(file.getName().indexOf("_"));
			p = service.findBySrcFileLike("FrontSheet"+s);
			if(p!=null)
				pId = p.getpId();
		}
		File destFile = new File(MOVE_DIR+file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(srcDir)+srcDir.length()));
		File errFile = new File(ERR_DIR+file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(srcDir)+srcDir.length()));
		System.out.println(pId + "\t" + file.getAbsolutePath());
		if (pId != null) {
			String fileName = file.getName();
			//List<Patient> list = repository.findByPId(pId);
			if (p==null) {
				if (fileName.startsWith("FrontSheet")) {
					repository.save(PatientUtil.extractPatientFromFile(file));
					//file.renameTo(destFile);
					FileUtils.moveFile(file, destFile); 
				} else {
					//非首页信息，但是在数据库里没有首页纪录
					System.out.println("no ffffff\t" + file.getName());
					FileUtils.moveFile(file, errFile);
				}
			} else {
				//Patient p = list.get(0);
				//try{
					if (fileName.startsWith("FrontSheet")) {
						// 首页信息
						p.addFront(PatientUtil.extractFrontPageWithText(file));
					} else if (fileName.startsWith("HospitalRecord")) {
						// 入院信息
						p.addIn(PatientUtil.extractInFromFile(file));
					} else if (fileName.startsWith("OperationInfoRecord")||fileName.startsWith("OperationInfoRec")) {
						// 手术信息
						p.addOperation(PatientUtil.extractOperationFromFile(file));
					} else if (fileName.startsWith("Discharge")) {
						// 出院信息
						p.addOut(PatientUtil.extractOutFromFile(file));
					} else
						System.out.println("errrrrr\t" + file.getName());
					repository.save(p);
					FileUtils.moveFile(file, destFile);
//				}catch(StringIndexOutOfBoundsException e){
//					e.printStackTrace();
//					FileUtils.moveFile(file, errFile);
//				}
			}
			s.add(file.getName() + ":" + pId);
		}
		else
			FileUtils.moveFile(file, errFile);
	}
}
