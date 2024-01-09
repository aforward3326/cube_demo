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
  Date dateTime(Date date);

  /**
   * Format to YYYY/MM/dd UTC
   *
   * @param date
   * @return Date
   */
  Date formatDate(Date date);

  /**
   * Format to YYYY-MM-dd HH:mm:ssT UTC
   *
   * @param date
   * @return Date
   */
  String formatDateString(Date date);

  /**
   * Format to YYYY/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  Date parseDate(String date);

  /**
   * Format to YYYY/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  Date parseDate2(String date);

  /**
   * get past year date
   *
   * @param year
   * @return Date
   */
  Date pastYear(int year);

  /**
   * get past day date
   *
   * @param day
   * @return Date
   */
  Date pastDay(int day);
}
