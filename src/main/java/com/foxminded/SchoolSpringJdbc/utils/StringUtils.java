package com.foxminded.SchoolSpringJdbc.utils;

import java.util.Random;

public class StringUtils {

	private StringUtils() {
	}

	public static String generateRandomAlphabeticString(int lenght) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).limit(lenght)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}

	public static String generateRandomNumericString(int lenght) {
		Random random = new Random();
		int leftLimit = 0;
		int rightLimit = 9;

		return random.ints(leftLimit, rightLimit + 1).limit(lenght)
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
	}

	public static int generateRandomNumber(int leftLimit, int rightLimit) {
		Random random = new Random();
		return random.nextInt(leftLimit, rightLimit);
	}
}
