package com.LingduoKong.app;

/**
 * Created by lingduokong on 2/22/16.
 */
public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: ScoreCalculator <book JSON file> <genre CSV file>");
        }

        BookReader bookReader = new BookReader();
        GenresReader genresReader = new GenresReader();

        try {

            bookReader.readInBooks(args[0]);

            genresReader.readInCSV(args[1]);

            ScoreCalculator scoreCalculator = new ScoreCalculator(bookReader, genresReader);

            scoreCalculator.calculateAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
