import JSON.*;
import STEMMER.SnowballStemmer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * read in book info from a json file
 * generate a inverted index for each word
 */

public class BookReader {

    HashMap<String, Word> invertedIndex;
    ArrayList<String> titles;

    public BookReader() {
        invertedIndex = new HashMap<>();
        titles = new ArrayList<>();
    }

    // read in json book list from a file and parse the json object
    public void readInBooks(String filePath) throws Exception {
        File file = new File(filePath);
        BufferedReader in = new BufferedReader(new FileReader(file));

        String content = "";
        String line = null;

        while ((line = in.readLine()) != null) {
            content += line;
        }

        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject book = jsonArray.getJSONObject(i);
            String title = book.getString("title");

            titles.add(title);

            String description = book.getString("description");

            processBook(title, description);
        }
    }

    // get the content of a book and insert them into the table
    public void processBook(String title, String description) throws Exception {

        if (title == null) {
            throw new Exception("Illegal title with no content!");
        }

        if (description == null) {
            throw new Exception("Illegal description with no content!");
        }

        StringBuilder stringBuilder = new StringBuilder();

        /**
         * initialize a stemmer
         */
        Class stemClass = Class.forName("STEMMER.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

        int counter = 0;
        for (int i = 0; i < description.length(); i++) {
            char c = description.charAt(i);

            if (Character.isAlphabetic(c)) {
                stringBuilder.append(c);
            } else {

                if (stringBuilder.length() == 0) {
                    continue;
                }

                counter += 1;

                String word = stringBuilder.toString().toLowerCase();
                stringBuilder = new StringBuilder();

                /**
                 * add a stemmer for word precessing
                 */
                stemmer.setCurrent(word);
                stemmer.stem();
                word = stemmer.getCurrent();

                if (!invertedIndex.containsKey(word)) {
                    invertedIndex.put(word, new Word(word));
                }

                Word w = invertedIndex.get(word);
                w.insertNewPos(title, counter);

            }
        }
    }

    // for debug and later storage
    public String toString() {

        HashMap<String, JSONObject> tempMap = new HashMap<>();
        for (String key : invertedIndex.keySet()) {
            tempMap.put(key, new JSONObject(invertedIndex.get(key).toString()));
        }
        JSONObject invertedIndex = new JSONObject(tempMap);
        return invertedIndex.toString();
    }

}
