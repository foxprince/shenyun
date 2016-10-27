package cn.anthony.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String columnNames[]) {
	return createWorkBook(new XSSFWorkbook(), list, keys, columnNames);
    }

    /**
     * 创建excel文档，
     * 
     * @param list
     *            数据
     * @param keys
     *            list中map的key数组集合
     * @param columnNames
     *            excel的列名
     */
    public static Workbook createWorkBook(XSSFWorkbook wb, List<Map<String, Object>> list, String[] keys,
	    String columnNames[]) {

	// 创建第一个sheet（页），并命名
	XSSFSheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
	// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	for (int i = 0; i < keys.length; i++) {
	    sheet.setColumnWidth((short) i, (short) (35.7 * 150));
	}

	// 创建第一行
	Row row = sheet.createRow((short) 0);

	// 创建两种单元格格式
	CellStyle cs = wb.createCellStyle();
	CellStyle cs2 = wb.createCellStyle();

	// 创建两种字体
	Font f = wb.createFont();
	Font f2 = wb.createFont();

	// 创建第一种字体样式（用于列名）
	f.setFontHeightInPoints((short) 10);
	f.setColor(IndexedColors.BLACK.getIndex());
	f.setBoldweight(Font.BOLDWEIGHT_BOLD);

	// 创建第二种字体样式（用于值）
	f2.setFontHeightInPoints((short) 10);
	f2.setColor(IndexedColors.BLACK.getIndex());

	// Font f3=wb.createFont();
	// f3.setFontHeightInPoints((short) 10);
	// f3.setColor(IndexedColors.RED.getIndex());

	// 设置第一种单元格的样式（用于列名）
	cs.setFont(f);
	cs.setBorderLeft(CellStyle.BORDER_THIN);
	cs.setBorderRight(CellStyle.BORDER_THIN);
	cs.setBorderTop(CellStyle.BORDER_THIN);
	cs.setBorderBottom(CellStyle.BORDER_THIN);
	cs.setAlignment(CellStyle.ALIGN_CENTER);

	// 设置第二种单元格的样式（用于值）
	cs2.setFont(f2);
	cs2.setBorderLeft(CellStyle.BORDER_THIN);
	cs2.setBorderRight(CellStyle.BORDER_THIN);
	cs2.setBorderTop(CellStyle.BORDER_THIN);
	cs2.setBorderBottom(CellStyle.BORDER_THIN);
	cs2.setAlignment(CellStyle.ALIGN_CENTER);
	// 设置列名
	for (int i = 0; i < columnNames.length; i++) {
	    Cell cell = row.createCell(i);
	    cell.setCellValue(columnNames[i]);
	    cell.setCellStyle(cs);
	}
	// 设置每行每列的值
	for (short i = 1; i < list.size(); i++) {
	    // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	    // 创建一行，在页sheet上
	    Row row1 = sheet.createRow(i);
	    // 在row行上创建一个方格
	    for (short j = 0; j < keys.length; j++) {
		Cell cell = row1.createCell(j);
		cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
		cell.setCellStyle(cs2);
	    }
	}
	return wb;
    }

    /**
     * 读取Excel表格表头的内容
     * 
     * @param InputStream
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
	Workbook wb;
	Sheet sheet;
	Row row;
	String[] title = null;
	try {
	    wb = WorkbookFactory.create(is);
	    sheet = wb.getSheetAt(0);
	    row = sheet.getRow(0);
	    // 标题总列数
	    int colNum = row.getPhysicalNumberOfCells();
	    System.out.println("colNum:" + colNum);
	    title = new String[colNum];
	    for (int i = 0; i < colNum; i++) {
		// title[i] = getStringCellValue(row.getCell((short) i));
		title[i] = getCellFormatValue(row.getCell((short) i));
	    }
	} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
	    e.printStackTrace();
	}

	return title;
    }

    /**
     * 读取Excel数据内容
     * 
     * @param InputStream
     * @return Map 包含单元格数据内容的Map对象
     */
    public static Map<Integer, List<String>> readExcelContent(InputStream is) {
	Workbook wb;
	Sheet sheet;
	Row row;
	Map<Integer, List<String>> content = new HashMap<Integer, List<String>>();
	try {
	    wb = WorkbookFactory.create(is);
	    sheet = wb.getSheetAt(0);
	    // 得到总行数
	    int rowNum = sheet.getLastRowNum();
	    row = sheet.getRow(0);
	    int colNum = row.getPhysicalNumberOfCells();
	    // 正文内容应该从第二行开始,第一行为表头的标题
	    for (int i = 1; i <= rowNum; i++) {
		row = sheet.getRow(i);
		if (row != null) {
		    int j = 0;
		    List<String> l = new ArrayList<String>();
		    while (j < colNum) {
			l.add(getStringCellValue(row.getCell(j)).trim());
			j++;
		    }
		    content.put(i, l);
		}
	    }
	} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
	    e.printStackTrace();
	}

	return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private static String getStringCellValue(Cell cell) {
	String strCell = "";
	if (cell != null) {
	    final DataFormatter df = new DataFormatter();
	    strCell = df.formatCellValue(cell);
	    /*
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
		strCell = cell.getStringCellValue();
		break;
	    case Cell.CELL_TYPE_NUMERIC:
		if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell))
		    strCell = DateUtil.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()),"yyyy-MM-dd");
		else
		    strCell = String.valueOf(cell.getNumericCellValue());
		break;
	    case Cell.CELL_TYPE_BOOLEAN:
		strCell = String.valueOf(cell.getBooleanCellValue());
		break;
	    case Cell.CELL_TYPE_BLANK:
		strCell = "";
		break;
	    default:
		strCell = "";
		break;
	    }
	    */
	}
	return strCell;
    }

    /**
     * 根据HSSFCell类型设置数据
     * 
     * @param cell
     * @return
     */
    private static String getCellFormatValue(Cell cell) {
	String cellvalue = "";
	if (cell != null) {
	    // 判断当前Cell的Type
	    switch (cell.getCellType()) {
	    // 如果当前Cell的Type为NUMERIC
	    case Cell.CELL_TYPE_NUMERIC:
	    case Cell.CELL_TYPE_FORMULA: {
		// 判断当前的cell是否为Date
		if (HSSFDateUtil.isCellDateFormatted(cell)) {
		    // 如果是Date类型则，转化为Data格式

		    // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
		    // cellvalue = cell.getDateCellValue().toLocaleString();

		    // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
		    Date date = cell.getDateCellValue();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    cellvalue = sdf.format(date);

		}
		// 如果是纯数字
		else {
		    // 取得当前Cell的数值
		    cellvalue = String.valueOf(cell.getNumericCellValue());
		}
		break;
	    }
		// 如果当前Cell的Type为STRIN
	    case Cell.CELL_TYPE_STRING:
		// 取得当前的Cell字符串
		cellvalue = cell.getRichStringCellValue().getString();
		break;
	    // 默认的Cell值
	    default:
		cellvalue = " ";
	    }
	} else {
	    cellvalue = "";
	}
	return cellvalue;

    }

    public static void main(String[] args) {
	try {
	    // 对读取Excel表格标题测试
	    InputStream is = new FileInputStream("E:\\project\\神云系统\\二期\\病房李组\\all.xlsx");
	    ExcelUtil excelReader = new ExcelUtil();
	    String[] title = excelReader.readExcelTitle(is);
	    System.out.println("获得Excel表格的标题:");
	    int j = 0;
	    for (String s : title) {
		//System.out.println("public String "+s+";");
		System.out.print(j++ + ": " + s + "\t");
	    }
	    System.out.println();
	    // 对读取Excel表格内容测试
	    InputStream is2 = new FileInputStream("E:\\project\\神云系统\\二期\\病房李组\\all.xlsx");
	    Map<Integer, List<String>> map = readExcelContent(is2);
	    System.out.println("获得Excel表格的内容:");
	    for (int i = 1; i <= map.size(); i++) {
		//System.out.println(map.get(i));
	    }

	} catch (FileNotFoundException e) {
	    System.out.println("未找到指定路径的文件!");
	    e.printStackTrace();
	}
    }
}
