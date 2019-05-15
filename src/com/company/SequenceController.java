package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceController {
    WordSet wordSet;
    Sequence sequence;
    List<Sequence> listOfNeighbor;
    SequenceController(String filename){
        wordSet = new WordSet(filename);
        sequence = new Sequence();
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
    }

    public void PrintUniqueWords(){
        System.out.println("Number of unique words: " + sequence.getUniqueWords());
    }

    public void PrintTotalLength(){
        System.out.println("Total Length of sequence: " + sequence.getSequenceNucleotidesCount());
    }

    public void PrintNumberOfWords(){
        System.out.println("Total number of words: " + sequence.getSequenceWordCount());
    }

    public void PrintUsedWords(){
        int max = sequence.getSequenceWordCount();
        for(int i=0; i < max; i++){
            System.out.println(wordSet.wordSet.get(sequence.getWord(i)));
        }
    }

    public void heuristic(int iterateCount,int startTemperature,int maxLength,int maksBestWordToDraw) {
        float cooler=(float)startTemperature/iterateCount;
        float temperature=startTemperature;
        Sequence temporarySequence=this.sequence;
        for(int i=0;i<iterateCount;i++){
            temporarySequence=heuristicIteration(temperature-=cooler,temporarySequence,maxLength,maksBestWordToDraw);
        }
        sequence=temporarySequence;
    }

    private Sequence heuristicIteration(float temperature,Sequence temporarySequence,int maxLength,int maksBestWordToDraw) {
        int drawnIndex=getRandomIndex(temporarySequence.getSequenceWordCount());

        temporarySequence.DropFromPlace(drawnIndex); //metoda dropFromPlace za kazdym razem liczy unique word nie potrzebnie

       temporarySequence= buildSequence(temporarySequence,maxLength,maksBestWordToDraw);
       return(chooseSequence(temperature,temporarySequence));


    }

    private Sequence chooseSequence(float temperature, Sequence temporarySequence) {
        if(sequence.getSequenceWordCount()<temporarySequence.getSequenceWordCount()) return temporarySequence;
        int sizeDifference=sequence.getSequenceWordCount()-temporarySequence.getSequenceWordCount();
        if(lotery(temperature,sizeDifference)){
            return temporarySequence;
        }
        return sequence;
    }

    private boolean lotery(float temperature, int sizeDifference) {
       // Random random=new Random();
       // if(random.nextDouble()<(float) sizeDifference/temperature) return true;
        return false;

    }

    private Sequence buildSequence(Sequence temporarySequence,int maxLength,int maksBestWordToDraw) {
        while(temporarySequence.getSequenceNucleotidesCount()<maxLength){
           WordWeightPair wordWeightPairToAdd =  wordSet.drawnNextWord(temporarySequence,maksBestWordToDraw);
           if(wordWeightPairToAdd.word==-1) break; // nie znaleziono nast słowa
           temporarySequence.AddWord(wordWeightPairToAdd.word,wordWeightPairToAdd.weight,wordWeightPairToAdd.connectionStrength);
        }
        return temporarySequence;
    }

    private int getRandomIndex(int bound) {
        Random  random=new Random();
        return random.nextInt(bound);
    }
}
