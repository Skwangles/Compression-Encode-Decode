package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWpack {
    private static final int sizeOfByte = 8;
    private static int currentMaxBitsNum = 5;
    private static int countOfPhrases = 0;
    private static byte outByte = 0;
    private static int outByteused = 0;

    public static void main(String[] args){
        readInInput();//Start the stream
    }

    private static void readInInput(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            while ((text = br.readLine()) != null) {
                bytePrint(Integer.parseInt(text)+1);//Stream value through - Adding 1 to use non-zero based indexes
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
        endOfFile();//Makes sure all remaining bits are printed

    }

    private static void bytePrint(int input){
        updateMaxBitsNum(); //Update currentMaxBits
        if((sizeOfByte - outByteused) < currentMaxBitsNum){//See if remainder of space can fit the input
            byte temp = (byte) (input << (sizeOfByte-(currentMaxBitsNum % (sizeOfByte-outByteused))));//Creates the overflow value
            byte sizeAdjustInput = (byte) (input >> (currentMaxBitsNum % (sizeOfByte-outByteused)));//Shifts the bits to fit in the remaining space
            outByte = (byte) (outByte | sizeAdjustInput);
            System.out.write(outByte);
            outByte = temp;//
            outByteused = (currentMaxBitsNum % (sizeOfByte-outByteused));
        }
        else if((sizeOfByte-outByteused) == currentMaxBitsNum) {
    //int will fit perfectly - no wiggle room
            //Do stuff
            System.out.write(outByte);
            outByte = 0;//Cleans the byte to avoid carried bits
            outByteused = 0;//Finally, resets the amount of the byte used to 0
        }
        else{//The int will fit easily - but not finish the byte
            outByte = (byte) (outByte |(input << ((sizeOfByte-outByteused)-currentMaxBitsNum)));//Aligns bits and ORs them
            outByteused += currentMaxBitsNum;//Increase the amount used
        }
    }

    private static void endOfFile(){//Ensure any partially filled bytes are printed
        if(outByte > 0){//Checks if a byte is partially formed - otherwise won't do anything
            outByte = (byte) (outByte & (0xFF << sizeOfByte-outByteused));//Creates a bitmask and cleans the byte to ensure no unexpected bits are in the remainder
            System.out.write(outByte);
        }//Only prints any remaining important bits, leaves the rest as 0
    }

    private static void updateMaxBitsNum(){//Updates and Returns the current Mac number of bits number should take up.
        double value = Math.log(countOfPhrases + 16)/Math.log(2) + 1;
        if(value % 1 == 0){//Checks if number is whole
            currentMaxBitsNum = (int) value;//Cast whole number to Int
        }
        countOfPhrases++;//Increments the amount of phrase numbers passed through
    }
}
