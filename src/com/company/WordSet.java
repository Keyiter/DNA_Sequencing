package com.company;

import javafx.collections.transformation.SortedList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordSet {

    public ArrayList<IndeksValue>[] coverLists;
    public List<String> wordSet;
    WordSet(String _fileName) {
        wordSet = new ArrayList<String>();
        coverLists=new ArrayList[500];
        try {
            FileReader fileReader = new FileReader(_fileName);
            String line = null;
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            Integer count = 1;
            while ((line = bufferedReader.readLine()) != null) {
                wordSet.add(line);
                count++;
            }
            bufferedReader.close();
            prepareCoverTable();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + _fileName);
        } catch (IOException ex) {
            System.out.println("Error reading file " + _fileName);
        }
    }

    private void prepareCoverTable() {
        for (int i=0;i<wordSet.size();i++) {
            String word = wordSet.get(i);
            Integer indeks = i;
            for (int o=0;o<wordSet.size();o++) {
                String secondWord = wordSet.get(o);
                Integer secondIndeks = o;
                if (secondIndeks != indeks) {
                    if (coverLists[indeks] == null) {
                        coverLists[indeks] = new ArrayList<>();
                    }
                    coverLists[indeks].add(new IndeksValue(secondIndeks, getCoverNumber(word, secondWord)));
                }
            }
            Collections.sort(coverLists[indeks]);
            Collections.reverse(coverLists[indeks]);
        }
    }

    private int getCoverNumber(String word, String word2) {
        int length = word.length();
        for (int i = 1; i < length; i++) {
            if (word2.substring(0, length  - i).equals(word.substring(i, length ))) {
                return length - i;
            }
        }
        return 0;
    }
    public WordWeightPair getConnectionInfo(Integer wordIndeks, int i){
        int conTemp = 0;
        int temp = 0;
        int wordInd = wordIndeks;
        String word=wordSet.get(wordIndeks);
        for(int j=1;j<10;j++){
            if(wordSet.get(i).substring(0, 9 - j).equals(word.substring(j, 9))){
                conTemp = 10-j;
                break;
            }
        }
        int max =0;
       for(IndeksValue indx : coverLists[i]){
           if(indx.getIndex() == wordInd )
               continue;
           temp = conTemp;
           temp += indx.getValue();
           if(temp > max){
               max = temp;
           }
           if(max >= 18)
               return new WordWeightPair(18,9, indx.getIndex());
       }

        WordWeightPair retVal;
        if(conTemp >0)
            retVal = new WordWeightPair(max+conTemp,conTemp,i);
        else
            retVal = new WordWeightPair(-1,-1,-1);

        return retVal;
    }

    public WordWeightPair FindBestMatchingWord(Integer word, float probability, List<Integer> notUniqueTabu){
        List<Integer> tabuList = new ArrayList<>();
        float rand =0;
        boolean found;
        int max =0;
        int conMax =0;
        int index =0;
        int wordInd = word;
        do {

            if(max > 0)
                tabuList.add(index);

            max = 0;
            index = 0;
            found = false;

            for (IndeksValue indx : coverLists[wordInd]) {
                int temp=0;
                int conTemp =0;

                if(tabuList.contains(indx.getIndex()) || notUniqueTabu.contains(indx.getIndex()))
                    continue;
                temp = indx.getValue();
                conTemp = indx.getValue();
                found = true;

                if(max - 9 > temp)
                    continue;

                for (IndeksValue indx2 : coverLists[indx.getIndex()]) {
                    if(indx2.getIndex() == wordInd )
                        continue;
                    temp = conTemp;
                    temp += indx2.getValue();
                    if(temp > max){
                        max = temp;
                        conMax = conTemp;
                        index = indx.getIndex();
                    }
                    if(max >= 18)
                        return new WordWeightPair(18,9, indx.getIndex());
                }
            }


            if(probability<1){
                Random random = new Random();
                rand = random.nextFloat();
            }

        } while(probability <1 && rand >= probability);

        WordWeightPair retVal;
        if(found)
            retVal = new WordWeightPair(max,conMax, index);
        else if(!tabuList.isEmpty())
            retVal = new WordWeightPair(max/3,conMax, tabuList.get(0));
        else
             retVal = new WordWeightPair(-1,-1,-1);

        return  retVal;

    }

    public WordWeightPair drawnNextWord(Sequence temporarySequence,int maksBestWordToDraw,int powNr) {
        List<WordWeightPair> drawnWords=new ArrayList<>();
        if(temporarySequence.getSequenceWordCount()==0) temporarySequence.AddWord(getRandomIndex(wordSet.size()),0,0);
        for (IndeksValue indeksValue:coverLists[temporarySequence.getLastWord()]
             ) {
            if(temporarySequence.contains(indeksValue.indeks)){
                continue;
            }
            else{
                IndeksValue secondWord=getSecondWord(indeksValue,temporarySequence);
                WordWeightPair wordWeightPair=new WordWeightPair(secondWord.value+indeksValue.value,indeksValue.value,indeksValue.indeks);
                if(drawnWords.size()<maksBestWordToDraw)
                    drawnWords.add(wordWeightPair);
                else{
                    WordWeightPair weakest=getWeakest(drawnWords);
                    if(weakest.weight<wordWeightPair.weight){
                        drawnWords.remove(weakest);
                        drawnWords.add(wordWeightPair);
                        continue;
                    }
                    if(getWeakest(drawnWords).weight-indeksValue.value>=9) break;

                }
            }

        }
        //losowanie słowa z tablicy
        return drawnWord(drawnWords, powNr);
    }

    private WordWeightPair getWeakest(List<WordWeightPair> drawnWords) {
        WordWeightPair weakestWordWeightPair=drawnWords.get(0);
        for (WordWeightPair wordWeightPair:drawnWords
             ) {
            if(weakestWordWeightPair.weight>wordWeightPair.weight) {
                weakestWordWeightPair=wordWeightPair;
            }
        }
        return weakestWordWeightPair;
    }

    private IndeksValue getSecondWord(IndeksValue firstWord,Sequence temporarySequence) {
        for (IndeksValue indeksValue: coverLists[firstWord.indeks]
             ) {
            if(indeksValue.indeks==firstWord.indeks) continue;
            if(!temporarySequence.contains(indeksValue.indeks)) return indeksValue;
        }
        return new IndeksValue(-1,-1); //brak drugiego slowa
    }




    private WordWeightPair drawnWord(List<WordWeightPair> drawnWords, int powNr) {
        if(drawnWords.isEmpty())
            return new WordWeightPair(-1,-1,-1);  //nie wylosowano słowa żadnego

        for (int i =0; i <drawnWords.size()-1;i++){
            int sum=(int)(Math.pow(drawnWords.get(i).connectionStrength,powNr+1)+Math.pow(drawnWords.get(i+1).connectionStrength,powNr));
            int drawnNumber=getRandomIndex(sum) ;
            if( drawnNumber <= Math.pow(drawnWords.get(i).connectionStrength,powNr+1))
                return drawnWords.get(i);
        }
           // System.out.println("wybrano najgorszy " + drawnWords.get(drawnWords.size()-1).connectionStrength);
            return drawnWords.get(drawnWords.size()-1);
    }
    private int getRandomIndex(int bound) {
        Random  random=new Random();
        try {
            return random.nextInt(bound);
        }catch(Exception e){
            System.out.println("Word set Ex  " + bound + "  " + e);
            return -1;
        }
    }
    private class IndeksValue implements Comparable<IndeksValue> {
        private int indeks;
        private int value;

        IndeksValue(int indeks, int value) {
            this.indeks = indeks;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int getIndex(){
            return indeks;
        }

        @Override
        public int compareTo(IndeksValue other) {
            return (this.value - other.getValue());
        }

    }

}
