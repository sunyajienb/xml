package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class TestMyHandel implements XmlInter{

	public static void main(String[] args) {
		TestMyHandel testMyHandel = new TestMyHandel();
		
		String fileName = "E:\\software\\eclipse\\AProgram\\test1\\src\\xml\\book.xml";
		testMyHandel.parse(fileName);
		
		List<Book> books = new ArrayList<Book>();
    	Book book1 = new Book(1,"thinkjava",50.2f);
    	books.add(book1);
    	Book book2 = new Book(2,"¥˙¬Î÷ÿππ",30.2f);
    	books.add(book2);
    	String fileNameCreate = "E:/software/eclipse/AProgram/test1/src/xml/bookSaxcreate.xml";
		testMyHandel.create(books, fileNameCreate);
	}
	@Override
	public void parse(String fileName) {
		MyHandel myHandel = new MyHandel();
			
		try {
			//String fileName = "E:\\software\\eclipse\\AProgram\\test1\\src\\xml\\book.xml";
			InputStream inputStream = new FileInputStream(fileName);
			
			List<Book> books = myHandel.getBooks(inputStream);
			for(Book book:books){
				System.out.println(book);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void create(List<Book> books, String fileName) {
		SAXTransformerFactory saxTransformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		
		try {
			 TransformerHandler transformerHandler = saxTransformerFactory.newTransformerHandler();
			 Transformer transformer = transformerHandler.getTransformer();
			 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			 
			 File file = new File(fileName);
			 transformerHandler.setResult(new StreamResult(file));
			 AttributesImpl attrs = new AttributesImpl();
			 
			 transformerHandler.startDocument();
			 transformerHandler.startElement("", "", "books", attrs);
			 
			 for(Book book: books){
				 attrs.clear();
				 attrs.addAttribute("", "", "id", "", String.valueOf(book.getId()));				 
				 transformerHandler.startElement("", "", "book", attrs);
				 
				 attrs.clear();
				 transformerHandler.startElement("", "", "name", attrs);
				 transformerHandler.characters(book.getName().toCharArray(), 0, book.getName().length());
				 transformerHandler.endElement("", "", "name");
				 
				 attrs.clear();
				 transformerHandler.startElement("", "", "price", attrs);
				 transformerHandler.characters(String.valueOf(book.getPrice()).toCharArray(), 0, String.valueOf(book.getPrice()).length());
				 transformerHandler.endElement("", "", "price");
				 
				 transformerHandler.endElement("", "", "book");
			 }
			 transformerHandler.endElement("", "", "books");
			 transformerHandler.endDocument();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
