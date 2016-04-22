package cn.anthony.boot;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.util.PatientUtil;

//@SpringBootApplication
public class TestService implements CommandLineRunner {
    private static final String MOVE_DIR = "E:\\project\\神云系统\\data\\2014已处理\\";
    private static final String MUL_DIR = "E:\\project\\神云系统\\data\\重复住院\\";

    @Autowired
    private PatientRepository repository;
    private Set<String> s = new HashSet<String>();

    public static void main(String[] args) {
	SpringApplication.run(TestService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
	// String id = "571581aa4ea3f8d11a838059";
	// Patient p = repository.findOne(id);
	// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(p)));
	processTool();
    }

    private void processTool() throws ParseException {
	String srcDir = "E:\\project\\神云系统\\KYBLSJ201405-201412\\KYBLSJ";
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
	// System.out.println(s);
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
		pp(file);
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
		}
 else
		    System.out.println("no ffffff\t" + file.getName());
	    }
	    else {
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
