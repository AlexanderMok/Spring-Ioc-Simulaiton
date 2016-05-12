package spring.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import spring.config.Bean;
import spring.config.Property;
import spring.parser.ConfigParser;

/**
 * @author Administrator, 2016年5月7日 上午10:26:52
 * 
 */
public class Dom4jClassPathXmlApplicationContext implements BeanFactory {
	
	//beans from xml config file
	private Map<String, Bean> beans;

	// container to store bean
	private Map<String, Object> contextMap = new HashMap<String, Object>();

	// parse xml file instantly this class is initializing
	public Dom4jClassPathXmlApplicationContext(String path) {
		beans = ConfigParser.dom4jParser(path);

		if (beans != null) {
			// loop beans
			for (Entry<String, Bean> en : beans.entrySet()) {

				String id = en.getKey();
				Bean bean = en.getValue();

				Object existBean = contextMap.get("id");

				// bean is not initialized and scope is singleton
				if (existBean == null && bean.getScope().equals("singleton")) {

					// initialize bean. 需检验是否已经注入
					// Bean must be checked if it is already been initialized
					// before this process
					Object obj = createBean(bean);

					// put beans into the container
					contextMap.put(id, obj);
				}

			}
		}
	}

	/**
	 * @param bean
	 * @return
	 */
	private Object createBean(Bean bean) {

		String className = null;

		// user reflection
		Class clazz = null;
		try {
			className = bean.getClassName();
			clazz = Class.forName(className);
		} catch (ClassNotFoundException | NullPointerException e) {
			e.printStackTrace();
			throw new RuntimeException(className
					+ " not found in xml config file.");
		}

		// generate Objection
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(
					"no  default constructor defined in " + className);
		}
		
		
		/**
		 * <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
         *		<property name="driverClassName" value="${jdbc.driverClass}"/>
         *      <property name="url" value="${jdbc.url}"/>
         * </bean>	
		 */

		// inject property
		if (bean.getProperties() != null) {
			
			
			for (Property properties : bean.getProperties()) {

				String propName = null;
				Method setterMethod = null;
				try {
					
					propName = properties.getName();
					
					// using setter to inject value <property name="name"
					// value="changing"/>, <property name="name" ref="ref"/>
					setterMethod = BeanMethod.getWriterMethod(object,propName);
					
				} catch (NullPointerException e) {
					e.printStackTrace();
					throw new RuntimeException("property name is not defined.");
				}


				// inject value into object using setter
				if (properties.getValue() != null) {
					
					
					//use apache BeanUtils tool to inject value so that automation of type change can be done
					Map<String, String[]> paraMap = new HashMap<String, String[]>();
					paraMap.put(propName, new String[]{properties.getValue()});
					
					try {
						BeanUtils.populate(object, paraMap);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}

				}

				// inject another Object
				if (properties.getRef() != null) {
					// get ref instance
					Object refBean = contextMap.get(properties.getRef());

					// refBean is not generated. Generate it by recursion
					if (refBean == null) {

						refBean = createBean(beans.get(properties.getRef()));
						
						if(beans.get(properties.getRef()).getScope().equals("singleton")){
							// put refBean into Container if scope in the xml file is singleton
							contextMap.put(properties.getRef(), refBean);
						}
					}
					
					//setter to inject value or ref
					try {
						
						setterMethod.invoke(object, refBean);
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				

			}
		}

		return object;
	}

	@Override
	public Object getBean(String id) {
		Object bean = contextMap.get(id);
		
		//if scope is not singleton, then bean does not exist in contextMap and should be null
		if(bean == null){
			bean = createBean(beans.get(id));
		}
		return bean;
	}

}
