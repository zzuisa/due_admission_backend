package cn.zzuisa.utils.kit;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

	public static String uploadFile(byte[] file, String fileDir, String fileName) throws Exception {
		String filePath = fileDir + fileName;

		File targetFile = new File(fileDir);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath);
		out.write(file);
		out.flush();
		out.close();
		return filePath;
	}
}
