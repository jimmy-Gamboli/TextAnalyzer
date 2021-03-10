import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * A class that represents a text analyzer object whose functions include
 * getting word frequency, character frequency, and word frequency with a stop
 * list
 * 
 * 
 * @author Jimmy Gamboli
 *
 */

public class TextAnalyzer {

	protected Map map;
	protected File book;
	protected ArrayList<String> fileLines;
	protected Scanner reader;

	/**
	 * initializes variables and a hashmap for the text analysis
	 * 
	 * 
	 * @param book
	 * @throws FileNotFoundException
	 */
	public TextAnalyzer(File book) throws FileNotFoundException {
		this.book = book;
		map = new HashMap<String, Integer>();
		readLines();
	}

	/**
	 * helper method that reads in each line in text file and places each text file
	 * line into an ArrayList known as fileLines
	 * 
	 * Makes us of printTopTen() helper method
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	private void readLines() throws FileNotFoundException {
		try {

			reader = new Scanner(book);
			fileLines = new ArrayList<String>();
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				fileLines.add(data);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occured. The file was not found");
			e.printStackTrace();
		}
	}

	/**
	 * Method that iterates through fileLines and calculates the frequency of each
	 * word of a given text file that was specified in the object's constructor.
	 * 
	 * Makes us of printTopTen() helper method to print top 10 most frequent words
	 */
	public void wordFreq() {

		map = new HashMap<String, Integer>();

		for (String line : fileLines) {
			Scanner scan = new Scanner(line);
			scan.useDelimiter("[^a-zA-Z']");
			while (scan.hasNext()) {
				String word = scan.next();
				word = word.toLowerCase();

				word = word.replaceAll("^'+", "");
				word = word.replaceAll("'+$", "");

				if (!word.isEmpty()) {
					if (map.containsKey(word)) {
						map.put(word, (int) map.get(word) + 1);
					} else {
						map.put(word, 1);
					}
				}

			}
		}
		printTopTen();
	}

	/**
	 * Count the frequency of each character and prints the character and frequency
	 * of the top 10 most common characters in text file in order from most to least
	 * frequent
	 * 
	 * Makes us of printTopTen() helper method
	 */
	public void charFreq() {

		map = new HashMap<String, Integer>();

		for (String line : fileLines) {

			line = line.toLowerCase();
			line = line.replaceAll("[^a-zA-Z ]", "");
			for (char c : line.toCharArray()) {
				if (Character.isLetter(c)) {
					String s = Character.toString(c);
					if (map.containsKey(s)) {
						map.put(s, (int) map.get(s) + 1);
					} else {
						map.put(s, 1);
					}

				}

			}
		}

		printTopTen();
	}

	/**
	 * Find the top ten words in a text file in terms of frequency that are not in
	 * the stopList file and prints them from most to least frequent
	 * 
	 * @param stopList
	 * @throws FileNotFoundException
	 */
	public void wordFreqStopList(File stopList) throws FileNotFoundException {

		map = new HashMap<String, Integer>();
		Set<String> stopListWords = new HashSet<String>();

		try {

			reader = new Scanner(stopList);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				stopListWords.add(data);
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occured. The file was not found");
			e.printStackTrace();
		}

		for (String line : fileLines) {
			Scanner scan = new Scanner(line);
			scan.useDelimiter("[^a-zA-Z']");
			while (scan.hasNext()) {
				String word = scan.next();

				word = word.toLowerCase();
				word = word.replaceAll("^'+", "");
				word = word.replaceAll("'+$", "");

				if (!word.isEmpty() && !stopListWords.contains(word)) {
					if (map.containsKey(word)) {
						map.put(word, (int) map.get(word) + 1);
					} else {
						map.put(word, 1);
					}
				}

			}
		}
		printTopTen();
	}

	/**
	 * 
	 * Finds and prints the top ten words in the text file that do not contain a
	 * user-inputted letter
	 * 
	 * It also prints the percentage of words in the file that do not contain the
	 * letter that is passed as a parameter
	 * 
	 * @param letter- a character representing the letter that is to not be included
	 */
	public void wordsWithoutLetter(char letter) {
		int numWords = 0;
		int numLetter = 0;
		map = new HashMap<String, Integer>();

		for (String line : fileLines) {
			reader = new Scanner(line);
			reader.useDelimiter("[^a-zA-Z']");

			while (reader.hasNext()) {
				String word = reader.next();
				word = word.toLowerCase();
				numWords++;
				word = word.replaceAll("^'+", "");
				word = word.replaceAll("'+$", "");

				if (!word.isEmpty() && !word.contains(Character.toString(letter))) {
					if (map.containsKey(word)) {
						map.put(word, (int) map.get(word) + 1);
					} else {
						map.put(word, 1);

					}
					numLetter++;
				}

			}
		}
		printTopTen();
		System.out.println("percentage of words without e: " + (numLetter * 1.0 / numWords) * 100);

	}

	/**
	 * Helper method that will take all the data in a given hashmap, sort the
	 * entries based on value from highest to lowest, and then print the top ten
	 * MapEntry objects.
	 * 
	 * Makes us of MapEntry method that returns a comparator and Collection.sort()
	 * to sort the list of MapEntry objects in hashMap
	 */
	private void printTopTen() {

		Set<Map.Entry<String, Integer>> set = map.entrySet();

		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();

		for (Map.Entry<String, Integer> entry : set) {
			list.add(entry);
		}

		Comparator<Map.Entry<String, Integer>> comp = Map.Entry.comparingByValue();
		Collections.sort(list, comp.reversed());

		for (int i = 0; i < 10; i++) {
			System.out.println((i + 1) + ": " + list.get(i));
		}
	}

}
