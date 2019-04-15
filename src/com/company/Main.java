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
        int maxLength = 1000; //scan.nextInt();

        SequenceController sequence = new SequenceController(filename);

        sequence.MakeGreedySearch(maxLength);

        //find random first

        sequence.PrintUniqueWords();
        sequence.PrintNumberOfWords();
        sequence.PrintTotalLength();



    }

}
