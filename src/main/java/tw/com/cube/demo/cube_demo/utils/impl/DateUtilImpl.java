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
   * Format to yyyy-MM-dd HH:mm:ss
   *
   * @param date
   * @return Date
   */
  @Override
  public Date dateTime(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
  public Date formatDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
  public String formatDateString(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    return sdf.format(date);
  }

  /**
   * Parse String to Date (yyyyMMdd)
   *
   * @param date
   * @return Date
   */
  @Override
  public Date parseDate(String date) {
    //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Format to YYYY/MM/dd
   *
   * @param date
   * @return Date
   */
  @Override
  public Date parseDate2(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
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
    return formatDate(calendar.getTime());
  }

  @Override
  public Date pastDay(int day) {
    Date currentDate = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(currentDate);
    calendar.add(Calendar.DAY_OF_YEAR, -day);
    return formatDate(calendar.getTime());
  }
}
