package com.foxminded.SchoolSpringJdbc.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

	@Test
	void test_generateRandomAlphabeticString() {
		int stringLength = 2;
		String actualResult = StringUtils.generateRandomAlphabeticString(stringLength);
		int expectedLength = 2;
		long charsCount = actualResult.chars().filter(it -> Character.isLetter(it)).count();

		assertTrue(!actualResult.isEmpty());
		assertEquals(expectedLength, actualResult.length());
		assertEquals(String.class, actualResult.getClass());
		assertEquals(stringLength, charsCount);
	}

	@Test
	void test_generateRandomNumericString() {
		int stringLength = 2;
		String actualResult = StringUtils.generateRandomNumericString(stringLength);
		int expectedLength = 2;
		long digitsCount = actualResult.chars().filter(it -> Character.isDigit(it)).count();

		assertTrue(!actualResult.isEmpty());
		assertEquals(expectedLength, actualResult.length());
		assertEquals(String.class, actualResult.getClass());
		assertEquals(stringLength, digitsCount);

	}

	@Test
	void test_generateRandomNumber() {
		int leftLimit = 0;
		int rightLimit = 9;
		int actualNumber = StringUtils.generateRandomNumber(leftLimit, rightLimit);

		assertTrue(actualNumber >= leftLimit);
		assertTrue(actualNumber <= rightLimit);
	}

}
