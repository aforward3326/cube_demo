package tw.com.cube.demo.cube_demo.utils;

import java.util.Date;
import org.springframework.stereotype.Component;
import tw.com.cube.demo.cube_demo.exception.InvalidDateFormatException;

@Component
public interface DateUtil {
  /**
   * Format Date (UTC) 1.yyyyMMdd 2.YYYY/MM/dd 3.YYYY-MM-dd 4.yyyyMMddHHmmss 5.yyyy-MM-dd HH:mm:ss
   * 6.yyyy/MM/dd HH:mm:ss
   *
   * @return Date
   */
  Date formatDate(Date date, int type) throws InvalidDateFormatException;

  /**
   * Format Date (UTC)
   *
   * @return Date
   */
  Date formatDate(Date date, String formatDate) throws InvalidDateFormatException;

  /**
   * Format date to string (UTC)
   *
   * @return Date
   */
  String formatDateString(Date date, String formatDate) throws InvalidDateFormatException;

  /**
   * Format date to string (UTC) 1.yyyyMMdd 2.YYYY/MM/dd 3.YYYY-MM-dd 4.yyyyMMddHHmmss 5.yyyy-MM-dd
   * HH:mm:ss * 6.yyyy/MM/dd HH:mm:ss
   *
   * @return Date
   */
  String formatDateString(Date date, int type) throws InvalidDateFormatException;

  /**
   * Parse String to Date (UTC)
   *
   * @return Date
   */
  Date parseDate(String date, String formatDate) throws InvalidDateFormatException;

  /**
   * Parse String to Date (UTC) 1.yyyyMMdd 2.YYYY/MM/dd 3.YYYY-MM-dd 4.yyyyMMddHHmmss 5.yyyy-MM-dd
   * HH:mm:ss 6.yyyy/MM/dd HH:mm:ss
   *
   * @return Date
   */
  Date parseDate(String date, int type) throws InvalidDateFormatException;

  /**
   * get past year date
   *
   * @return Date
   */
  Date pastYear(int year) throws InvalidDateFormatException;

  /**
   * get past day date
   *
   * @return Date
   */
  Date pastDay(int day) throws InvalidDateFormatException;
}
