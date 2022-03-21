package com.skwangles;

import java.io.IOException;
import java.util.Scanner;

//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Liam developed this part

public class LZWunpack {
    // Is 17, because we are operating with non-zerobased values (0 escape)
    private static final int initalDictSize = 17;
    private static int bitsRequired;// Holds the expected size of the next phrase number
    private static int countOfPhrases;// Holds the count of values that have been parsed
    private static int bitsLeftOver; // How many bits are left over from the previous byte

    public static void main(String[] args) {
        // Initalising values
        bitsRequired = 5;
        countOfPhrases = 0;
        bitsLeftOver = 0;

        StringBuilder stream = new StringBuilder();

        int input;
        try {
            while ((input = System.in.read()) != -1) {
                stream.append(printBin(input));
            }
        } catch (IOException e) {

        }

        for (int i = 0; (i + bitsRequired) < stream.length(); i += bitsRequired) {
            updateBitsRequired();

            String nextPhraseString = stream.substring(i, i + bitsRequired);

            int nextPhrase = Integer.parseInt(nextPhraseString, 2);
            if (nextPhrase != 0) {
                System.out.println(nextPhrase - 1);
            }
        }

        System.out.flush();
    }

    // Updates and Returns the current number of bits a phrase number should take
    // up.
    private static void updateBitsRequired() {
        // Gets the no of bits required
        bitsRequired = (int) Math.ceil(Math.log(countOfPhrases + initalDictSize) / Math.log(2));
        countOfPhrases++;// Increments the amount of phrase numbers passed through
    }

    private static String printBin(int bin) {
        return String.format("%8s", Integer.toBinaryString(bin)).replace(' ', '0');
    }

    private static byte getFirstBits(int n, byte b) {
        b >>= 8 - n;
        return b;
    }
}
