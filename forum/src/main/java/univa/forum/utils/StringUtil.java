package univa.forum.utils;

import java.util.Optional;
import java.util.Random;

public final class StringUtil {
	
	/**
	 * return 0-9+a-Z
	 */
	public static String RandomString(int length) {
		if( length < 1 ) {
			throw new RuntimeException(" random string length > 1");
		}
		StringBuffer buffer = new StringBuffer();
		Random rnd = new Random();
		for(int i=0; i < length; i++) {
			int rIndex = rnd.nextInt(3);
			switch(rIndex) {
			case 0: // a-z
				buffer.append((char)((int)(rnd.nextInt(26)) + 97));
				break;
			case 1: // A-Z
				buffer.append((char)((int)(rnd.nextInt(26)) + 65));
				break;
			case 2: // 0-9
				buffer.append(rnd.nextInt(10));
				break;
			}
		}
		
		return buffer.toString();
	}
}