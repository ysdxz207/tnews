import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 函数式接口演示
 * 函数式接口必须要有且仅有一个抽象方法声明
 * @author huangfeihong
 * @date 2017-04-26 14:42
 */
public class TestInterface {
	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}

	public static Integer convertit(String from) {
		return Integer.valueOf(from);
	}



	public static void main(String[] args) throws IOException {
		//Converter<String, Integer> converter = (from) -> convertit(from);
		Converter<String, Integer> converter = Integer::valueOf;
		Integer converted = converter.convert("123");
		System.out.println(converted);

		Files.lines(Paths.get(
				"D:\\workspace\\idea\\tnews\\tnews-web\\src\\test\\java\\TestInterface.java"))
				.map(String::trim).map((s) -> s.indexOf("main") > -1).forEach(System.out::println);
	}
}
