package com.company;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordSet {
    public int [][] coverTable;
    public List<String> wordSet;
    public HashMap<Integer,String> wordMap;
    WordSet(String _fileName){
        wordSet = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(_fileName);
            String line = null;
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            Integer count=1;
            while((line = bufferedReader.readLine()) != null) {
                wordSet.add(line);
                wordMap.put(count,line);
                count++;
            }
            bufferedReader.close();
            prepareCoverTable();
        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file " + _fileName);
        } catch (IOException ex){
            System.out.println("Error reading file " + _fileName);
        }
    }

    private void prepareCoverTable(){
        for (Map.Entry<Integer,String> pair:wordMap.entrySet()) {
            String word=pair.getValue();
            Integer indeks=pair.getKey();
            for (Map.Entry<Integer,String> secondPair:wordMap.entrySet()){
                String secondWord=secondPair.getValue();
                Integer secondIndeks=secondPair.getKey();
                if(secondIndeks!=indeks)
                    coverTable[indeks][secondIndeks]=getCoverNumber(word,secondWord);
            }
        }
    }
    private int getCoverNumber(String word,String word2){
        int length=word.length();
        for(int i=1;i<length;i++){
            if(word2.substring(0,length-1-i).equals(word.substring(i,length-1))){
                return length-i;
            }
        }
        return 0;
    }
    public WordWeightPair getConnectionInfo(String word, int i){
        int conTemp = 0;
        int temp = 0;

        for(int j=1;j<10;j++){
            if(wordSet.get(i).substring(0, 9 - j).equals(word.substring(j, 9))){
                conTemp = 10-j;
                break;
            }
        }
        int max =0;
        for(int k=0;k < wordSet.size();k++){
            temp = 0;
            if(i != k)
                for(int j=1;j<10;j++) {
                    if (wordSet.get(i).substring(j, 9).equals(wordSet.get(k).substring(0, 9-j))) {
                        temp += (10 - j);
                        break;
                    }
                }
            if(temp > max){
                max = temp;
            }
        }

        WordWeightPair retVal;
        if(conTemp >0)
            retVal = new WordWeightPair(max+conTemp,conTemp,wordSet.get(i));
        else
            retVal = new WordWeightPair(-1,-1,"");

        return retVal;
    }

    public WordWeightPair FindBestMatchingWord(String word, float probability, List<Integer> notUniqueTabu){
        List<Integer> tabuList = new ArrayList<>();
        float rand =0;
        boolean found;
        int max =0;
        int conMax =0;
        int index =0;

        do {

            if(max > 0)
                tabuList.add(index);

            max = 0;
            index = 0;
            found = false;
            for(int i =0; i <wordSet.size();i++){
                if(tabuList.contains(i) || notUniqueTabu.contains(i))
                    continue;

                int temp=0;
                int conTemp =0;

                for(int j=1;j<10;j++){
                    if(wordSet.get(i).substring(0, 9 - j).equals(word.substring(j, 9))){
                        temp = 10-j;
                        conTemp = 10-j;
                        found = true;
                        break;
                    }

                }

               if(max - 9 > temp)
                    continue;
                for(int k=0;k < wordSet.size();k++){
                    temp = conTemp;
                    if(i != k)
                        for(int j=1;j<10;j++) {
                            if (wordSet.get(i).substring(j, 9).equals(wordSet.get(k).substring(0, 9-j))) {
                                temp += (10 - j);
                                break;
                            }
                        }
                    if(temp > max){
                        max = temp;
                        conMax = conTemp;
                        index = i;
                    }
                   if(max >= 18)
                       return new WordWeightPair(18,9, wordSet.get(i));
                }



            }

            if(probability<1){
                Random random = new Random();
                rand = random.nextFloat();
            }

        } while(probability <1 && rand >= probability);

        WordWeightPair retVal;
        if(found)
            retVal = new WordWeightPair(max,conMax, wordSet.get(index));
        else if(!tabuList.isEmpty())
            retVal = new WordWeightPair(max/3,conMax, wordSet.get(tabuList.get(0)));
        else
             retVal = new WordWeightPair(-1,-1,"");

        return  retVal;

    }

}
