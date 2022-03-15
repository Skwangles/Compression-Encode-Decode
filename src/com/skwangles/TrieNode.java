package com.skwangles;

        public class TrieNode {
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

