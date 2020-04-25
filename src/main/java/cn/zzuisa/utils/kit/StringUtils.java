package cn.zzuisa.utils.kit;

import java.security.MessageDigest;
import java.time.LocalDateTime;

public class StringUtils {

	public static String arrToStr(Long []arr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < arr.length;i ++) {
			sb.append(String.valueOf(arr[i]));
			if(i == arr.length - 1) {
				break;
			}
			sb.append(",");
		}
		return sb.toString();
	}

	public static Integer nullToZero(Integer i) {
		return i == null ? 0 : i;
	}

	public static LocalDateTime nullToNow(LocalDateTime time) {
		return time == null ? LocalDateTime.now() : time;
	}



	public final static String MD5_SHA(String s, String method) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes();
            //如果输入“SHA”，就是实现SHA加密。
            MessageDigest mdTemp = MessageDigest.getInstance(method);
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
