package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Alexander developed this part


public class LZWencode {
    public static char[] applicableChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static char input;//Holds the very first char, and holds the char between navigate.Trie calls.
    public static int phrases;
    public static void main(String[] args){
        //
        //Setup
        //
        phrases = 0;//Defines the starting phrase number
        TrieNode root = new TrieNode('\0', -1);//Root has no applicable phrase number
        for (char c: applicableChars) {
            root.AddChild(c, getPhraseNumber());//Creates Node, and assigns phraseNumber
        }
        input = getNextChar();//Gets the first char
        //
        //Encoding
        //
        while(input != '\0') {
            int[] outValue = root.navigateTrie(input);//Gets the longest path

            System.out.println(outValue[0]);//----Prints out to Standard Out----
            input = (char) outValue[1];
        }
    }

    public static char getNextChar(){//Gets a single char - if end of stream, return escape char '\0'
        int text;
        try {
            while ((text = System.in.read()) != -1  || input != '\0')  {
                if((char) text == '\n') continue;//Skip spaces

                if(text == -1 || text < (int)applicableChars[0] || text > (int)applicableChars[applicableChars.length-1]) return '\0';//Returns escape char if outside the ascii bounds of the acceptable Chars
                else return Character.toUpperCase((char) text);
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
        return '\0';
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


    public int[] navigateTrie(char next){//Uses the static value of the complete input - and slowly chips off the chars as it is parsed.
        if(next != '\0'){//Must be an applicable char, else end is found
            for (TrieNode child : children) {
                if(child == null) break; //If null item, the rest of the array will be Null
                if (child.character == next) {
                    return child.navigateTrie(LZWencode.getNextChar());//Goes down a step, removing the used char from the string - recursive call
                }
            }
            AddChild(next, LZWencode.getPhraseNumber());//If this is reached, no child matches the first char -- assuming it is not null/empty - so should be added the Trie
            return new int[] {this.phraseNum, (int) next};//Passes back the next char - without accessing the variable directly
        }
        return new int[] {this.phraseNum, 0};//return phrase + escape char
    }
}