package utils;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class UsefulUtils {
	
	public static <T> T getFirst(List<T> list) {
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	public static String hashPassword(String value) {
		return DigestUtils.sha256Hex(value);
	}

}
