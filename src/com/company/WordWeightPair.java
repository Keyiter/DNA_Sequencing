package com.company;

public class WordWeightPair {
    public String word;
    public Integer weight;
    public Integer connectionStrength;

    WordWeightPair(int _weight,int _strength, String _word){
        weight = _weight;
        connectionStrength = _strength;
        word = _word;
    }
}
