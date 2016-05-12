package container;

import org.junit.Test;

import spring.beans.Animal;
import spring.beans.Cat;
import spring.beans.Chinese;
import spring.beans.English;
import spring.container.Dom4jClassPathXmlApplicationContext;

/**
 * @author Administrator, 2016年5月7日 下午8:05:46
 *
 */
public class ContainerTest {
	
	@Test
	public void testContainer(){
		Dom4jClassPathXmlApplicationContext context = new Dom4jClassPathXmlApplicationContext("/bean.xml");
//		DomClassPathXMLApplicationContext context = new DomClassPathXMLApplicationContext("/bean.xml");
		Chinese chinese = (Chinese) context.getBean("chinese");
		English english = (English)context.getBean("english");
		chinese.speak();
		english.speak();
		
	}
	
	@Test
	public void testBean(){
		Dom4jClassPathXmlApplicationContext context = new Dom4jClassPathXmlApplicationContext("/bean.xml");
		Animal animal = (Animal)context.getBean("animal");
		System.out.println(animal.getAge());
		Cat cat = (Cat)context.getBean("cat");
		Cat cat2 = (Cat)context.getBean("cat");
	}
	
	
}
