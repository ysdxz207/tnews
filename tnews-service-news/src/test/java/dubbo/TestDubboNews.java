package dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @描述: 启动Dubbo服务用的MainClass.
 */
public class TestDubboNews {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		synchronized (TestDubboNews.class) {
			while (true) {
				try {
					TestDubboNews.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
    
}