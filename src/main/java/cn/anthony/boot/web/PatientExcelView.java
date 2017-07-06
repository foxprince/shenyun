package cn.anthony.boot.web;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import cn.anthony.util.ExcelUtil;

public class PatientExcelView extends AbstractView {
	public PatientExcelView() {
		setContentType("application/vnd.ms-excel");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/**
	 * Renders the Excel view, given the specified model.
	 */
	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Create a fresh workbook instance for this render step.
		XSSFWorkbook workbook = createWorkbook(model, request);
		// Delegate to application-provided document code.
		buildExcelDocument(model, workbook, request, response);
		// Set the content type.
		response.setContentType(getContentType());
		// Flush byte array to servlet output stream.
		renderWorkbook(workbook, response);
	}

	/**
	 * Template method for creating the POI {@link Workbook} instance.
	 * <p>
	 * The default implementation creates a traditional {@link HSSFWorkbook}.
	 * Spring-provided subclasses are overriding this for the OOXML-based
	 * variants; custom subclasses may override this for reading a workbook from
	 * a file.
	 * 
	 * @param model
	 *            the model Map
	 * @param request
	 *            current HTTP request (for taking the URL or headers into
	 *            account)
	 * @return the new {@link Workbook} instance
	 */
	protected XSSFWorkbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
		return new XSSFWorkbook();
	}

	/**
	 * The actual render step: taking the POI {@link Workbook} and rendering it
	 * to the given response.
	 * 
	 * @param workbook
	 *            the POI Workbook to render
	 * @param response
	 *            current HTTP response
	 * @throws IOException
	 *             when thrown by I/O methods that we're delegating to
	 */
	protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		workbook.write(out);
		// Closeable only implemented as of POI 3.10
		if (workbook instanceof Closeable) {
			((Closeable) workbook).close();
		}
	}

	protected void buildExcelDocument(Map<String, Object> model, XSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String excelName = "patient.xlsx";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
		List result = (List) model.get("result");
		String[] keys = (String[]) model.get("keys");
		String[] columnNames = (String[]) model.get("columnNames");
		ExcelUtil.createWorkBook(workbook, result, keys, columnNames);
	}
}