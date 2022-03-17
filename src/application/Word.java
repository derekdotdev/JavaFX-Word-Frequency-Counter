package application;

/** Word Class objects are created from HashMap(of String, Integer) (key=word, value=frequency) and 
 * implements Comparable(of Word) so ArrayList(Word) can be sorted and overrides toString() to print results to console. 
 * @author derekdileo */
public class Word implements Comparable<Word> {

	// Local variables
	private String word;
	private int frequency;

	// Constructor
	public Word(String word, int frequency) {
		super();
		this.word = word;
		if (frequency > 0) {
			this.frequency = frequency;
		}
	}
	
	// Getters & Setters
	public String getWord() {
		return word;
	}

	public int getFrequency() {
		return frequency;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setFrequency(int frequency) {
		if(frequency > 0) {
			this.frequency = frequency;
		}
	}
	
	public void validateWord() {
		if(this.word.isEmpty()) {
			throw new RuntimeException("Word Cannot be null or empty");
		}
	}

	public void validateFrequency() {
		if(this.frequency <= 0) {
			throw new RuntimeException("Frequency Cannot be negative");
		}
		String str = "" + frequency;

		if(!str.matches("\\d")) {
			throw new RuntimeException("Frequency should Contain only digits");
		}
		
 	}
	
	/**Implemented Method that allows Word objects to be compared
	 * in order to allow ArrayList(of Word) to be sorted by value (frequency). */
	@Override
	public int compareTo(Word word) {
		return this.frequency - word.getFrequency();
	}
	
	/** Method which takes index value (in sorted list) for final output of values to console and GUI
	 * @param index is index location in ArrayList(of Word) sorted by frequency (value)  
	 * @return returns a String to be printed to console and GUI */
	public String toString(int index) {
		int size = word.length();
		
		// Account for 10 sticking out
		if (index < 9) {
			if(size <= 4) {
				return "\n " + (index + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n " + (index + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n " + (index + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n " + (index + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
		} else {
			if(size <= 4) {
				return "\n" + (index + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n" + (index + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n" + (index + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n" + (index + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
			
		}
		
	}

}
