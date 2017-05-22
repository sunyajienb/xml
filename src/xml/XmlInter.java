package xml;

import java.util.List;

public interface XmlInter {
	public void parse(String fileName);
	public void create(List<Book> books,String fileName);	
}
