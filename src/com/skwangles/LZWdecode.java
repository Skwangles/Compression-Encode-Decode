package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LZWdecode {

    static class Dictionary {
        // For testing with simpler alphabet
        // List<Integer> phraseNumbers = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        // List<Character> followedBy = new ArrayList<>(Arrays.asList('\0', 'a', 'b',
        // 'c', 'd'));

        List<Integer> phraseNumbers = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        List<Character> followedBy = new ArrayList<>(
                Arrays.asList('\0', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'));

        public char getFirstChar(int phraseNumber) {
            if (phraseNumbers.get(phraseNumber) == 0) {
                return followedBy.get(phraseNumber);
            } else {
                return getFirstChar(phraseNumbers.get(phraseNumber));
            }
        }

        public void printPhrase(int phraseNumber) {
            if (phraseNumber > phraseNumbers.size()) {
                System.err.print("Invalid input");
                return;
            } else if (phraseNumbers.get(phraseNumber) == 0) {
                System.out.print(followedBy.get(phraseNumber));
            } else {
                // First print the nested phrase
                printPhrase(phraseNumbers.get(phraseNumber));

                // Then print this phrase's followedBy character
                System.out.print(followedBy.get(phraseNumber));
            }
        }

        public void add(int phraseNumber) {
            phraseNumbers.add(phraseNumber);
            followedBy.add('\0');
        }

        public void set(int phraseNumber, char c) {
            followedBy.set(phraseNumber, c);
        }

        public String toString() {
            return phraseNumbers + "\n" + followedBy;
        }

        public int getLastPhraseNumber() {
            return phraseNumbers.size() - 1;
        }
    }

    public static void main(String[] args) {
        Dictionary dict = new Dictionary();

        Scanner scanner = new Scanner(System.in);

        boolean first = true;

        while (scanner.hasNext()) {
            int phraseNumber = Integer.parseInt(scanner.nextLine()) + 1;

            if (first) {
                first = false;
            } else {
                char firstCharacter = dict.getFirstChar(phraseNumber);
                // Set the followedBy character to the first character of this phrase
                dict.set(dict.getLastPhraseNumber(), firstCharacter);
            }

            dict.printPhrase(phraseNumber);

            // Create new empty phrase
            dict.add(phraseNumber);
        }

        scanner.close();
    }
}
