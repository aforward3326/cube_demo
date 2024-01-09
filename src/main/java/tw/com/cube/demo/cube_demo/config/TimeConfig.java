package tw.com.cube.demo.cube_demo.config;

import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {
  public static DateTimeFormatter DATE_TIME_FMT_1 =
      DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
  public static DateTimeFormatter DATE_TIME_FMT_2 =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static DateTimeFormatter DATE_TIME_FMT_3 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  public static DateTimeFormatter DATE_FMT_1 = DateTimeFormatter.ofPattern("yyy/MM/dd");
  public static DateTimeFormatter DATE_FMT_2 = DateTimeFormatter.ofPattern("yyyyMMdd");
}
