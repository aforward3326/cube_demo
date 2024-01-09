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

  /**
   * Format to YYYY/MM/dd UTC
   *
   * @param date
   * @return Date
   */
  public Date formatDate(Date date);

  /**
   * Format to YYYY-MM-dd HH:mm:ssT UTC
   *
   * @param date
   * @return Date
   */
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

  /**
   * get past year date
   *
   * @param year
   * @return Date
   */
  public Date pastYear(int year);

  /**
   * get past day date
   *
   * @param day
   * @return Date
   */
  public Date pastDay(int day);
}
