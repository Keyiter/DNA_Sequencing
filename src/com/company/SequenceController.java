package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceController {
    WordSet wordSet;
    Sequence sequence;
    Sequence max;
    SequenceController(String filename){
        wordSet = new WordSet(filename);
        sequence = new Sequence();
    }

    public int GetFileLength(){
        return wordSet.wordSet.size();
    }

    public void MakeGreedySearch(int maxLength){
        //wybiera losowe słowo z sekwencji jako pierwsze

        sequence.AddWord(getRandomIndex(wordSet.wordSet.size()),0,0);

        boolean ret = true;
        while(sequence.getSequenceNucleotidesCount() <= maxLength && ret) {
            boolean unique= true;
            ArrayList<Integer> tabu = new ArrayList<Integer>();
            do {
                WordWeightPair foundWord = wordSet.FindBestMatchingWord(sequence.getLastWord(), 100, tabu);
                if(foundWord.weight == -1) {
                    if(!tabu.isEmpty()) {
                        foundWord = wordSet.getConnectionInfo(sequence.getLastWord(), tabu.get(0));
                        sequence.AddWord(foundWord.word,foundWord.weight,foundWord.connectionStrength);
                        break;
                    }
                    else {
                        ret = false;
                        break;
                    }
                } else {
                    if (sequence.contains(foundWord.word)) {
                        unique = false;
                        tabu.add(foundWord.word);
                    }
                    else {
                        sequence.AddWord(foundWord.word,foundWord.weight,foundWord.connectionStrength);
                        unique = true;
                    }
                }
            }while(!unique);
        }
        max = new Sequence( sequence);
    }

    public void PrintUniqueWords(){
        //System.out.println( "Number of unique words: " + sequence.getUniqueWords());
        System.out.println( "Number of max unique words: " + max.getUniqueWords());
    }

    public void PrintTotalLength(){
        //System.out.println( "Total Length of sequence: " + sequence.getSequenceNucleotidesCount());
        System.out.println( "Total max Length of sequence: " + max.getSequenceNucleotidesCount());
    }

    public void PrintNumberOfWords(){
        //System.out.println( "Total number of words: " + sequence.getSequenceWordCount());
        System.out.println( "Total max number of words: " + max.getSequenceWordCount());
    }

    public String GetUniqueWords(){
        //System.out.println( "Number of unique words: " + sequence.getUniqueWords());
        return ("Number of max unique words: " + max.getUniqueWords());
    }

    public String GetTotalLength(){
        //System.out.println( "Total Length of sequence: " + sequence.getSequenceNucleotidesCount());
        return ( "Total max Length of sequence: " + max.getSequenceNucleotidesCount());
    }

    public String GetNumberOfWords(){
        //System.out.println( "Total number of words: " + sequence.getSequenceWordCount());
        return ( "Total max number of words: " + max.getSequenceWordCount());
    }

    public void PrintUsedWords(){
        int max = sequence.getSequenceWordCount();
        for(int i=0; i < max; i++){
            System.out.println(wordSet.wordSet.get(sequence.getWord(i)));
        }
    }

    public void heuristic(int iterateCount,int startTemperature,int maxLength,int maksBestWordToDraw) {
        float cooler=(float)startTemperature/iterateCount ;
        float temperature=startTemperature;
        Sequence temporarySequence=new Sequence(this.sequence);
        max = new Sequence(sequence);
        float powNr = 2;
        for(int i=0;i<iterateCount;i++){
            if(i % (iterateCount/4) == 0)
                powNr ++;
            cooler *= 1 + (iterateCount*(0.0001/iterateCount));
            sequence=heuristicIteration(temperature-=cooler ,startTemperature,temporarySequence,maxLength,maksBestWordToDraw,(int)powNr);

            if(sequence.getUniqueWords() > max.getUniqueWords())
                max = new Sequence(sequence);


            if(i % (iterateCount/5) ==0) {
                System.out.println("Iteration: " + i + " current Unique words: " + sequence.getUniqueWords());
               // System.out.println(temperature);
            }
        }

    }

    private Sequence heuristicIteration(float temperature,float startTemp,Sequence temporarySequence,int maxLength,int maksBestWordToDraw, int powNr) {
            ArrayList<Integer> connections = new ArrayList<>();
            for(int i =0; i < temporarySequence.connections.size();i++){
                for(int j = 0; j < 10- temporarySequence.connections.get(i); j++){
                    connections.add(i);
                }
            }
            int drawnIndex = connections.get(getRandomIndex(connections.size()));
            //int drawnIndex = getRandomIndex(temporarySequence.words.size());
            temporarySequence.DropFromPlace(drawnIndex); //metoda dropFromPlace za kazdym razem liczy unique word nie potrzebnie

       temporarySequence= buildSequence(temporarySequence,maxLength,maksBestWordToDraw,powNr);
       return(chooseSequence(temperature,startTemp,temporarySequence));


    }

    private Sequence chooseSequence(float temperature,float startTemp, Sequence temporarySequence) {
        if(sequence.getSequenceWordCount()<temporarySequence.getSequenceWordCount()) return temporarySequence;
        int sizeDifference=sequence.getSequenceWordCount()-temporarySequence.getSequenceWordCount();

        if(lotery(temperature,startTemp,sizeDifference) || sizeDifference <= 0){
            return new Sequence(temporarySequence);
        }
        return new Sequence(sequence);
    }

    private boolean lotery(float temperature,float startTemp, int sizeDifference) {
        Random random=new Random();
        if(random.nextDouble() * startTemp <(float) temperature/sizeDifference) return true;
        return false;

    }

    private Sequence buildSequence(Sequence temporarySequence,int maxLength,int maksBestWordToDraw,int powNr) {
        while(temporarySequence.getSequenceNucleotidesCount()<maxLength){
           WordWeightPair wordWeightPairToAdd =  wordSet.drawnNextWord(temporarySequence,maksBestWordToDraw,powNr);
           if(wordWeightPairToAdd.word==-1) break; // nie znaleziono nast słowa
           temporarySequence.AddWord(wordWeightPairToAdd.word,wordWeightPairToAdd.weight,wordWeightPairToAdd.connectionStrength);
        }
        return temporarySequence;
    }

    private int getRandomIndex(int bound) {
        Random  random=new Random();
        try {
            return random.nextInt(bound);
        }catch (Exception e){
            System.out.println("In SesContrl " + e);
            return -1;
        }
    }

    public void test(){
        Sequence seq1 = new Sequence();
        Sequence seq2 = new Sequence();
        seq1.AddWord(1,2,3);
        seq1.AddWord(2,3,4);
        seq2 = new Sequence(seq1);
        //seq2 = seq1;
        seq2.AddWord(3,4,5);
        int max = seq1.getSequenceWordCount();
        for(int i=0; i < max; i++){
            System.out.println(wordSet.wordSet.get(seq1.getWord(i)));
        }
        System.out.println("");
        int max2 = seq2.getSequenceWordCount();
        for(int i=0; i < max2; i++){
            System.out.println(wordSet.wordSet.get(seq2.getWord(i)));
        }
    }
}
