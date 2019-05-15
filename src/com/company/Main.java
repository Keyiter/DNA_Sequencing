package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
       // System.out.println("Insert File name: ");
        String filename = "data.txt"; //scan.nextLine();
       // System.out.println("Insert Max Sequence Length ");
        int maxLength = 200; //scan.nextInt();

        SequenceController sequence = new SequenceController(filename);

        long start = System.currentTimeMillis();

        sequence.MakeGreedySearch(maxLength);

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;

        System.out.println("Processing time: " + timeElapsed/1000.f);

        sequence.PrintUniqueWords();
        sequence.PrintNumberOfWords();
        sequence.PrintTotalLength();
        //sequence.PrintUsedWords();



        start = System.currentTimeMillis();

//maksBestWordToDraw to liczba najlepszych słów z któych ma algorytm losować przy dobudowywaniu sekwencji
        //maxLength to długość sekwencji maksymalna
       sequence.heuristic(1000,100,maxLength,2);


        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        sequence.PrintUniqueWords();
        sequence.PrintNumberOfWords();
        sequence.PrintTotalLength();
        System.out.println("Processing time: " + timeElapsed/1000.f);
    }

}
