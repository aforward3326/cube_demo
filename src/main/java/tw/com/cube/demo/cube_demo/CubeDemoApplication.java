package tw.com.cube.demo.cube_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableMongoRepositories
@ImportResource("classpath:springconfig/spring-config.xml")
public class CubeDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(CubeDemoApplication.class, args);
  }
}
