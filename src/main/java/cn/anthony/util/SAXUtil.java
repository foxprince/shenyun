package cn.anthony.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXUtil {
	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			MyHandler handler = new MyHandler();
			// E:\project\神云系统\data\待处理\FrontSheet_000024837100_3.xml
			// E:\project\神云系统\data\已处理\FrontSheet_000472457900_1.xml
			handler.type = "front";
			File f = new File("E:\\project\\神云系统\\data\\2015已处理\\FrontSheet_000024837100_3.xml");
			saxParser.parse(f, handler);
			List<Object> l = handler.structerList;
			frontHandle(handler, f);
			System.out.println(StringTools.formatCollection(l));
			// FrontPage fp = new FrontPage();
			// RefactorUtil.setObjectValue(fp, handler.m);
			// System.out.println(StringTools.formatMap(RefactorUtil.getObjectParaMap(fp)));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getFrontPageMap(File f) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			MyHandler handler = new MyHandler();
			handler.type = "front";
			saxParser.parse(f, handler);
			return handler.m;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取到Front的注释值
	 * 
	 * @param handler
	 * @param f
	 * @throws IOException
	 */
	private static void frontHandle(MyHandler handler, File f) throws IOException {
		ReversedLinesFileReader fr = new ReversedLinesFileReader(f);
		String ch;
		Front t = null, t0;
		boolean first = false, second = false;
		Pattern p = Pattern.compile("<text>(.*?)[:|：]{0,1}</text>");
		Matcher m = null;// p.matcher("<text>科室</text>");
		// System.out.println(m.matches());
		do {
			ch = fr.readLine();
			if ((t0 = hit(handler.structerList, ch)) != null) {
				first = true;
				t = t0;
				continue;
			}
			// if (first && (t = hit(handler.structerList, ch)) != null) {
			// second = true;
			// }
			if (ch != null && first && (m = p.matcher(ch)).find()) {
				// System.out.println(t.toString() + "\t" + m.group(1));
				second = false;
				first = false;
				t.comment = m.group(1);
			}
		} while (ch != null);
		fr.close();
	}

	private static Front hit(List<Object> l, String ch) {
		for (Object f : l) {
			if (ch != null && ch.startsWith("<key") && ch.contains("id=\"" + ((Front) f).tag + "\"")) {
				// System.out.println(ch);
				return (Front) f;
			}
		}
		return null;
	}
}

class Front {
	public String tag;
	public String text;
	public String comment;

	public Front(String tag, String text) {
		super();
		this.tag = tag;
		this.text = text;
	}

	public String toString() {
		// return comment + ":\t" + tag + "=\t" + text;
		// return "public String " + tag + ";//" + comment;
		StringBuilder sb = new StringBuilder();
		sb.append("//" + comment + "\n");
		sb.append("public String " + tag + "=" + text + ";");
		// sb.append("put(\"" + tag + "\",\"" + comment + "\");");
		return sb.toString();
	}
}

class Hospital {
	String defaultText;
	String field;
	String text;
	String value;

	public Hospital(String defaultText, String filed, String text, String value) {
		super();
		this.defaultText = defaultText;
		this.field = filed;
		this.text = text;
		this.value = value;
	}

	public String toString() {
		return field + "\t//" + defaultText + ":" + text + "\t" + value;
	}
}

class MyHandler extends DefaultHandler {
	String type = null;
	public List<Object> structerList = null;
	public Map<String, String> m = new TreeMap<String, String>();

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("struct")) {
			if (structerList == null)
				structerList = new ArrayList<>();
			if (type.equals("front")) {
				structerList.add(new Front(attributes.getValue("id"), attributes.getValue("text")));
				m.put(attributes.getValue("id"), attributes.getValue("text"));
			} else if (type.equals("hospital"))
				structerList.add(new Hospital(attributes.getValue("defaulttext"), attributes.getValue("field"),
						attributes.getValue("text"), attributes.getValue("value")));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
	}
}
