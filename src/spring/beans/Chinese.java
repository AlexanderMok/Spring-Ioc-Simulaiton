package spring.beans;

/**
 * @author Administrator, 2016年5月7日 上午11:22:16
 *
 */
public class Chinese implements Human{
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void speak() {
		System.out.println("现在是" + name + "说中文。");
	}

}
