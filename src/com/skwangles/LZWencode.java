package com.skwangles;

import java.util.Arrays;

public class LZWencode {
    //NEED TO PARSE HEX CODE FIRST
    private final MultiwayTrieNode root;
    private int phrases;
    //private final char[] applicableChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final char[] applicableChars = { 'A', 'B', 'C', 'D'};//To test with tony's numbers
    private String input;
    public LZWencode(){
        //PARSE HEX CODE FIRST - MODIFY INPUT TO BE THE USER's INPUT (Possibly use a stringbuilder as the user enters values.
        //input = "AB9D34C142FE2FEC142FE234C142FE2FB9D34C142";//---------Will be set to read in value------
        input = "AAABBABAABAAA";
        root = new MultiwayTrieNode('\0', -1);
        phrases = 1;//Sets the phrase number set to 0
        for (char c: applicableChars) {
            root.AddChild(c);//Creates Node, and assigns phraseNumber
        }
    }



    private void printPhraseNumber(int phraseNum){
        //Format the output as desired - or divert to packing
        System.out.println(phraseNum);
    }

    //
    //Multiway trie functions + LWZ Compression mixed in.
    //
    private int getNextPhraseNum(){//Handles the distribution of phrase numbers
        phrases += 1;
        return phrases - 1;//returns the value before incrementation
    }

    public void encodeString(){
        while(input.length() > 0) {
            int outValue = root.searchTrie(input);
            printPhraseNumber(outValue);
        }
    }

    //Trie Nodes
    class MultiwayTrieNode {
        public char c;
        public int phraseNum;
        public MultiwayTrieNode[] children;

        public MultiwayTrieNode(char c, int phraseNum){
            this.c = c;
            this.phraseNum = phraseNum;//Sets the phrase number
            children = new MultiwayTrieNode[applicableChars.length]; //0123456789ABCDEF - 16 chars max in each level - NEED TO CHECK IF A SPACE BETWEEN HEX is applicable
        }

        private void AddChild(char c){
            int index = 0;
            while(this.children[index] != null){//Loops until the next null array space is found
                index++;
            }
            this.children[index] = new MultiwayTrieNode(c, getNextPhraseNum());
        }

        //Can possibly take the input in as a single value, then progressively bit off it each time the nodes go through it.
        public int searchTrie(String pattern){
            if(pattern.length() > 0){//String must have at least 1 char
                if(children.length >0) {
                    for (MultiwayTrieNode child : children) {
                        if(child == null) break; //If one encounters a null item, the rest of the array will be Null
                        if (child.c == pattern.charAt(0)) {//Checks if the char is the same as the pattern
                            return child.searchTrie(pattern.substring(1));//Goes down a step, removing the used char from the string - recursive call
                        }
                    }
                }
                //If this is reached, no child matches the first char -- assuming it is not null/empty
                AddChild(pattern.charAt(0));
                input = pattern; //Update the current user input to be less the chars already parsed.
                return this.phraseNum;//The phrase value to be recorded is this one's

            }else{
                //This Node is the end of the line - can fetch the next char to see if it matches any of it's children
                input = pattern;//Update the current user input to be less the chars already parsed.
                return this.phraseNum;
            }
        }
    }
}



