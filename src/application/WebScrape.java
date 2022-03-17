package application;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/** A Class that will scrape text from given URL, strip HTML tags, split and add all words to a String array, and
 *  finally, add each word to a database with frequency of 1 (if already in db, frequency is incremented). 
 *  @author derekdileo */
public class WebScrape {
	
	/** A Method for scrubbing text from a user-requested URL which removes HTML tags from each line, 
	 *  splits each line into individual words, adds all to a String array and, finally, returns the array for later processing.   
	 *  @param website is the URL that the user wants to process
	 *  @param sourceHead is the first line of text to be processed by the application
	 *  @param sourceEnd is the last line of text to be processed by the application
	 *  @throws IOException
	 *  @return String[] which contains every word on the requested site
	 *  @author Derek DiLeo */
	public static String[] parseSite(String website, String sourceHead, String sourceEnd) {
		try {
		// Instantiate the URL class
		URL url = new URL(website);
		
		// Retrieve the contents of the page
		Scanner sc = new Scanner(url.openStream());
		
		// Instantiate the StringBuffer class to hold the result
		StringBuffer sb = new StringBuffer();
		
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			
			// Detect user-indicated start
			if(line.toString().contains(sourceHead)) {
				
				//System.out.println("Line containing sourceHead: " + line);
				sb.append(" " + line);
				
				while(sc.hasNextLine()) {
					
					line = sc.nextLine();
					sb.append(" " + line);
					
					// Reached the user-indicated end
					if(line.toString().contains(sourceEnd)) {
						break;
					}
					
				}
				
			}
			
		}
		
		// Retrieve the String from the StringBuffer object
		String result = sb.toString();
		String[] words = null;
		
		// Remove the HTML tags
		String nohtml = result.toLowerCase().replaceAll("\\<.*?>", "");
		
		// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
		words = nohtml.split("[^a-zA-Z’]+"); 

        sc.close();
        return words;
        
		} catch (IOException e) {
			System.out.println("IOException in WebScrape.parseSite(): " + e);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/** A Method which uses String array from parseSite() to push words to database. 
	 *  @param words is a string array created by parseSite() method which 
	 *  contains every word (and its multiples) that was parsed. */
	public static void wordsToDB(String[] words) {
		try {
			for (String word : words) {
				// Do not allow white blank white space or "mdash"
				if (word.toString() != "" && word.toString() != " " && !word.toString().contains("mdash")
						&& !word.toString().contains("	")) {
					
					// Check if word exists in db (-1 if not)
					int frequency = Database.queryFrequency(word);
					
					// If word already in database...
					if (frequency != -1) {
						// Increment the frequency
						Database.update(word, ++frequency);
					} else {
						// Add to database
						Database.post(word, 1);
					}
					
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("Error in WebScrape.wordsToDB: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
