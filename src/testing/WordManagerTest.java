package testing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import application.WordManager;

class WordManagerTest {
	
	private WordManager wordManager;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Should Print Before All Tests");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("Instantiating Word Manager");
		wordManager = new WordManager();
		
	}
	
	@Test
	@DisplayName("Should Create Word")
	void shouldCreateWord() {
		wordManager.addWord("hello", 1);
		assertFalse(wordManager.getAllWords().isEmpty());
		assertEquals(1, wordManager.getAllWords().size());
	}

	@Test
	@DisplayName("Should Not Create Word When word is Null")
	public void shouldThrowRuntimeExceptionWhenWordIsNull() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			wordManager.addWord(null, 15);
		});
	}
	
	@Test
	@DisplayName("Should Not Create Word When frequency is Zero")
	public void shouldThrowRuntimeExceptionWhenFrequencyIsZero() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			wordManager.addWord("Then", 0);
		});
	}
	
	@Test
	@DisplayName("Should Only Create Word on Mac OS")
	@EnabledOnOs(value = OS.MAC, disabledReason = "Should Run only on MAC")
	public void shouldCreateWordOnMac() {
		wordManager.addWord("Apple", 6);
		assertFalse(wordManager.getAllWords().isEmpty());
		assertEquals(1, wordManager.getAllWords().size());
	}
	
	@Test
	@DisplayName("Should Not Create Word on Windows")
	@DisabledOnOs(value = OS.WINDOWS, disabledReason = "Should Run only on MAC")
	public void shouldNotCreateWordOnWindows() {
		wordManager.addWord("Window", 6);
		assertFalse(wordManager.getAllWords().isEmpty());
		assertEquals(1, wordManager.getAllWords().size());
	}
	
	@Nested
	class RepeatedTests {
		@DisplayName("Repeat Word Creation Test 5 Times")
		@RepeatedTest(value = 5, name = "Repeating Word Creation Test {currentRepetition} of {totalRepetitions}")
		public void shouldTestWordCreationRepeatedly() {
			wordManager.addWord("When", 1);
			assertFalse(wordManager.getAllWords().isEmpty());
			assertEquals(1, wordManager.getAllWords().size());
		}
	}
	
	@Nested
	class ParameterizedTests {
		@DisplayName("Frequency should match the required Format") 
		@ParameterizedTest
		@ValueSource(ints = {1, 1, 1})
		public void shouldTestFrequencyFormatUsingValueSource(int frequency) {
			wordManager.addWord("Try", frequency);
			assertFalse(wordManager.getAllWords().isEmpty());
			assertEquals(1, wordManager.getAllWords().size());
		}
		
		@DisplayName("Frequency should not match the required Format") 
		@ParameterizedTest
		@ValueSource(strings = {"F", "A", "I", "L"})
		public void shouldTestFrequencyFormatUsingValueSourceAndFail(String frequency) {
			Assertions.assertThrows(RuntimeException.class, () -> {
				wordManager.addWord("Then", Integer.parseInt(frequency));
			});
			assertTrue(wordManager.getAllWords().isEmpty());
			assertEquals(0, wordManager.getAllWords().size());
		}
		
		@DisplayName("CSV Source Case - Frequency should match the required Format")
		@ParameterizedTest
        @CsvSource({"1", "1", "1"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String frequency) {
            wordManager.addWord("Joy", Integer.parseInt(frequency));
            assertFalse(wordManager.getAllWords().isEmpty());
            assertEquals(1, wordManager.getAllWords().size());
        }

        @DisplayName("CSV File Source Case - Phone Number should match the required Format")
        @ParameterizedTest
        @CsvFileSource(resources = "resources/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            wordManager.addWord("Jockey", Integer.parseInt(phoneNumber));
            assertFalse(wordManager.getAllWords().isEmpty());
            assertEquals(1, wordManager.getAllWords().size());
        } 
		
	}
	
	@DisplayName("Method Source Case - Frequency should match the required Format")
    @ParameterizedTest
    @MethodSource("frequencyList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(int phoneNumber) {
        wordManager.addWord("Java", phoneNumber);
        assertFalse(wordManager.getAllWords().isEmpty());
        assertEquals(1, wordManager.getAllWords().size());
    }

    private static List<Integer> frequencyList() {
        return Arrays.asList(1, 1, 1);
    }

    @Test
    @DisplayName("Test Should Be Disabled")
    @Disabled
    public void shouldBeDisabled() {
        throw new RuntimeException("Test Should Not be executed");
    }

	
	
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("Should Execute After Each Test");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Should be executed at the end of the Test");
	}

}
