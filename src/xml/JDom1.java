package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class JDom1 implements XmlInter{

	public static void main(String[] args) {
		JDom1 jDom1 = new JDom1();
		String fileName = "E:\\software\\eclipse\\AProgram\\test1\\src\\xml\\User.xml";
		jDom1.parse(fileName);
		
		List<Book> books = new ArrayList<Book>();
    	Book book1 = new Book(1,"thinkjava",50.2f);
    	books.add(book1);
    	Book book2 = new Book(2,"¥˙¬Î÷ÿππ",30.2f);
    	books.add(book2);
    	String fileNameCreate = "E:/software/eclipse/AProgram/test1/src/xml/bookJdomcreate.xml";
    	jDom1.create(books, fileNameCreate);
	}

	@Override
	public void parse(String fileName){
		SAXBuilder builder = new SAXBuilder();
		
		try {
			Document document = builder.build(fileName);
			
			Element element = document.getRootElement();
			List<Element> list = element.getChildren("user"); 
			
			for(Element user:list){
				System.out.println("------------------------");
				
				String id = user.getAttributeValue("id");
				System.out.println("id" + ":" + id);
				List<Element> list1 = user.getChildren();
								
				for(Element ele:list1){
					System.out.println(ele.getName() + ":" + ele.getValue());					
				}
			}
			
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void create(List<Book> books, String fileName) {
		Element booksE = new Element("books");
		
		for(Book book:books){
			Element bookE = new Element("book");
			bookE.setAttribute("id",String.valueOf(book.getId()));
			
			Element name = new Element("name");
			name.setText(book.getName());
			bookE.addContent(name);
			
			Element price = new Element("price");
			price.setText(String.valueOf(book.getPrice()));
			bookE.addContent(price);
			
			booksE.addContent(bookE);
		}

		Document document = new Document(booksE);
		Format format = Format.getPrettyFormat();
		XMLOutputter outputter = new XMLOutputter(format);
		
		File file = new File(fileName);
		
		try {
			outputter.output(document, new FileOutputStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
