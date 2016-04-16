package cn.anthony.boot;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.anthony.boot.domain.Patient;
import cn.anthony.boot.repository.PatientRepository;
import cn.anthony.boot.util.PatientUtil;

@SpringBootApplication
public class TestService implements CommandLineRunner {
    private static final String MOVE_DIR = "E:\\project\\神云系统\\data\\已处理\\";
    private static final String MUL_DIR = "E:\\project\\神云系统\\data\\重复住院\\";

    @Autowired
    private PatientRepository repository;
    private Set<String> s = new HashSet<String>();

    public static void main(String[] args) {
	SpringApplication.run(TestService.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
	String srcDir = "E:\\project\\神云系统\\data\\数据导出-2015-1-1日-8月10日";
	File dir = new File(srcDir);
	// 先处理首页文件，处理完移走
	process(dir, (File f) -> {
	    return f.isDirectory() ? true : f.getName().startsWith("FrontSheet");
	});
	// 再处理其他文件
	process(dir, (File f) -> {
	    return true;
	});
	// System.out.println(s);
	System.out.println(s.size());
    }



    public void process(File dir, FileFilter filter) {
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

    private void pp(File file) {
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
	    }
	    else {
		Patient p = list.get(0);
		if (fileName.startsWith("FrontSheet")) {
		    // TODO 更新首页信息
		    file.renameTo(new File(MUL_DIR + file.getName()));
		} else if (fileName.startsWith("HospitalRecord")) {
		    // 入院信息
		    p.addIn(PatientUtil.extractInFromFile(file));
		    file.renameTo(new File(MOVE_DIR + file.getName()));
		} else if (fileName.startsWith("OperationInfoRecord")) {
		    // 手术信息
		    p.addOperation(PatientUtil.extractOperationFromFile(file));
		    file.renameTo(new File(MOVE_DIR + file.getName()));
		} else if (fileName.startsWith("Discharge")) {
		    // 出院信息
		    p.addOut(PatientUtil.extractOutFromFile(file));
		    file.renameTo(new File(MOVE_DIR + file.getName()));
		}
		repository.save(p);
	    }
	    s.add(file.getName() + ":" + pId);
	}
    }


}
