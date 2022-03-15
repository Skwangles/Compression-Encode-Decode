package com.skwangles;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWencode {
    //public static char[] applicableChars = { 'A', 'B', 'C', 'D'};//To test with tony's numbers
    public static char[] applicableChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};//NEED TO CHECK IF A SPACE BETWEEN HEX is applicable
    public static StringBuilder input;
    public static int phrases;
    public static void main(String[] args){//Static main function allow LZWencode to be a standalone file

        //
        //Setup
        //
        input = new StringBuilder();
        phrases = 0;
        TrieNode root = new TrieNode('\0', -1);//Root has no applicable phrase number
        for (char c: applicableChars) {
            root.AddChild(c, getPhraseNumber());//Creates Node, and assigns phraseNumber
        }
        readInInput();//Parses the HEX
        //
        //Encoding
        //
        while(input.length() > 0) {
            int outValue = root.navigateTrie();//Gets the longest path
            System.out.println(outValue);//----Prints out to Standard Out----
        }
    }


    private static void readInInput(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            while ((text = br.readLine()) != null) {
                input.append(text.toUpperCase());//Adds to input string, and ensures all hex chars are uppercase
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
    }

    public static int getPhraseNumber(){
        phrases++;
        return phrases -1;
    }
}


class TrieNode {
    public char character;
    public int phraseNum;
    public TrieNode[] children;

    public TrieNode(char character, int phraseNum){
        this.character = character;
        this.phraseNum = phraseNum;//Sets the phrase number
        children = new TrieNode[LZWencode.applicableChars.length];//Flexible Trie structure - changes based on accepted values
    }

    public void AddChild(char c, int phraseNum){
        int index = 0;
        while(this.children[index] != null){//Loops until the next null array space is found
            index++;
        }
        this.children[index] = new TrieNode(c, phraseNum);
    }

    public int navigateTrie(){//Uses the static value of input - and slowly chips off the chars as it is parsed.
        if(LZWencode.input.length() > 0){//String must have at least 1 char - otherwise end of input is found
            for (TrieNode child : children) {
                if(child == null) break; //If null item, the rest of the array will be Null
                if (child.character == LZWencode.input.charAt(0)) {
                    LZWencode.input.deleteCharAt(0);//Remove the parsed char
                    return child.navigateTrie();//Goes down a step, removing the used char from the string - recursive call
                }
            }
            //If this is reached, no child matches the first char -- assuming it is not null/empty - so should be added the Trie
            AddChild(LZWencode.input.charAt(0), LZWencode.getPhraseNumber());
        }
        return this.phraseNum;
    }
}