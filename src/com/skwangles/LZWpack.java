package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313

import java.util.ArrayList;
import java.util.Scanner;

public class LZWpack {
    private static final int sizeOfByte = 32;
    private static final int initalDictSize = 17;//Is 17, because we are operating with non-zerobased values (0 is escape)
    private static int currentMaxBitsNum;
    private static int countOfPhrases;
    private static int outByte;
    private static int outByteused;
    private static ArrayList<Integer> byteList;//Contains all bytes (converted to ints) output by the program

    public static void main(String[] args){
        byteList = new ArrayList<>();
//        countOfBytesToInt = 0;
//        bytesToInt = new byte[4];//4 bytes will go to 1 int
        outByteused = 0;//The full 8bytes are unused
        outByte = 0;//Has no value
        countOfPhrases = 0;
        currentMaxBitsNum = 5;//initally must start with 5
        readInInput();//Start the stream
    }

    private static void readInInput(){
        Scanner scanner = new Scanner(System.in);
        String text;
        try {
            while (scanner.hasNext()) {
                text = scanner.next();
                bytePrint(Integer.parseInt(text));//Stream value through - Adding 1 to use non-zero based indexes (Reserves 0 as escape char)
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
        endOfFile();//Makes sure all remaining bits are printed
        scanner.close();

        //
        //Prints the values stored in the ArrayList
        //
        for (int b: byteList) {
            System.out.println(Integer.toBinaryString(b));
        }
        System.out.println(byteList.toString());
    }

    private static void bytePrint(int input){
        updateMaxBitsNum(); //Update currentMaxBits
        if((sizeOfByte - outByteused) < currentMaxBitsNum){//See if remainder of space can fit the input
            int temp =  (input << (2*sizeOfByte-currentMaxBitsNum-outByteused));//Creates the overflow value
            int sizeAdjustInput = (input >> (currentMaxBitsNum - (sizeOfByte-outByteused)));//Shifts the bits to fit in the remaining space
            outByte =  (outByte | sizeAdjustInput);
            addToOut(outByte);
            outByte = temp;//
            outByteused = currentMaxBitsNum + outByteused - sizeOfByte;
        }
        else if((sizeOfByte-outByteused) == currentMaxBitsNum) {
            //int will fit perfectly - no wiggle room
            addToOut(outByte);
            outByte = 0;//Cleans the byte to avoid carried bits
            outByteused = 0;//Finally, resets the amount of the byte used to 0
        }
        else{//The int will fit easily - but not finish the byte
            int sizeAdjusted = (input << ((sizeOfByte-outByteused)-currentMaxBitsNum));
            outByte = (outByte | sizeAdjusted);//Aligns bits and ORs them
            outByteused += currentMaxBitsNum;//Increase the amount used
        }
    }

    private static void endOfFile(){//Ensure any partially filled bytes are printed
        if(outByteused > 0 && outByteused < sizeOfByte ){//Checks if a byte is partially formed - otherwise won't do anything (not checking the value of outbyte as it may have straight 0s that are important)
            outByte = outByte & (0xFF << sizeOfByte-outByteused);//Creates a bitmask and cleans the byte to ensure no unexpected bits are in the remainder
            addToOut(outByte);
        }
    }

    private static void updateMaxBitsNum(){//Updates and Returns the current Mac number of bits number should take up.
        currentMaxBitsNum = (int) Math.ceil(Math.log(countOfPhrases + initalDictSize)/Math.log(2));//Gets the no of bits required
        countOfPhrases++;//Increments the amount of phrase numbers passed through
    }

    private static void addToOut(int outbyte){
        byteList.add(outbyte);
    }
}
