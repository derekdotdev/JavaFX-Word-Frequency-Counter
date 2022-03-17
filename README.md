# cen3024-module-006-word-frequency-count-gui

## This Java program scrapes [The Raven by Edgar Allen Poe eBook on Project Gutenberg's website] (https://www.gutenberg.org/files/1065/1065-h/1065-h.htm), 
tracks each word and its occurrences and then returns the top 10 occurrences to a JavaFX GUI. 

[Link to video demo] (https://vimeo.com/636527956)

###### This is done in the following steps:

- Copy InputStream (from URL) to a text file in the project directory under src/resources/doc.

- Parse each line of text with Scanner to a String, use replaceAll() and RegEx to strip away html tags.

- Put words in a String array using line.split() with RegEx to only include letters and apostrophes (allow contractions).

- Iterate through String array and add to HashMap<String, Integer> with key=word, value=occurrences.

- Iterate through the HashMap, instantiate a Word (which implements Comparable<Word>) object with attributes passed from key/value
  and add all to ArrayList<Word>.

- Sort the ArrayList<Word> and then reverse to put in ascending order.

- Print all to console and push top 10 to JavaFX GUI using Label.setText() method. 
