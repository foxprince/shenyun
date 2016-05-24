package cn.anthony.boot.web;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import cn.anthony.boot.domain.Patient;

public class PatientPdfView extends AbstractPdfView {
    @Override
    public void buildPdfDocument(Map model, Document document, PdfWriter writer, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String excelName = "用户信息.pdf";
	// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));

	Patient patient = (Patient) model.get("patient");
	// 显示中文
	BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(bfChinese, 12, com.lowagie.text.Font.NORMAL);

	document.add(new Paragraph(patient.toString(), FontChinese));

    }

}
