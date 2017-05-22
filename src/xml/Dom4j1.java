package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author admin
 * 解析xml
 * 生成xml
 */
public class Dom4j1 implements XmlInter{

	public static void main(String[] args) {
		Dom4j1 dom4j1 = new Dom4j1();
		
		String fileName = "E:\\software\\eclipse\\AProgram\\test1\\src\\xml\\User.xml";
		dom4j1.parse(fileName);

		List<Book> books = new ArrayList<Book>();
    	Book book1 = new Book(1,"thinkjava",50.2f);
    	books.add(book1);
    	Book book2 = new Book(2,"代码重构",30.2f);
    	books.add(book2);
    	String fileNameCreate = "E:/software/eclipse/AProgram/test1/src/xml/bookDom4jcreate.xml";
    	dom4j1.create(books, fileNameCreate);
	}
	@Override
	public void parse(String fileName){
		SAXReader saxReader = new SAXReader();
		
		try {
			Document document = saxReader.read(new File(fileName));
			Element element = document.getRootElement();
		
			Iterator<Element> it = element.elementIterator();
			while(it.hasNext()){
				System.out.println("--------------------");
				Element element2 = it.next();
				
				System.out.println(element2.attribute("id").getName() + ":" + element2.attribute("id").getValue());
				
				Iterator<Element> it2 = element2.elementIterator();
				while(it2.hasNext()){
					Element element3 = it2.next();
					System.out.println(element3.getName() + ":" + element3.getText());
				}
			}
		
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void create(List<Book> books, String fileName) {
		Document document = DocumentHelper.createDocument();
		//添加根节点
		Element booksE = document.addElement("books");
		
		for(Book book: books){
			Element bookE = booksE.addElement("book");//添加子节点
			//设置属性
			bookE.addAttribute("id", String.valueOf(book.getId()));
			
			Element name = bookE.addElement("name");
			name.setText(book.getName());//给子节点设值
			
			Element price = bookE.addElement("price");
			price.setText(String.valueOf(book.getPrice()));			
		}
		
		File file = new File(fileName);
		//漂亮格式输出
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		//OutputFormat outputFormat = new OutputFormat();
		
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file),outputFormat);
			xmlWriter.write(document);
			
			xmlWriter.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
