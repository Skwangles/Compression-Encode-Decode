package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313

import java.util.ArrayList;
import java.util.Scanner;

public class LZWpack {
    private static final int sizeOfByte = 8;
    private static int currentMaxBitsNum = 5;//initally must start with 5
    private static int countOfPhrases = 0;
    private static byte outByte = 0;
    private static int outByteused = 0;
    private static ArrayList<Byte> byteList;

    public static void main(String[] args){
        byteList = new ArrayList<>();
        readInInput();//Start the stream
    }

    private static void readInInput(){
        Scanner scanner = new Scanner(System.in);
        String text;
        try {
            while (scanner.hasNext()) {
                text = scanner.next();
                bytePrint(Integer.parseInt(text));//Stream value through - Adding 1 to use non-zero based indexes
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
        endOfFile();//Makes sure all remaining bits are printed
        scanner.close();
    }

    private static void bytePrint(int input){
        updateMaxBitsNum(); //Update currentMaxBits
        if((sizeOfByte - outByteused) < currentMaxBitsNum){//See if remainder of space can fit the input
            byte temp = (byte) (input << (2*sizeOfByte-currentMaxBitsNum-outByteused));//Creates the overflow value
            byte sizeAdjustInput = (byte) (input >> (currentMaxBitsNum - (sizeOfByte-outByteused)));//Shifts the bits to fit in the remaining space
            outByte = (byte) (outByte | sizeAdjustInput);
            printOut(outByte);
            outByte = temp;//
            outByteused = currentMaxBitsNum + outByteused - sizeOfByte;
        }
        else if((sizeOfByte-outByteused) == currentMaxBitsNum) {
    //int will fit perfectly - no wiggle room
            //Do stuff
            printOut(outByte);
            outByte = 0;//Cleans the byte to avoid carried bits
            outByteused = 0;//Finally, resets the amount of the byte used to 0
        }
        else{//The int will fit easily - but not finish the byte
            byte sizeAdjusted = (byte) (input << ((sizeOfByte-outByteused)-currentMaxBitsNum));
            outByte = (byte) (outByte | sizeAdjusted);//Aligns bits and ORs them
            outByteused += currentMaxBitsNum;//Increase the amount used
        }
    }

    private static void endOfFile(){//Ensure any partially filled bytes are printed
        if(outByteused > 0 && outByteused < sizeOfByte ){//Checks if a byte is partially formed - otherwise won't do anything (not checking the value of outbyte as it may have straight 0s that are important)
            outByte = (byte) (outByte & (0xFF << sizeOfByte-outByteused));//Creates a bitmask and cleans the byte to ensure no unexpected bits are in the remainder
            printOut(outByte);
        }//Only prints any remaining important bits, leaves the rest as 0
        for (byte b: byteList) {
            System.out.println(Integer.toBinaryString(b));
        }
        System.out.println(byteList.toString());
    }

    private static void updateMaxBitsNum(){//Updates and Returns the current Mac number of bits number should take up.
//        double value = Math.log(countOfPhrases + 17)/Math.log(2) + 1;
//        if(value % 1 == 0){//Checks if number is whole
//            currentMaxBitsNum = (int) value;//Cast whole number to Int
//        }
        countOfPhrases++;//Increments the amount of phrase numbers passed through
    }

    private static void printOut(byte outbyte){
        byteList.add(outbyte);
        //System.out.println(outbyte);
    }
}
