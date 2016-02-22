package com.LingduoKong.app;

import org.json.JSONArray;
import org.json.JSONObject;
import org.tartarus.snowball.SnowballStemmer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * read in book info from a json file
 * generate a inverted index for each word
 */

public class BookReader {

    HashMap<String, Word> invertedIndex;
    ArrayList<String> titles;

    /**
     * initialize a book reader which get books' information and store them to an inverted index table.
     */
    public BookReader() {
        invertedIndex = new HashMap<>();
        titles = new ArrayList<>();
    }

    /**
     * read in a JSON file for book information and store them into an inverted index table.
     * @param filePath is the OS path of a file, which contains book info stored in JSON format.
     * @throws Exception are FileNotExisted exception and other exceptions about JSON readIn procedure.
     */
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
    private void processBook(String title, String description) throws Exception {

        if (title == null) {
            throw new Exception("Illegal title with no content!");
        }

        if (description == null) {
            throw new Exception("Illegal description with no content!");
        }

        StringBuilder stringBuilder = new StringBuilder();

        /*
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

                /*
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

    /**
     * Convert the inverted Index table to string for two reasons.
     * First: debug
     * Second: store to a file and reload next time without recalculating books.
     * @return a string in JSON format of current inverted index table
     */
    public String toString() {

        HashMap<String, JSONObject> tempMap = new HashMap<>();
        for (String key : invertedIndex.keySet()) {
            tempMap.put(key, new JSONObject(invertedIndex.get(key).toString()));
        }
        JSONObject invertedIndex = new JSONObject(tempMap);
        return invertedIndex.toString();
    }

}
