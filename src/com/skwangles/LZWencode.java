package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Alexander developed this part
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWencode {
    public static char[] applicableChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static StringBuilder input;
    public static int phrases;
    public static void main(String[] args){
        //
        //Setup
        //
        input = new StringBuilder();
        phrases = 1;//Defines the starting phrase number - Tony said we can pick 0 or 1 as the start, we opted for 1.
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
                text = text.replaceAll("\\s","").toUpperCase();//Removes all whitespace
                if(text.equals("")) continue;//Do not add an empty string
                input.append(text);//Adds to input string, and ensures all hex chars are uppercase
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
    }

    public static int getPhraseNumber(){//Updates the phrase number
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

    public int navigateTrie(){//Uses the static value of the complete input - and slowly chips off the chars as it is parsed.
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