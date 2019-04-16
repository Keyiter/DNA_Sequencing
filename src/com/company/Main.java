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
        int maxLength = 300; //scan.nextInt();

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


    }

}
