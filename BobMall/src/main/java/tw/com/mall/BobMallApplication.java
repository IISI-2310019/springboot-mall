package tw.com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("tw.com.mall.mapper")
@SpringBootApplication
public class BobMallApplication {

	public static void main(String[] args) {
		SpringApplication.run(BobMallApplication.class, args);
	}

}
