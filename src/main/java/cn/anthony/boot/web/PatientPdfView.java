package cn.anthony.boot.web;

import cn.anthony.boot.domain.Patient;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

public class PatientPdfView extends AbstractITextPdfView {
	@Override
	public void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Patient patient = (Patient) model.get("patient");
		String fileName = patient.getName() + ".pdf";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		// 显示中文
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// BaseFont bfChinese = BaseFont.createFont("MSungStd-Light",
		// "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
		document.add(new Paragraph(patient.toString(), FontChinese));
	}
}
