package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sequence{
    List<Integer> words;
    List<Integer> weight;
    List<Integer> connections;
    int uniqueWords;


    Sequence(){
        words = new ArrayList<Integer>();
        weight = new ArrayList<Integer>();
        connections = new ArrayList<Integer>();
        uniqueWords = 0;
    }

    public void AddWord(Integer _word,int _weight, int _strength){
        if(!words.contains(_word))
            uniqueWords++;
        words.add(_word);
        weight.add(_weight);
        connections.add(_strength);
    }

    public void DropFromPlace(String _word){
        int index = words.indexOf(_word);
        words = words.subList(0,index);
        weight = weight.subList(0,index);
        connections = connections.subList(0,index);
        CalculateUnique();
    }

    public void DropFromPlace(int _index){
        words = words.subList(0,_index);
        weight = weight.subList(0,_index);
        connections = connections.subList(0,_index);
        CalculateUnique();
    }

    public void DropFromPlace(String _word,boolean toLeft){
        if(toLeft) {
            int index = words.indexOf(_word);
            words = words.subList(index, words.size());
            weight = weight.subList(index, weight.size());
            connections = connections.subList(index, connections.size());
        } else {
            int index = words.indexOf(_word);
            words = words.subList(0,index);
            weight = weight.subList(0,index);
            connections = connections.subList(0,index);
        }
        CalculateUnique();
    }

    public void DropFromPlace(int _index,boolean toLeft){
        if(toLeft) {
            words = words.subList(_index, words.size());
            weight = weight.subList(_index, weight.size());
            connections = connections.subList(_index, connections.size());
        } else {
            words = words.subList(0,_index);
            weight = weight.subList(0,_index);
            connections = connections.subList(0,_index);
        }
        CalculateUnique();
    }

    public Integer getWord(int i){return  words.get(i);}
    public Integer getLastWord() {return  words.get(words.size()-1);}
    public Integer getWeigt(int i){return weight.get(i);}

    public Integer getSequenceSize(){return words.size();}

    public boolean contains(Integer word){
        return words.contains(word);

    }

    public int getUniqueWords(){return uniqueWords;}

    public int getNumberOfWords(){
        int length = 0;
        for (int num: connections) {
            length += 10 - num ;
        }
        return length;
    }

    protected void CalculateUnique(){
        uniqueWords = words.size();
        Set<Integer> appeared = new HashSet<>();
        for (Integer item : words) {
            if (!appeared.add(item)) {
                uniqueWords--;
            }
        }

    }
}
