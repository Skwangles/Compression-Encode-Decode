package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
                bytePrint(Integer.parseInt(text));//Stream value through
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
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
        }
        else{//The int will fit easily
    //do stuff
        }
    }

    private static void updateMaxBitsNum(){//Updates and Returns the current Mac number of bits number should take up.
        double value = Math.log(countOfPhrases + 16)/Math.log(2) + 1;
        if(value % 1 == 0){//Checks if number is whole
            currentMaxBitsNum = (int) value;//Cast whole number to Int
        }
        countOfPhrases++;//Increments the amount of phrase numbers passed through
    }
}
