package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import application.Database;

class DatabaseTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Database.deleteTable("words");
		Database.createWordsTable("words");
		Database.post("The", 5);
		System.out.println("Created a words table and posted 'The', 5.");
	}

	// TestInfo testInfo.getDisplayName() is a handy trick. 
	
	@BeforeEach
	void setUp(TestInfo testInfo) throws Exception {
		Database.update("The", 5);
		System.out.println("beforeeach: " + testInfo.getDisplayName() + " in " + this + ": Frequency of 'The' updated to 5");
	}
	
	
	
	
	
	
	
	@DisplayName("Frequency should be 5")
	@Test
	void shouldQueryFrequencyAndPass() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertEquals(5, frequency);
	}
	
	@DisplayName("Frequency should be 7, not 6 ")
	@Test
	void shouldQueryFrequencyAndFail() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(6, frequency);
	}
	
	@DisplayName("Frequency should update from 5 to 7")
	@Test
	void shouldUpdateFrequencyCheckUpdateAndPass() {
		assertEquals(5, Database.queryFrequency("The"));
		Database.update("The", 7);
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(5, Database.queryFrequency("The"));
		assertEquals(7, frequency);
	}
	
	@DisplayName("Frequency should update from 5 to 7")
	@Test
	void updateFrequency_testFail() {
		Database.update("The", 7);
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(5, frequency);
	}
	
	@Nested
	class RepeatedTests {
		@DisplayName("Search for words not in table")
		@RepeatedTest(value = 5, name = "Repeating Database.query(word) Test {currentRepetition} of {totalRepetitions}")
		void shouldQueryForWordNotInTable() {
			int frequency = Database.queryFrequency("burrito");
			assertEquals(-1, frequency);
		}
	}
	
	@Nested
	class ParameterizedTests {
		@DisplayName("Search for word not present in database")
		@ParameterizedTest
		@ValueSource(strings = {"rice", "cake", "wife", "lake", "twice", "baked"})
		public void shouldSearchForWordNotPresentInDatabase(String word) {
			int frequency = Database.queryFrequency(word);
			assertEquals(-1, frequency);
		}
	
		@DisplayName("CSV Source Case - Word should be posted to database, found and then deleted")
		@ParameterizedTest
		@CsvSource({"rice", "cake", "wife", "lake", "twice", "baked"})
		public void shouldTestWordFormatUsingCSVSource(String word) {
			try {
				Database.post(word, 1);
				assertEquals(1, Database.queryFrequency(word));
				Database.delete(word);
				assertEquals(-1, Database.queryFrequency(word));
			} catch (Exception e) {
				System.out.println("Exception in CSV Source Case: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
        @DisplayName("CSV File Source Case -  Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "resources/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String frequency) {
            try {
				String word = "Jockey";
            	Database.post(word, Integer.parseInt(frequency));
				assertEquals(1, Database.queryFrequency(word));
				Database.delete("Jockey");
				assertEquals(-1, Database.queryFrequency(word));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        } 
		
	}
	
	@DisplayName("Method Source Case - Search for word not in database")
	@ParameterizedTest
	@MethodSource("wordList")
	public void shouldSearchForWordNotPresentInDatabasUsingMethodSource(String word) {
		int frequency = Database.queryFrequency(word);
		assertEquals(-1, frequency);
	}
		
	private static List<String> wordList() {
		return Arrays.asList("rice", "cake", "wife", "lake", "twice", "baked");
	}
	
	@Test
	@DisplayName("Should check all items in the list (just for practice)")
	void shouldCheckAllItemsInTheList() {
		int[] numbers = {2, 3, 4, 5, 6, 7};
		
		Assertions.assertAll(() -> assertEquals(2, numbers[0]), 
				() -> assertEquals(3, numbers[1]), 
				() -> assertEquals(4, numbers[2]), 
				() -> assertEquals(5, numbers[3]), 
				() -> assertEquals(6, numbers[4]), 
				() -> assertEquals(7, numbers[5]));
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Database.deleteTable("words");
		Database.createWordsTable("words");
	}
	
	
	// Extra Tests
	// newApproach
	@Test
	void newApproach() {
		NumberFormatException expected = assertThrows(NumberFormatException.class, () -> {
			Integer.parseInt("foo");
		});
		assertEquals("For input string: \"foo\"", expected.getMessage());
	}
	
	
	
	
	
	
	
	
	
	
	
//	oldSchoolApproaches
//		@Test(expected = NumberFormatException.class)
//		public void annotationParameterApproach() {
//			Integer.parseInt("foo");
//		}
//		
//		@Rule
//		public ExpectedException thrown = ExpectedException.none();
//		
//		@Test
//		public void ruleBasedApproach() {
//			thrown.expect(NumberFormatException.class);
//			thrown.expectMessage("For input string: \"foo\"");
//			
//			Integer.parseInt("foo");
//		}
//		
	
	
}
