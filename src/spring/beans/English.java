package spring.beans;

/**
 * @author Administrator, 2016年5月7日 上午11:23:24
 *
 */
public class English implements Human{
	
	private Chinese chinese;
	
	private String name = "British";
	

	public Chinese getChinese() {
		return chinese;
	}

	public void setChinese(Chinese chinese) {
		this.chinese = chinese;
	}

	@Override
	public void speak() {
		System.out.println(name + " speaks in English.");
		getChinese().speak();
	} 
}
