/**
 * 
 */
package spring.beans;

/**
 * @author Administrator, 2016年5月12日 下午3:16:42
 *
 */
public class Cat {
	private BlackCat blackCat;
	public Cat() {
		System.out.println("Cat实例创建");
	}
	public BlackCat getBlackCat() {
		return blackCat;
	}
	public void setBlackCat(BlackCat blackCat) {
		this.blackCat = blackCat;
	}
	
}
