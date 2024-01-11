package tw.com.cube.demo.cube_demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApiUriConfig {
  @Value("${forex.api}")
  private String forexApi;
}
