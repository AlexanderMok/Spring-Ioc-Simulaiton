/**
 * 
 */
package spring.container;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author Administrator
 * 
 */
public class BeanMethod {

	/**
	 * @param object,propName
	 * @return setter method in object
	 */
	public static Method getWriterMethod(Object object, String propName) {

		Method method = null;
		/*
		 * Introspect on a Java Bean and learn about all its properties, exposed
		 * methods, and events.
		 */
		try {
			// 1.analyze bean
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			
			// 2.get propertyDescriptors
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			// loop propertyDescriptors
			if (propertyDescriptors != null) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {

					// get attribute name from current propertyDescriptor
					// 相当于 获取类的成员变量名称
					String pName = propertyDescriptor.getName();
					// 如果当前的名称等于传入的<property name=""/>的name值
					if (pName.equals(propName)) {
						// get setter method,can return null
						method = propertyDescriptor.getWriteMethod();
					}
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		if (method == null) {
			throw new RuntimeException("no setter in" + object.getClass());
		}
		return method;
	}

}
