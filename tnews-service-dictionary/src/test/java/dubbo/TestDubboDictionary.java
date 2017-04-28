package dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 */
public class TestDubboDictionary {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (TestDubboDictionary.class) {
			while (true) {
				try {
					TestDubboDictionary.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
}