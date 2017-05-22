package xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandel extends DefaultHandler {

	private List<Book> books = null;
	private Book book = null;
	private String preTag = null;
	
	public List<Book> getBooks() {
		return books;
	}
	
	public List<Book> getBooks(InputStream xmlStream) throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		MyHandel myHandel = new MyHandel();
		parser.parse(xmlStream, myHandel);
		
		return myHandel.getBooks();
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if(preTag != null){
			String context = new String(ch, start, length);
			
			if("name".equals(preTag)){
				book.setName(context);
			}else if("price".equals(preTag)){
				book.setPrice(Float.parseFloat(context));
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("结束Document");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if("book".equals(qName)){
			books.add(book);
			book = null;
		}
		preTag = null;//////////////////
		
		System.out.println("结束Element:" + qName);
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始Document");
		books = new ArrayList<Book>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("开始Element:" + qName);
		
		if("book".equals(qName)){
			book = new Book();
			book.setId(Integer.parseInt(attributes.getValue(0)));
		}
		
		preTag = qName;
	}
	
	
	
}
