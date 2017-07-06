package cn.anthony.boot.web;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.servlet.view.AbstractView;

public class ZipView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/octet-stream");
		String name = URLEncoder.encode((String) model.get("patientNo"), "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + name + ".zip");
		ServletOutputStream os = response.getOutputStream();
		File f = (File) model.get("file");
		IOUtils.copy(new FileInputStream(f), os);
		// FileUtils.forceDelete(f);
		os.flush();
		os.close();
	}
}