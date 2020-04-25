package cn.zzuisa.utils.kit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		return sdf.format(date);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
