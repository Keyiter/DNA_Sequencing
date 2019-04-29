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
        Random random = new Random();

        //wybiera losowe słowo z sekwencji jako pierwsze
        sequence.AddWord(wordSet.wordSet.get(random.nextInt(wordSet.wordSet.size())),0,0);

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
            System.out.println(sequence.getWord(i));
        }
    }
}
