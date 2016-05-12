/**
 * 
 */
package spring.beans;

/**
 * @author Administrator, 2016年5月12日 下午3:14:59
 *
 */
public class Animal {

	private Cat cat;
	
	private int age;
	public Animal() {
		System.out.println("Animal实例创建");
	}
	public Cat getCat() {
		return cat;
	}
	public void setCat(Cat cat) {
		this.cat = cat;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
