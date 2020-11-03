package cn.anthony.boot.util;

import cn.anthony.util.FileTools;
import cn.anthony.util.StringTools;
import cn.anthony.util.http.HTTPUtil;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PageUtil {
	private static final String GENERATE_DIR = "E:\\project\\神云系统\\generate\\";
	private static final String TEMPLATE_FILE = "E:\\project\\神云系统\\patient.html";
	private static final String TEMPLATE_DIR = "E:\\project\\神云系统\\patient_files\\";
	private static final String PRINT_DIV = "<div class='row no-print'> <div class='col-xs-12'><a href='./print?id=${patient.id}' target='_blank' class='btn btn-default'><i class='fa fa-print'></i> 打印</a><a id='generatePdfBtn'  onclick='test('${patient.id}')' class='btn btn-primary pull-right' style='margin-right: 5px;'><i class='fa fa-download'></i> 导出压缩文件</a> </div> </div> ";

	public static void main(String[] args) {
		//
		// zip();
		try {
			// compressZipfile("E:\\project\\神云系统\\patient_files\\","E:\\project\\神云系统\\demo.zip");
			System.out.println(generateZip("574c4571ad2a809b716d41e4", "184470"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String generateZip(String id, String patientNo) {
		// 建目录
		File f = new File(GENERATE_DIR + patientNo);
		f.mkdir();
		f = new File(GENERATE_DIR + patientNo + "\\patient_files");
		f.mkdir();
		try {
			FileUtils.copyDirectory(new File(TEMPLATE_DIR), f);
			f = new File(GENERATE_DIR + patientNo + "\\pacs");
			f.mkdir();
			createStaticIndex(id, patientNo);
			compressZipfile(GENERATE_DIR + patientNo + "\\", GENERATE_DIR + patientNo + ".zip");
			// 删除目录
			f = new File(GENERATE_DIR + patientNo);
			FileUtils.forceDelete(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return GENERATE_DIR + patientNo + ".zip";
	}

	public static String createStaticIndex(String id, String patientNo) {
		String url = "http://127.0.0.1/patient/?id=" + id;
		String t = HTTPUtil.get(url);
		String start = "<!-- Main content -->";
		String end = "<!-- /.content -->";
		String body = StringTools.e(new StringBuilder(t), start, end);
		body = StringUtils.replace(body, PRINT_DIV, "");
		body = StringUtils.replace(body, "../resources/dist/img", "./patient_files");
		body = StringUtils.replace(body, "../resources/pacs", "./pacs");
		for (File srcFile : PatientUtil.getPacsFiles(patientNo))
			try {
				FileUtils.copyDirectoryToDirectory(srcFile, new File(GENERATE_DIR + patientNo + "\\pacs"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		String content = FileTools.readFromFile(TEMPLATE_FILE, "UTF-8");
		content = StringUtils.replace(content, "${section content}", body);
		String fileName = GENERATE_DIR + patientNo + "\\" + patientNo + ".html";
		FileTools.createFile(fileName, content, "UTF-8");
		return fileName;
	}

	public static void zip() {
		ZipArchiveOutputStream zipOutput = null;
		try {
			String folderPath = "E:\\project\\神云系统\\patient_files";
			File zipFile = new File("E:\\project\\神云系统\\demo.zip");
			zipOutput = (ZipArchiveOutputStream) new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP,
					new FileOutputStream(zipFile));
			zipOutput.setEncoding("UTF-8");
			zipOutput.setUseZip64(Zip64Mode.AsNeeded);
			File[] files = new File(folderPath).listFiles();
			for (File file : files) {
				InputStream in = null;
				try {
					if (file.isFile()) {
						in = new FileInputStream(file);
						ZipArchiveEntry entry = new ZipArchiveEntry(file, file.getName());
						zipOutput.putArchiveEntry(entry);
						IOUtils.copy(in, zipOutput);
						zipOutput.closeArchiveEntry();
					}
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e) {
						}
					}
				}
			}
			zipOutput.finish();
			zipOutput.close();
		} catch (Exception e) {
			if (zipOutput != null) {
				try {
					zipOutput.close();
				} catch (Exception e1) {
				}
			}
			e.printStackTrace();
		}
	}

	public static void compressZipfile(String sourceDir, String outputFile) throws IOException, FileNotFoundException {
		ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(outputFile));
		compressDirectoryToZipfile(sourceDir, sourceDir, zipFile);
		IOUtils.closeQuietly(zipFile);
	}

	private static void compressDirectoryToZipfile(String rootDir, String sourceDir, ZipOutputStream out)
			throws IOException, FileNotFoundException {
		for (File file : new File(sourceDir).listFiles()) {
			if (file.isDirectory()) {
				compressDirectoryToZipfile(rootDir, sourceDir + file.getName() + File.separator, out);
			} else {
				ZipEntry entry = new ZipEntry(sourceDir.replace(rootDir, "") + file.getName());
				out.putNextEntry(entry);
				FileInputStream in = new FileInputStream(sourceDir + file.getName());
				IOUtils.copy(in, out);
				IOUtils.closeQuietly(in);
			}
		}
	}
}
