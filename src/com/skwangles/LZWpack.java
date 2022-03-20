package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Alexander developed this part
import java.util.Scanner;

public class LZWpack {
    private static final int outputBitCount = 8;//32bit Integers
    private static final int initalDictSize = 17;//Is 17, because we are operating with non-zerobased values (0 is escape)
    private static int bitsRequired;
    private static int countOfPhrases;
    private static int outByte;
    private static int bitsInUse;

    public static void main(String[] args){
        //Setup
        bitsInUse = 0;//The full 8bytes are unused
        outByte = 0;//Has no value
        countOfPhrases = 0;
        bitsRequired = 5;//initally must start with 5

        //
        //Read in - starting the program
        //
        Scanner scanner = new Scanner(System.in);
        String text;
        try {
            while (scanner.hasNext()) {
                text = scanner.next();
                packToBinary(Integer.parseInt(text)+1);//Stream value through - Adding 1 to use non-zero based indexes (Reserves 0 as escape char)
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
        scanner.close();

        //
        //Will add the last byte only if is not 0 - the ending of a byte that may be only 0s will not be printed, but both negative and positive integers will be printed
        //
        if(bitsInUse != 0){
            printOut(outByte);
        }
        System.out.flush();//Prints the bytes in the buffer to the output
    }

    private static void packToBinary(int input){
        updateBitsRequired(); //Update currentMaxBits
        if((outputBitCount - bitsInUse) < bitsRequired){//See if remainder the of space can fit the input
            int temp =  (input << (2* outputBitCount - bitsRequired - bitsInUse));//Creates the overflow value
            int shiftedInput = (input >> (bitsRequired - (outputBitCount - bitsInUse)));//Shifts the bits to fit in the remaining space
            outByte =  (outByte | shiftedInput);
            printOut(outByte);
            outByte = temp;
            bitsInUse = bitsRequired + bitsInUse - outputBitCount;
        }
        else if((outputBitCount - bitsInUse) == bitsRequired) {
            //int will fit perfectly - no wiggle room
            printOut(outByte);
            outByte = 0;//Cleans the byte to avoid carried bits
            bitsInUse = 0;//Finally, resets the amount of the byte used to 0
        }
        else{//The int will fit easily - but not finish the byte
            int shiftedInput = (input << ((outputBitCount - bitsInUse)- bitsRequired));//Aligns bits with the next unused space
            outByte = outByte | shiftedInput;//ORs the bits together
            bitsInUse += bitsRequired;//Increase the amount used
        }
    }

    private static void updateBitsRequired(){//Updates and Returns the current Mac number of bits number should take up.
        bitsRequired = (int) Math.ceil(Math.log(countOfPhrases + initalDictSize)/Math.log(2));//Gets the no of bits required
        countOfPhrases++;//Increments the amount of phrase numbers passed through

    }

    private static void printOut(int outByte){
        System.out.write(outByte);

    }
}
