package cn.zzuisa.utils.kit;

public class RetStrKit {

	public static String tranImgPath(String imgPath) {
		if (!imgPath.startsWith("http")) {
			return "http://lock.iot.gdatacloud.com/" + imgPath;
		}
		return imgPath;
	}
}
