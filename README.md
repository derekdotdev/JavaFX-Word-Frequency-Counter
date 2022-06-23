# JavaFX Word Frequency Counter

## By default, this JavaFX program was created to scrape [The Raven by Edgar Allen Poe eBook on Project Gutenberg's website] (https://www.gutenberg.org/files/1065/1065-h/1065-h.htm),
and track each word and its occurrences before returning the top 10 occurrences to a GUI. It now has the ability to accept any URL and a start + end point to calculate the number of occurences of each word on a given web page.

[Here's a video demonstration] (https://vimeo.com/636527956)

###### This is done in the following steps:

- Accept the URL, start and end points from the user,

- Process the user input:
  - Add each line of the page to a string buffer and write buffer to a single String object
  - Use replaceAll() and RegEx to strip away html tags and toLowerCase() to avoid duplicate entries ('The' and 'the').
  - Split string into individual words using split() with RegEx to ignore all characters except alphabet and apostrophe (allow contractions) and add resultant words to a String array.
  - Iterate over String array and add unique entries to local MySQL database (formerly HashMap<String, Integer> with key=word, value=occurrences).
  - Finally, iterate over data and display Top 10 and All results to the JavaFX GUI

This project was created for CEN3024C - Software Development I - Module 6 and is saved locally as cen3024-module-006-word-frequency-count-gui
