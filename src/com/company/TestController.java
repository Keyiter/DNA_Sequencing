package com.company;

import java.io.FileWriter;
import java.io.PrintWriter;

public class TestController {
    public void MakeFirstTest(){
        String[] negLos = new String[24];
        int[] count = {200,200,200,200,200,200,300,300,300,300,300,300,400,400,400,400,400,400,500,500,500,500,500,500};
        for(int i =0; i < negLos.length; i++){
            negLos[i] = "first/data" + (i > 8  ? i+1 : ("0"+(i+1))) + ".txt";
        }


       try{
            FileWriter negLosFile = new FileWriter("NegLos.txt");
            PrintWriter printWriter = new PrintWriter(negLosFile);
            printWriter.println("Instancje z błędami negatywnymi losowymi");
            int i = 0;
            for(String file : negLos){
                System.out.println("procesing of file " + file);
                printWriter.println("procesing of file " + file);
                printWriter.println("");

                SequenceController sequence = new SequenceController(file);
                int seqLength = count[i];
                i++;
                long start = System.currentTimeMillis();

                sequence.MakeGreedySearch(seqLength);

                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println("procesing of file " + file);
                printWriter.println("Processing time of greedy : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");

                 start = System.currentTimeMillis();
                sequence.heuristic(5000,100,seqLength,4);

                finish = System.currentTimeMillis();
                timeElapsed = finish - start;


                printWriter.println("Processing time of heuristic : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");
                printWriter.println("");
                printWriter.println("");
            }
           printWriter.close();
       } catch (Exception e){
           System.out.println("error" + e);
       }
    }

    public void MakeSecondTest(){

        String[] negPow = new String[5];
        int[] count = {500,500,500,500,500};

        for(int i =0; i < negPow.length; i++){
            negPow[i] = "second/data" + (i > 9 ? i : ("0"+i)) + ".txt";
        }

        try{
            FileWriter negLosFile = new FileWriter("negPow.txt");
            PrintWriter printWriter = new PrintWriter(negLosFile);
            printWriter.print("Instancje z błędami negatywnymi wynikającymi z powtórzeń");
            int i = 0;
            for(String file : negPow){
                System.out.println("procesing of file " + file);
                printWriter.print("procesing of file " + file);
                printWriter.print("");
                SequenceController sequence = new SequenceController(file);
                int seqLength = count[i];
                i++;

                long start = System.currentTimeMillis();

                sequence.MakeGreedySearch(seqLength);

                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println("Processing time of greedy : " + timeElapsed/1000.f);
                printWriter.println("Processing time of greedy : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");

                start = System.currentTimeMillis();
                sequence.heuristic(5000,100,seqLength,4);

                finish = System.currentTimeMillis();
                timeElapsed = finish - start;
                System.out.println("Processing time of heuristic : " + timeElapsed/1000.f);
                printWriter.println("Processing time of heuristic : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");
                printWriter.println("");
                printWriter.println("");
            }
            printWriter.close();
        } catch (Exception e){
            System.out.println("error");
        }
    }

    public void MakeThirdTest(){

        String[] pozLos = new String[12];
        int[] count = {200,200,200,300,300,300,400,400,400,500,500,500};
        for(int i =0; i < pozLos.length; i++){
            pozLos[i] = "third/data" + (i > 9  ? i : ("0"+i)) + ".txt";
        }

        try{
            FileWriter negLosFile = new FileWriter("pozLos.txt");
            PrintWriter printWriter = new PrintWriter(negLosFile);
            printWriter.println("Instancje z błędami pozytywnymi losowymi");
            int i =0;
            for(String file : pozLos){
                System.out.println("procesing of file " + file);
                printWriter.println("procesing of file " + file);
                printWriter.println("");
                SequenceController sequence = new SequenceController(file);
                int seqLength = count[i];
                i++;

                long start = System.currentTimeMillis();

                sequence.MakeGreedySearch(seqLength);

                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;


                printWriter.println("Processing time of heuristic : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");

                start = System.currentTimeMillis();
                sequence.heuristic(5000,100,seqLength,4);

                finish = System.currentTimeMillis();
                timeElapsed = finish - start;

                printWriter.println("Processing time of greedy : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");
                printWriter.println("");
                printWriter.println("");
            }
            printWriter.close();
        } catch (Exception e){
            System.out.println("error");
        }
    }

    public void MakeForthTest(){
        String[] pozPrzek = new String[12];
        int[] count = {200,200,200,300,300,300,400,400,400,500,500,500};
        for(int i =0; i < pozPrzek.length; i++){
            pozPrzek[i] = "forth/data" + (i > 9  ? i : ("0"+i)) + ".txt";
        }

        try{
            FileWriter negLosFile = new FileWriter("pozPrzek.txt");
            PrintWriter printWriter = new PrintWriter(negLosFile);
            printWriter.println("Instancje z błędami pozytywnymi, przekłamania na końcach oligonukleotydów");
            int i =0;
            for(String file : pozPrzek){
                System.out.println("procesing of file " + file);
                printWriter.println("procesing of file " + file);
                printWriter.println("");
                SequenceController sequence = new SequenceController(file);
                int seqLength = count[i];
                i++;

                long start = System.currentTimeMillis();

                sequence.MakeGreedySearch(seqLength);

                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;


                printWriter.println("Processing time of heuristic : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");

                start = System.currentTimeMillis();
                sequence.heuristic(5000,100,seqLength,4);

                finish = System.currentTimeMillis();
                timeElapsed = finish - start;

                printWriter.println("Processing time of greedy : " + timeElapsed/1000.f);
                printWriter.println(sequence.GetUniqueWords());
                printWriter.println(sequence.GetNumberOfWords());
                printWriter.println(sequence.GetTotalLength());
                printWriter.println("");
                printWriter.println("");
                printWriter.println("");
            }
            printWriter.close();
        } catch (Exception e){
            System.out.println("error");
        }
    }
}
