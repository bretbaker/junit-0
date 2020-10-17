package com.junit0.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class StringTest {
	
	private String str;

	@BeforeAll
	static void beforeAll() {
		System.out.println("init connection to db");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("close connection to db");
	}

	@BeforeEach
	void beforeEach(TestInfo info) {
		System.out.println("setup test data for " + info.getDisplayName());
	}

	@AfterEach
	void afterEach() {
		System.out.println("clean up test data");
	}

	@Test
	@Disabled
	void length_basic() {
		int actualLength = "abcd".length();
		int expectedLength = 4;
		assertEquals(expectedLength, actualLength);
	}

	@Test
	void length_greater_than_zero() {
		assertTrue("abcd".length() > 0);
	}

	@ParameterizedTest
	@ValueSource(strings = { "abcd", "abc", "a", "def" })
	void length_greater_than_zero_using_parameterized_test(String str) {
		assertTrue(str.length() > 0);
	}

	@Test
	@DisplayName("when length is null throw an exception")
	void length_exception() {
		String str = null;
		assertThrows(NullPointerException.class, () -> {
			str.length();
		});
	}
	
	@Test
	void performanceTest() {
		assertTimeout(Duration.ofSeconds(5), () -> {
			for (int i = 0; i <= 1000000; i++) {
				System.out.println(i);
			}
		});
	}

	@Test
	void toUpperCase_basic() {
		String str = "abcd";
		String result = str.toUpperCase();
		assertNotNull(result);
		assertEquals("ABCD", result);
	}

	@Test
	void uppercase() {
		assertEquals("ABCD", "abcd".toUpperCase());
		assertEquals("ABC", "abc".toUpperCase());
		assertEquals("", "".toUpperCase());
		assertEquals("ABCDEFG", "abcdefg".toUpperCase());
	}

	@ParameterizedTest(name = "{0} toUpperCase is {1}")
	@CsvSource(value = { "abcd, ABCD", "abc, ABC", "'', ''" , "abcdefg, ABCDEFG" })
	void uppercase_paramterized_csv(String word, String capitalizedWord) {
		assertEquals(capitalizedWord, word.toUpperCase());
	}
	
	@ParameterizedTest(name = "{0} length is {1}")
	@CsvSource(value = { "abcd, 4", "abc, 3", "'', 0" , "abcdefg, 7" })
	void length_paramterized_csv(String word, int expectedLength) {
		assertEquals(expectedLength, word.length());
	}

	@Test
	@RepeatedTest(10)
	void contains_basic() {
		assertFalse("abcdefgh".contains("ijk"));
	}

	@Test
	void split_basic() {
		String str = "abc def ghi";
		String actualResult[] = str.split(" ");
		String[] expectedResult = new String[] { "abc", "def", "ghi" };
		assertArrayEquals(expectedResult, actualResult);
	}
	
	@Nested
	@DisplayName("For an empty string")
	class EmptyStringTests {
		
		@BeforeEach
		void setToEmpty() {
			str = "";
		}
		
		@Test
		@DisplayName("length should be zero")
		void lengthIsZero() {
			assertEquals(0, str.length());
		}
		
		@Test
		@DisplayName("uppercase is empty")
		void upperCaseIsEmpty() {
			assertEquals("", str.toUpperCase());
		}
	}
	

}
