package tw.com.cube.demo.cube_demo.utils;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public interface DateUtil {
  /**
   * Format to YYYY/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  public Date dateTime(Date date);

  public Date formatDate(Date date);

  public String formatDateString(Date date);

  /**
   * Format to YYYY/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  public Date parseDate(String date);

  /**
   * Format to YYYY/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  public Date parseDate2(String date);

  public Date pastYear(int year);

  public Date pastDay(int day);
}
