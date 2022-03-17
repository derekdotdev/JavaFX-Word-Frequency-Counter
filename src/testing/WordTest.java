package testing;

import application.Word;
import org.junit.jupiter.api.*;

/**
 * Word class was created before a database was implemented and, although it is no longer used, 
 * creating tests and validation just for practice. 
 * @author derekdileo */
class WordTest {

	public static Word wordTest;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass()... (runs once)");
		wordTest = new Word("testing", 5);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass()...(runs once at end)");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("setUp()... (runs before each test)");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("tearDown()...(runs after each test)");
	}

//	@Test
//	@DisplayName("Should test wordTest.toString(0) method against strCode")
//	void wordTestingToStringCheck() {
//		int index = 0;
//		String strCode = "\n " + (index + 1) + ") Word: " + wordTest.getWord() + "\t\t\tFrequency: " + wordTest.getFrequency();
//		assertEquals(strCode, wordTest.toString(0));
//	}
	
//	@Test
//	@DisplayName("Should check all items in the list (just for practice)")
//	void shouldCheckAllItemsInTheList() {
//		int[] numbers = {2, 3, 4, 5, 6, 7};
//
//		Assertions.assertAll(() -> assert(2, numbers[0]),
//				() -> assertEquals(3, numbers[1]),
//				() -> assertEquals(4, numbers[2]),
//				() -> assertEquals(5, numbers[3]),
//				() -> assertEquals(6, numbers[4]),
//				() -> assertEquals(7, numbers[5]));
//	}
	
//
//	@Test
//	void compareTo_Test(Word wordTest2) {
//		int result = wordTest.compareTo(wordTest2);		
//		System.out.println("Result: " + result);
//		assertEquals(0, result, 0);
//		//fail("Not yet implemented");
//	}
	
	// An example with a fail and expected Exception
//	@Test(expected IllegalArgumentException.class)
//	void test() {
//		fail("Not yet implemented");
//	}
	
}
