package spring.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import spring.config.Bean;
import spring.config.Property;
import spring.container.Dom4jClassPathXmlApplicationContext;
import spring.container.DomClassPathXMLApplicationContext;

/**
 * @author Administrator, 2016年5月7日 下午8:10:11
 * 
 */
public class ConfigParser {

	private static Map<String, Bean> beans = new HashMap<String, Bean>();
	
	/**
	 * dom parse xml file
	 * @param path
	 * @return
	 */
	public static Map<String, Bean> domParser(String path) {
		org.w3c.dom.Document document = null;
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(DomClassPathXMLApplicationContext.class
					.getResourceAsStream(path));

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"please check your configuration file. Make sure it is correct.");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		// 获取所有<bean>节点
		NodeList beanList = document.getElementsByTagName("bean");

		for (int i = 0; i < beanList.getLength(); i++) {

			Node node = beanList.item(i);
			NamedNodeMap namedNodeMap = node.getAttributes();
			// dom解析属性是倒着来的，先class，再id,<bean id="" class=""></bean>
			String clazz = namedNodeMap.item(0).getNodeValue();
			String id = namedNodeMap.item(1).getNodeValue();
			
			//Encapsulate the Object into  Object Bean
//			Bean bean = new Bean(id, clazz);
			
			
			

		}
		return beans;
	}
	
	
	/**
	 * dom4j parse xml file
	 * @param path
	 * @return
	 */
	public static Map<String, Bean> dom4jParser(String path) {

		// 1.parse xml configuration

		SAXReader reader = new SAXReader();
		org.dom4j.Document document = null;
		try {
			document = reader.read(Dom4jClassPathXmlApplicationContext.class
					.getResourceAsStream(path));
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"please check your configuration file. Make sure it is correct.");
		}
		// 2. define xPath to fetch all <bean>
		String xpath = "//bean";
		List<Element> list = document.selectNodes(xpath);

		if (list.size() > 0) {
			
			for (Element beanEle : list) {

				String id = beanEle.attributeValue("id");
				String clazz = beanEle.attributeValue("class");
				
				String scope = beanEle.attributeValue("scope");
				
				// default scope is singleton
				if (scope == null) {
					scope = "singleton";
				}
				
				//Encapsulate the Object into  Object Bean
				Bean bean = new Bean(id, clazz,scope);
				
				
				//loop <property/>
				List<Element> properties = beanEle.elements("property");
				
				if (properties!=null) {
					for(Element propertyEle : properties) {
						
						String name = propertyEle.attributeValue("name");
						String value = propertyEle.attributeValue("value");
						String ref = propertyEle.attributeValue("ref");
						
						Property property = new Property();
						if(name!=null){
							property.setName(name);
						}
						if(value!=null){
							property.setValue(value);
						}
						if(ref!=null){
							property.setRef(ref);
						}
						bean.getProperties().add(property);
					}
				}
				
				beans.put(id, bean);
			}
		}
		return beans;
	}
}
