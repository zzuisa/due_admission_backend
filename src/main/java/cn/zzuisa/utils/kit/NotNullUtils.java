package cn.zzuisa.utils.kit;

public class NotNullUtils {

	public static Boolean isBlank(Object o) {
		if(o == null) {
			return true;
		}
		if(o instanceof String && ((String) o).trim().equals("")) {
			return true;
		}
		return false;
	}


}
