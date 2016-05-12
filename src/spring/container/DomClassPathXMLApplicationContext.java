package spring.container;

import java.util.HashMap;
import java.util.Map;

import spring.config.Bean;
import spring.parser.ConfigParser;


/**
 * @author Administrator, 2016年5月6日 下午10:14:40
 *
 */
public class DomClassPathXMLApplicationContext implements BeanFactory {

	private Map<String, Bean> beans = new HashMap<String, Bean>();
	

	//parse xml file instantly this class is initializing
	public DomClassPathXMLApplicationContext(String path) {
		beans = ConfigParser.domParser(path);		
	}
	
	@Override
	public Object getBean(String id) {
		return beans.get(id);
	}
}
