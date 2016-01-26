import STEMMER.SnowballStemmer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * It reads a CSV file as genres and stores them into a map.
 */

public class GenresReader {

    HashMap<String, Genre> generTable;

    public GenresReader(){
        generTable = new HashMap<>();
    }

    // separate the read in method so that it can read as many csv file as we want
    public void readInCSV(String filePath) throws Exception{
        File file = new File(filePath);
        BufferedReader in = new BufferedReader(new FileReader(file));

        /**
         * initialize a stemmer
         */
        Class stemClass = Class.forName("STEMMER.englishStemmer");
        SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();

        String line = in.readLine();

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

            if (! generTable.containsKey(strs[0])) {
                generTable.put(genre_name, new Genre(genre_name));
            }
            generTable.get(genre_name).key_value.put(keyword, value);
        }
    }

    // whether a string represents a number
    public boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    // for display and debug
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : generTable.keySet()) {
            stringBuilder.append(key + " " + generTable.get(key).toString() + "\n");
        }
        return stringBuilder.toString();
    }

}
