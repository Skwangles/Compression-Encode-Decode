package com.skwangles;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWencode {
    public static char[] applicableChars = { 'A', 'B', 'C', 'D'};//To test with tony's numbers
    //public static char[] applicableChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};//NEED TO CHECK IF A SPACE BETWEEN HEX is applicable
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
            int outValue = root.navigateTrie();
            System.out.println(outValue);
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
