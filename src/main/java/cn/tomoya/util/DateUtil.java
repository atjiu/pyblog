package cn.tomoya.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tomoya on 2017/7/11.
 */
public class DateUtil {

  static Logger log = LoggerFactory.getLogger(DateUtil.class);

  public static final String DATEFORMATSTYLE = "yyyy-MM-dd";
  public static final String DATETIMEFORMATSTYLE = "yyyy-MM-dd HH:mm:ss";

  public static Date string2Date(String dateString, String style) {
    if (StringUtils.isEmpty(dateString)) return null;
    Date date = new Date();
    SimpleDateFormat strToDate = new SimpleDateFormat(style);
    try {
      date = strToDate.parse(dateString);
    } catch (ParseException e) {
      log.error(e.getMessage());
    }
    return date;
  }

  public static String formatDate(Date date, String style) {
    SimpleDateFormat sdf = new SimpleDateFormat(style);
    return sdf.format(date);
  }
}
