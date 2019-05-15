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
        while(sequence.getNumberOfWords() <= maxLength && ret) {
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
                        tabu.add(wordSet.wordSet.indexOf(foundWord.word));
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
        System.out.println("Total Length of sequence: " + sequence.getNumberOfWords());
    }

    public void PrintNumberOfWords(){
        System.out.println("Total number of words: " + sequence.getSequenceSize());
    }

    public void PrintUsedWords(){
        int max = sequence.getSequenceSize();
        for(int i=0; i < max; i++){
            System.out.println(wordSet.wordSet.get(sequence.getWord(i)));
        }
    }

    public void heuristic(int iterateCount,int startTemperature,int maxLength) {
        float cooler=(float)startTemperature/iterateCount;
        float temperature=startTemperature;
        Sequence temporarySequence=this.sequence;
        for(int i=0;i<iterateCount;i++){
            heuristicIteration(temperature-=cooler,temporarySequence,maxLength);
        }
    }

    private void heuristicIteration(float temperature,Sequence temporarySequence,int maxLength) {
        int drawnIndex=getRandomIndex(temporarySequence.getSequenceSize());

        temporarySequence.DropFromPlace(drawnIndex); //metoda dropFromPlace za kazdym razem liczy unique word nie potrzebnie

        buildSequence(temporarySequence,maxLength);

    }

    private void buildSequence(Sequence temporarySequence,int maxLength) {
        while(temporarySequence.getSequenceSize()<maxLength){
         //   temporarySequence.AddWord -- wordSet.drawnNextWord(temporarySequence,6); konieczne zmienienie sequence zeby przechowywala indeksy
        }
    }

    private int getRandomIndex(int bound) {
        Random  random=new Random();
        return random.nextInt(bound);
    }
}
