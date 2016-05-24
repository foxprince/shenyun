package cn.anthony.boot.web;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import cn.anthony.util.ExcelUtil;


public class PatientExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	String excelName = "patient.xls";
	// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
	response.setContentType("APPLICATION/OCTET-STREAM");
	response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
	List result = (List) model.get("result");
	String[] keys = (String[]) model.get("keys");
	String[] columnNames = (String[]) model.get("columnNames");
	ExcelUtil.createWorkBook(workbook, result, keys, columnNames);
    }

}