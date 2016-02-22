package com.LingduoKong.app;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * It reads a CSV file as genres and stores them into a map.
 */

public class GenresReader {

    HashMap<String, Genre> genreTable;

    /**
     * create a genre reader instance without any arguments
     * Initialize a table which maps a genre with keywords and factors
     */
    public GenresReader(){
        genreTable = new HashMap<>();
    }

    /**
     * read in a CSV file with genre keywords and factors
     * parse them and store to the genre table
     * @param filePath is the path of CSV file
     * @throws Exception throw ClassNotFound and FileNotExisted exceptions
     */
    public void readInCSV(String filePath) throws Exception{
        File file = new File(filePath);
        BufferedReader in = new BufferedReader(new FileReader(file));

        englishStemmer stemmer = new englishStemmer();

        String line = null;

        while ((line = in.readLine()) != null) {

            if (line.length() == 0) {
                continue;
            }

            String[] strs = line.split(",");

            if (strs.length != 3 || !isNumeric(strs[2].trim())) {
                throw new Exception("Illegal genre entry: " + line);
            }

            String genre_name = strs[0].trim();
            String keyword = strs[1].trim().toLowerCase();

            /**
             * process key word
             */

            String[] strings = keyword.split(" ");
            for (int i = 0; i < strings.length; i++) {
                stemmer.setCurrent(strings[i]);
                stemmer.stem();
                strings[i] = stemmer.getCurrent();
            }
            keyword = "";
            for (int i = 0; i < strings.length; i++) {
                keyword += strings[i] + " ";
            }


            if (genre_name.length() == 0 || keyword.length() == 0) {
                throw new Exception("Illegal genre entry: " + line);
            }

            int value = Integer.parseInt(strs[2].trim());

            if (! genreTable.containsKey(strs[0])) {
                genreTable.put(genre_name, new Genre(genre_name));
            }
            genreTable.get(genre_name).key_value.put(keyword, value);
        }
    }

    // whether a string represents a number
    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * convert current genre table to a string for testing and viewing
     * @return the string representing whole table
     */
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : genreTable.keySet()) {
            stringBuilder.append(key + " " + genreTable.get(key).toString() + "\n");
        }
        return stringBuilder.toString();
    }

}
