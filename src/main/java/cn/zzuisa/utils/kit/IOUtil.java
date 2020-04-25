package cn.zzuisa.utils.kit;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {

	public static void echoReqBody(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inStream;
		try {
			inStream = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		do {
//			c = (char) br.read();
//			System.out.print(c);
//		} while (c != '\n');
        System.out.print(sb);
	}
}
