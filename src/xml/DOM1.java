package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Alexia
 * 
 *         DOM 解析XML文档 生成xml
 */
public class DOM1 implements XmlInter {
	public static void main(String[] args) {
		DOM1 dom1 = new DOM1();

		String fileName = "E:\\software\\eclipse\\AProgram\\test1\\src\\xml\\User.xml";
		dom1.parse(fileName);

		List<Book> books = new ArrayList<Book>();
		Book book1 = new Book(1, "thinkjava", 50.2f);
		books.add(book1);
		Book book2 = new Book(2, "代码重构", 30.2f);
		books.add(book2);
		String fileNameCreate = "E:/software/eclipse/AProgram/test1/src/xml/bookDomcreate.xml";
		dom1.create(books, fileNameCreate);

	}

	@Override
	public void parse(String fileName) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList users = document.getChildNodes();// user collection

			for (int i = 0; i < users.getLength(); i++) {
				Node user = users.item(i);// one user
				NodeList userInfo = user.getChildNodes();// one user inner child collection

				for (int j = 0; j < userInfo.getLength(); j++) {
					Node node = userInfo.item(j);// one user inner child one attribute
					NodeList userMeta = node.getChildNodes();

					for (int k = 0; k < userMeta.getLength(); k++) {
						if (userMeta.item(k).getNodeName() != "#text")
							System.out.println(userMeta.item(k).getNodeName() + 
									":" + userMeta.item(k).getTextContent());
					}					
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(List<Book> books, String fileName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			document.setXmlStandalone(true);// 去除standalone="no"

			Element booksE = document.createElement("books");

			for (Book book : books) {
				Element bookE = document.createElement("book");

				bookE.setAttribute("id", String.valueOf(book.getId()));
				Element name = document.createElement("name");
				name.setTextContent(book.getName());
				bookE.appendChild(name);
				Element price = document.createElement("price");
				price.setTextContent(String.valueOf(book.getPrice()));
				bookE.appendChild(price);

				booksE.appendChild(bookE);
			}
			document.appendChild(booksE);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置换行

			File file = new File(fileName);
			/*
			 * if(!file.exists()){ file.createNewFile(); }
			 */
			transformer.transform(new DOMSource(document), new StreamResult(file));

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*
			 * catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

	}
}