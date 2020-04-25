package cn.zzuisa.utils.kit;

public class S {

	public static boolean isNotNull(Object o) {
		if (o != null && !"".equals(o)) {
			return true;
		}
		return false;
	}
}
