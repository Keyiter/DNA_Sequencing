package com.company;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordSet {

    public List<String> wordSet;

    WordSet(String _fileName){
        wordSet = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(_fileName);
            String line = null;
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                wordSet.add(line);
            }
            bufferedReader.close();

        } catch(FileNotFoundException ex){
            System.out.println("Unable to open file " + _fileName);
        } catch (IOException ex){
            System.out.println("Error reading file " + _fileName);
        }
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
            retVal = new WordWeightPair(max,conMax, wordSet.get(tabuList.get(0)));
        else
             retVal = new WordWeightPair(-1,-1,"");

        return  retVal;

    }

}
