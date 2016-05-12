package spring.config;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 *
 */
public class Bean {
	private String id;
	private String className;
	private String scope;
	private List<Property> properties = new ArrayList<Property>();
	
	public Bean() {
	}
	
	public Bean(String id, String className,String scope) {
		this.id = id;
		this.className = className;
		this.scope = scope;
	}
	
	
	public Bean(String id, String className, List<Property> properties) {
		this.id = id;
		this.className = className;
		this.properties = properties;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

}
