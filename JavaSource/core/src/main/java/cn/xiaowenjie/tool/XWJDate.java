package cn.xiaowenjie.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XWJDate {

	/**
	 * 日期相隔多少天(除掉周末)
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int betweenWorkday(Date d1, Date d2) {
		return doBetween(d1, d2, true);
	}

	/**
	 * 日期相隔多少天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int between(Date d1, Date d2) {
		return doBetween(d1, d2, false);
	}

	private static int doBetween(Date d1, Date d2, boolean onlywork) {
		if (d2.before(d1)) {
			throw new IllegalArgumentException("d1 > d2");
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);

		int count = 1;
		while (true) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			if (calendar.getTime().equals(d2)) {
				return count;
			}

			if (onlywork) {
				int day = calendar.get(Calendar.DAY_OF_WEEK);
				if (day == 0 || day == 6) {
					continue;
				}
			}

			count++;
		}
	}

	/**
	 * 字符串转日期 yyyy-MM-dd
	 * 
	 * @param s
	 * @return
	 */
	public static Date toDate(String s) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("错误的日志格式yyyy-MM-dd: " + s);
	}

	public static void main(String[] args) throws ParseException {
		Date d1 = toDate("2018-04-01");
		Date d2 = toDate("2018-04-07");
		int between = between(d1, d2);
		System.out.println(between);

		int between2 = betweenWorkday(d1, d2);
		System.out.println(between2);
	}
}
