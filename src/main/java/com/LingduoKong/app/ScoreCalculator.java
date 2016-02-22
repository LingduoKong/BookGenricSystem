package com.LingduoKong.app;

import java.util.*;

/**
 * It is the integration of book reader and csv reader.
 * It is responsible for calculating the final result of each book and printing it.
 */

public class ScoreCalculator {

    HashMap<String, Word> invertedIndex;
    HashMap<String, Genre> genreTable;
    ArrayList<String> titleList;

    public ScoreCalculator(BookReader bookReader, GenresReader genresReader) {
        this.invertedIndex = bookReader.invertedIndex;
        this.genreTable = genresReader.genreTable;
        titleList = bookReader.titles;
    }

    // calculate each book's genre scores and print them
    public void calculateAll() {

        Collections.sort(titleList);

        for (String title : titleList) {

            PriorityQueue<GenreEntry> maxHeap = new PriorityQueue<>(3, new Comparator<GenreEntry>() {
                @Override
                public int compare(GenreEntry o1, GenreEntry o2) {
                    return o2.score - o1.score;
                }
            });

            System.out.println(title);

            HashMap<String, Integer> fit_scores = this.calculateBook(title);

            for (String s : fit_scores.keySet()) {
                maxHeap.add(new GenreEntry(s, fit_scores.get(s)));
            }

            for (int i = 0; i < 3 && maxHeap.size() > 0; i++) {
                System.out.println(maxHeap.poll().toString());
            }

            System.out.println();

        }

    }

    // calculate the scores of a single book and store them in a map
    public HashMap<String, Integer> calculateBook(String title) {

        HashMap<String, Integer> result = new HashMap<>();

        for (String genre_name : genreTable.keySet()) {

            Genre genre = genreTable.get(genre_name);

            HashMap<String, Integer> temp = new HashMap<>();

            for (String key : genre.key_value.keySet()) {

                int n = numOfExitedKey(key, title);

                if (n != 0) {
                    temp.put(key, n);
                }
            }

            int count = 0;
            int sum = 0;
            for (String key : temp.keySet()) {
                count += temp.get(key);
                sum += genre.key_value.get(key);
            }

            int fit_score = 0;

            if (temp.keySet().size() > 0) {
                int avg_point = sum / temp.keySet().size();
                fit_score = avg_point * count;
            }

            result.put(genre_name, fit_score);
        }

        return result;

    }

    // calculate how many time the key appears in the book's description.
    public int numOfExitedKey(String key, String title) {

        if (key == null || key.length() == 0) {
            return 0;
        }

        String[] keys = key.split(" ");

        if (!invertedIndex.containsKey(keys[0])) {

            return 0;
        }

        ArrayList<Integer> start_pos = invertedIndex.get(keys[0]).find(title);

        if (keys.length == 1) {

            return start_pos.size();

        } else {

            int count = 0;

            for (int pos : start_pos) {

                for (int i = 1; i < keys.length; i++) {

                    if (!invertedIndex.get(keys[i]).find(title, pos + i)) {
                        break;
                    }

                    if (i == keys.length - 1) {
                        count += 1;
                    }
                }
            }
            return count;
        }
    }

    /**
     * the class for a entry of the final result
     * be more convenient to print result;
     */
    class GenreEntry {
        String genre_name;
        int score;
        public GenreEntry(String genre_name, int score) {
            this.genre_name = genre_name;
            this.score = score;
        }
        public String toString() {
            return genre_name + ", " + score;
        }
    }

}
