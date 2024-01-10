package tw.com.cube.demo.cube_demo.utils.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tw.com.cube.demo.cube_demo.utils.DateUtil;

@Component
public class DateUtilImpl implements DateUtil {
  Logger logger = LoggerFactory.getLogger(DateUtil.class);

  /**
   * Format Date 1.yyyyMMdd 2.YYYY/MM/dd 3.YYYY-MM-dd 4.yyyyMMddHHmmss 5.yyyy-MM-dd * HH:mm:ss
   * 6.yyyy/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  @Override
  public Date formatDate(Date date, int type) {
    String formatDate;
    switch (type) {
      case 1:
        formatDate = "yyyyMMdd";
        break;
      case 2:
        formatDate = "yyyy/MM/dd";
        break;
      case 3:
        formatDate = "yyyy-MM-dd";
        break;
      case 4:
        formatDate = "yyyyMMddHHmmss";
        break;
      case 5:
        formatDate = "yyyy-MM-dd HH:mm:ss";
        break;
      case 6:
        formatDate = "yyyy/MM/dd HH:mm:ss";
        break;
      default:
        formatDate = "yyyyMMdd";
        break;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dfs = sdf.format(date);
    try {
      Date dateReturn = sdf.parse(dfs);
      return dateReturn;
    } catch (ParseException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Date formatDate(Date date, String formatDate) {
    SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    String dfs = sdf.format(date);
    try {
      Date dateReturn = sdf.parse(dfs);
      return dateReturn;
    } catch (ParseException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String formatDateString(Date date, String formatDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(date);
  }

  @Override
  public String formatDateString(Date date, int type) {
    String formatDate;
    switch (type) {
      case 1:
        formatDate = "yyyyMMdd";
        break;
      case 2:
        formatDate = "yyyy/MM/dd";
        break;
      case 3:
        formatDate = "yyyy-MM-dd";
        break;
      case 4:
        formatDate = "yyyyMMddHHmmss";
        break;
      case 5:
        formatDate = "yyyy-MM-dd HH:mm:ss";
        break;
      case 6:
        formatDate = "yyyy/MM/dd HH:mm:ss";
        break;
      default:
        formatDate = "yyyyMMdd";
        break;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(date);
  }

  /**
   * Parse String to Date 1.yyyyMMdd 2.YYYY/MM/dd 3.YYYY-MM-dd 4.yyyyMMddHHmmss 5.yyyy-MM-dd
   * HH:mm:ss 6.yyyy/MM/dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  @Override
  public Date parseDate(String date, int type) {
    String formatDate = "yyyyMMdd";
    switch (type) {
      case 1:
        formatDate = "yyyyMMdd";
        break;
      case 2:
        formatDate = "yyyy/MM/dd";
        break;
      case 3:
        formatDate = "yyyy-MM-dd";
        break;
      case 4:
        formatDate = "yyyyMMddHHmmss";
        break;
      case 5:
        formatDate = "yyyy-MM-dd HH:mm:ss";
        break;
      case 6:
        formatDate = "yyyy/MM/dd HH:mm:ss";
        break;
      default:
        formatDate = "yyyyMMdd";
        break;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Date parseDate(String date, String formatDate) {
    SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Date pastYear(int year) {
    Date currentDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.YEAR, -year);
    return formatDate(calendar.getTime(), 2);
  }

  @Override
  public Date pastDay(int day) {
    Date currentDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DAY_OF_YEAR, -day);
    return formatDate(calendar.getTime(), 2);
  }
}
