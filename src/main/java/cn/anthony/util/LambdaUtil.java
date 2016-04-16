package cn.anthony.util;

import java.io.File;
import java.util.function.Function;

public class LambdaUtil {
    public static <T> void doWithDir(File dir, Function<File, T> f) {
	File[] fs = dir.listFiles();
	for (int i = 0; i < fs.length; i++) {
	    File file = fs[i];
	    if (file.isDirectory()) {
		doWithDir(file, f);
	    } else {
		f.apply(file);
	    }
	}
    }

}
