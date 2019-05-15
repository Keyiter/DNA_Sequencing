package com.company;

public class WordWeightPair {
    public Integer word;
    public Integer weight;
    public Integer connectionStrength;

    WordWeightPair(int _weight,int _strength, Integer _word){
        weight = _weight;
        connectionStrength = _strength;
        word = _word;
    }
}
