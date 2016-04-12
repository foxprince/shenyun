/*
 * Created on 2004-12-3
 */
package cn.anthony.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Anthony
 */
public class FileTools {
    /**
     * 锟侥硷拷锟斤拷锟斤拷
     * 
     * @param src
     *            File 源锟侥硷拷
     * @param dest
     *            File 目锟斤拷锟侥硷拷
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void copy(File src, File dest) throws FileNotFoundException, IOException {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
	try {
	    bis = new BufferedInputStream(new FileInputStream(src));
	    bos = new BufferedOutputStream(new FileOutputStream(dest));
	    byte[] b = new byte[2048];
	    int i = bis.read(b);
	    while (i > 0) {
		bos.write(b, 0, i);
		i = bis.read(b);
	    }
	} catch (FileNotFoundException fnfe) {
	    throw fnfe;
	} catch (IOException ie) {
	    throw ie;
	} finally {
	    if (bos != null)
		try {
		    bos.close();
		} catch (Exception e) {
		}
	    if (bis != null)
		try {
		    bis.close();
		} catch (Exception e) {
		}
	}
    }

    /**
     * 锟斤拷锟狡讹拷锟斤拷募锟斤拷锟揭伙拷锟侥匡拷锟铰凤拷锟斤拷锟斤拷锟斤拷目锟斤拷路锟斤拷锟斤拷锟斤拷锟节ｏ拷锟皆讹拷锟斤拷锟斤拷锟斤拷
     * 
     * @param src
     *            File[] 锟侥硷拷锟斤拷锟斤拷
     * @param destDir
     *            File 目锟斤拷路锟斤拷
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void copy(File[] src, File destDir) throws FileNotFoundException, IOException {
	if (!destDir.exists())
	    destDir.mkdirs();
	for (File element : src)
	    copy(element, new File(destDir, element.getName()));
    }

    /**
     * 锟斤拷锟狡讹拷锟斤拷募锟斤拷锟揭伙拷锟侥匡拷锟铰凤拷锟斤拷锟斤拷锟斤拷目锟斤拷路锟斤拷锟斤拷锟斤拷锟节ｏ拷锟皆讹拷锟斤拷锟斤拷锟斤拷
     * 
     * @param src
     *            String[]
     * @param destDir
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void copy(String[] src, String destDir) throws FileNotFoundException, IOException {
	File[] files = new File[src.length];
	for (int i = 0; i < src.length; i++)
	    files[i] = new File(src[i]);
	File dir = new File(destDir);
	copy(files, dir);
    }

    /**
     * 锟斤拷锟狡讹拷锟斤拷募锟斤拷锟揭伙拷锟侥匡拷锟铰凤拷锟斤拷锟斤拷锟斤拷目锟斤拷路锟斤拷锟斤拷锟斤拷锟节ｏ拷锟皆讹拷锟斤拷锟斤拷锟斤拷
     * 
     * @param src
     *            String[]
     * @param destDir
     *            File
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void copy(String[] src, File destDir) throws FileNotFoundException, IOException {
	File[] files = new File[src.length];
	for (int i = 0; i < src.length; i++)
	    files[i] = new File(src[i]);
	copy(files, destDir);
    }

    /**
     * 锟斤拷锟狡讹拷锟斤拷募锟斤拷锟揭伙拷锟侥匡拷锟铰凤拷锟斤拷锟斤拷锟斤拷目锟斤拷路锟斤拷锟斤拷锟斤拷锟节ｏ拷锟皆讹拷锟斤拷锟斤拷锟斤拷
     * 
     * @param src
     *            File[]
     * @param destDir
     *            String
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void copy(File[] src, String destDir) throws FileNotFoundException, IOException {
	File dir = new File(destDir);
	copy(src, dir);
    }

    /**
     * 锟斤拷取锟侥硷拷锟斤拷锟捷ｏ拷锟斤拷锟斤拷锟斤拷
     * 
     * @param f
     * @return
     */
    public static byte[] readBinaryFile(File f) {
	int f_len = (int) f.length();
	String fileName = f.getAbsolutePath();
	byte[] b = new byte[f_len];
	try {
	    if (!f.exists()) {
	    } else {
		RandomAccessFile rf = new RandomAccessFile(fileName, "r");
		rf.read(b, 0, f_len);
	    }
	} catch (IOException ie) {
	    ie.printStackTrace();
	}
	return b;
    }

    public static int quickLineTotal(String fileName) {
	int total = 0;
	FileReader fr;
	try {
	    fr = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(fr);
	    String ln;
	    while ((ln = br.readLine()) != null)
		total++;
	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return total;
    }

    public static List<String> quickReadFile(String fileName) {
	List<String> list = new LinkedList<String>();
	FileReader fr;
	try {
	    fr = new FileReader(fileName);
	    BufferedReader br = new BufferedReader(fr);
	    String ln;
	    while ((ln = br.readLine()) != null)
		list.add(ln);
	    br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return list;
    }

    public static Set readFromFile(String fileName) {
	Set set = new HashSet();
	try {
	    RandomAccessFile rf = new RandomAccessFile(fileName, "r");
	    String tmp = null;
	    while ((tmp = rf.readLine()) != null)
		set.add(new String(tmp.trim().getBytes("ISO8859_1"), "GBK"));
	    rf.close();
	} catch (IOException e) {
	}
	return set;
    }

    public static String readFromFile(String fileName, String encoding) {
	StringBuffer sb = new StringBuffer();
	try {
	    RandomAccessFile rf = new RandomAccessFile(fileName, "r");
	    String tmp = null;
	    while ((tmp = rf.readLine()) != null)
		sb.append((new String(tmp.trim().getBytes("ISO8859_1"), encoding)));
	    rf.close();
	} catch (IOException e) {
	}
	return sb.toString();
    }

    public static Set readFromFile(File f) {
	Set set = new TreeSet();
	try {
	    RandomAccessFile rf = new RandomAccessFile(f, "r");
	    String tmp = null;
	    while ((tmp = rf.readLine()) != null)
		set.add(new String(tmp.trim().getBytes("ISO8859_1"), "GBK"));
	    rf.close();
	} catch (IOException e) {
	}
	return set;
    }

    public static List readFromFileWithOrder(String fileName) {
	List set = new ArrayList();
	try {
	    RandomAccessFile rf = new RandomAccessFile(fileName, "r");
	    String tmp = null;
	    while ((tmp = rf.readLine()) != null)
		set.add(new String(tmp.getBytes("ISO8859_1"), "GBK"));
	    rf.close();
	} catch (IOException e) {
	}
	return set;
    }

    /*
     * trim:锟角凤拷锟斤拷丝崭锟
     */
    public static List readFromFile(String fileName, boolean trim) {
	List set = new ArrayList();
	try {
	    RandomAccessFile rf = new RandomAccessFile(fileName, "r");
	    String tmp = null;
	    while ((tmp = rf.readLine()) != null)
		set.add(new String((trim ? tmp.trim() : tmp).getBytes("ISO8859_1"), "GBK"));
	    rf.close();
	} catch (IOException e) {
	}
	return set;
    }

    public static void createFile(String fileName, String content) {
	File f = new File(fileName);
	try {
	    // if (f.exists())
	    // f.delete();
	    if (!f.exists())
		f.createNewFile();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, true), "GBK"), true);
	    out.println(content);
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void createFile(String fileName, String content, String coding) {
	File f = new File(fileName);
	try {
	    // if (f.exists())
	    // f.delete();
	    if (!f.exists())
		f.createNewFile();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, true), coding), true);
	    out.println(content);
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void createFile(String fileName, Collection contentSet) {
	File f = new File(fileName);
	try {
	    // if (f.exists())
	    // f.delete();
	    if (!f.exists())
		f.createNewFile();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, true), "GBK"), true);
	    Iterator it = (contentSet).iterator();
	    while (it.hasNext())
		out.println((String) it.next());
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void createFile(String fileName, Collection contentSet, boolean append) {
	File f = new File(fileName);
	try {
	    if (f.exists() && !append)
		f.delete();
	    if (!f.exists())
		f.createNewFile();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, true), "GBK"), append);
	    Iterator it = (contentSet).iterator();
	    while (it.hasNext())
		out.println((String) it.next());
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void streamDo(String dir) {
	File d = new File(dir);
	if (!d.isDirectory()) {
	    System.out.println("need directory only!");
	    return;
	}
	String dPath = d.getAbsolutePath();
	String[] names = d.list();
	for (String element : names) {
	    File f = new File(dPath + "\\" + element + ".do");
	    try {
		System.out.println(f.createNewFile());
	    } catch (IOException e) {
		System.out.println(f.getAbsolutePath());
	    }
	}
    }

    /**
     * 锟斤拷一锟斤拷锟侥硷拷锟街革拷啥锟斤拷锟侥硷拷锟斤拷锟侥硷拷锟斤拷缀锟皆讹拷锟斤拷_n
     * 
     * @param f
     * @param rows
     * @return
     */
    public static List split(File f, int rows) {
	List l = new LinkedList();
	String fileName = f.getAbsolutePath();
	String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
	Set totalSet = readFromFile(fileName);
	Set splitSet = splitSet(totalSet, rows);
	Iterator it = splitSet.iterator();
	int i = 1;
	while (it.hasNext()) {
	    List perList = (List) it.next();
	    Set perSet = new HashSet(perList);
	    String name = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + (i++) + "." + ext;
	    createFile(name, perSet);
	    l.add(name);
	}
	return l;
    }

    public static Set splitSet(Set s, int per) {
	Set set = new HashSet();
	List l = new LinkedList(s);
	int from = 0;
	int end = from + per > l.size() ? l.size() : from + per;
	while (from < l.size()) {
	    List sublist = l.subList(from, end);
	    set.add(sublist);
	    from = end + 1;
	    end = from + per > l.size() ? l.size() : from + per;
	}
	return set;
    }

    public static Set splitList(List s, int per) {
	Set set = new HashSet();
	List l = new LinkedList(s);
	int from = 0;
	int end = from + per > l.size() ? l.size() : from + per;
	while (from < l.size()) {
	    List sublist = l.subList(from, end);
	    set.add(sublist);
	    from = end;
	    end = from + per > l.size() ? l.size() : from + per;
	}
	return set;
    }

    /*
     * 锟捷癸拷锟饺∫伙拷锟侥柯硷拷碌锟斤拷募锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟斤拷
     */
    public static Set readAllFiles(File f, FilenameFilter filter) {
	Set s = new HashSet();
	if (f.isDirectory()) {
	    File[] fs = f.listFiles(filter);
	    for (File f0 : fs) {
		if (f0.isDirectory())
		    s.addAll(readAllFiles(f0, filter));
		else
		    s.add(f0);
	    }
	}
	return s;
    }

    public static Collection readAllFileSet(File f, FilenameFilter filter) {
	List s = new LinkedList();
	if (f.isDirectory()) {
	    File[] fs = f.listFiles(filter);
	    for (File f0 : fs) {
		if (f0.isDirectory())
		    s.addAll(readAllFileSet(f0, filter));
		else {
		    s.add("************************************************");
		    s.add("*" + f0.getName() + "*");
		    s.add("************************************************");
		    s.addAll(readFromFileWithOrder(f0.getAbsolutePath()));
		}
	    }
	}
	return s;
    }

    protected void filterTest(String dir) {
	FilenameFilter filter = new FilenameFilter() {
	    @Override
	    public boolean accept(File dir, String name) {
		return name.indexOf("_") < 0;
	    }
	};
	File f = new File(dir);
	File[] fs = f.listFiles(filter);
	for (File element : fs) {
	    final String fname = element.getName();
	    final int to = fname.indexOf(".");
	    System.out.println(fname);
	    FilenameFilter filter0 = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
		    return (name.indexOf(fname.substring(0, to)) == 0) && !name.equals(fname);
		}
	    };
	    File[] fs0 = f.listFiles(filter0);
	    for (File element0 : fs0)
		System.out.println("\t" + element0.getName());
	}
    }

    static FileFilter filter = new FileFilter() {
	@Override
	public boolean accept(File dir) {
	    if (dir.isDirectory())
		return true;
	    else
		return dir.getName().indexOf("wav") > 0;
	}
    };

    public static void process(File dir) {
	File[] fs = dir.listFiles(filter);
	for (int i = 0; i < fs.length; i++) {
	    File file = fs[i];
	    String name = file.getName();
	    if (file.isDirectory()) {
		System.out.println("add node : " + name);
		// menu.add(parent.getID(), node);
		process(file);
	    } else {
		name = name.substring(0, name.indexOf("."));
		System.out.println("add leaf : " + name);
	    }
	}
    }

    public static String getFileSize(String filename, String type) {
	File f = new File(filename);
	if (f.exists()) {
	    try {
		FileInputStream fis = null;
		fis = new FileInputStream(f);
		long bs = fis.available();
		if (type.equals("M")) {
		    String r = "" + ((double) bs / 1024 / 1024);
		    int i = r.indexOf(".");
		    int l = r.length();
		    if (l > i + 3)
			return r.substring(0, i + 3);
		    else
			return r;
		} else if (type.equals("K")) {
		    String r = "" + ((double) bs / 1024);
		    int i = r.indexOf(".");
		    int l = r.length();
		    if (l > i + 3)
			return r.substring(0, i + 3);
		    else
			return r;
		}
	    } catch (FileNotFoundException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	return "0";
    }

    public static void main(String[] args) {
	String fileName = "/Users/hch/Documents/workspace/smsGroup/WebRoot/WEB-INF/lib";
	File dir = new File(fileName);
	StringBuffer sb = new StringBuffer("export CLASSPATH=$CLASSPATH:");
	for (String s : dir.list()) {
	    if (s.endsWith("jar") || s.endsWith("zip"))
		sb.append("$WEBINF/lib/" + s + ":");
	}
	sb.append("$HOME/tomcat/lib/servlet-api.jar");
	System.out.println(sb.toString());
    }

    public static void createFile(String filepath, String content, boolean append) {
	File f = new File(filepath);
	try {
	    // if (f.exists())
	    // f.delete();
	    if (!f.exists())
		f.createNewFile();
	    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, append), "GBK"), true);
	    out.println(content);
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
