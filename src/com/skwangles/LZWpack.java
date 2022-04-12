package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Alexander developed this part
import java.util.Scanner;

public class LZWpack {
    private static final int sizeOfAByte = 8;//32bit Integers
    private static final int initalDictSize = 17;//Is 17, because we are operating with non-zerobased values (0 is escape)
    private static int bitsNeeded;
    private static int countOfPhrases;
    private static byte outByte;
    private static int bitsInUse;


    public static void main(String[] args){

        //Setup
        bitsInUse = 0;//The full 8bytes are unused
        outByte = 0;//Has no value
        countOfPhrases = 0;
        bitsNeeded = 5;//initally must start with 5
        int loop = 0;
        //
        //Read in - starting the program
        //
        Scanner scanner = new Scanner(System.in);
        String text;
        try {
            while (scanner.hasNext()) {
                text = scanner.next();
                if(loop > 246){
                    int a = 1;
                }
                loop++;
                packToBinary((Integer.parseInt(text)+1));//Stream value through - Uses non-zero based indexes (Reserves 0 as escape char)
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

        if((sizeOfAByte - bitsInUse) < bitsNeeded){//See if remainder the of space can fit the input
            byte temp = (byte)(input << (2* sizeOfAByte - bitsNeeded - bitsInUse));//Creates the overflow value
            byte shiftedInput =  (byte)(input >> (bitsNeeded - (sizeOfAByte - bitsInUse)));//Shifts the bits to fit in the remaining space
            shiftedInput = (byte)(shiftedInput & (int)(Math.pow(2, (sizeOfAByte - bitsInUse + 1))-1));//Make sure it is all 0s outside the important bits
            outByte = (byte)(outByte | shiftedInput);
            printOut(outByte);
            outByte = temp;
            bitsInUse = (bitsNeeded + bitsInUse) % sizeOfAByte;
        }
        else if((sizeOfAByte - bitsInUse) == bitsNeeded) {
            //int will fit perfectly - no wiggle room
            outByte = (byte) (outByte | input);
            printOut(outByte);
            outByte = 0;//Cleans the byte to avoid carried bits
            bitsInUse = 0;//Finally, resets the amount of the byte used to 0
        }
        else{//The int will fit easily - but not finish the byte
            int shiftedInput = (input << ((sizeOfAByte - bitsInUse)- bitsNeeded));//Aligns bits with the next unused space
            outByte = (byte)(outByte | shiftedInput);//ORs the bits together
            bitsInUse += bitsNeeded;//Increase the amount used
            bitsInUse = bitsInUse % sizeOfAByte;
        }
    }

    private static void updateBitsRequired(){//Updates and Returns the current Mac number of bits number should take up.
        bitsNeeded = (int) Math.ceil(Math.log(countOfPhrases + initalDictSize)/Math.log(2));//Gets the no of bits required
        countOfPhrases++;//Increments the amount of phrase numbers passed through

    }

    private static void printOut(byte outByte){
//        String output = printBin(outByte);
//        System.out.println(output.substring(output.length()-8));
        System.out.write(outByte);
    }

    private static String printBin(int bin) {
        return String.format("%8s", Integer.toBinaryString(bin)).replace(' ', '0');
    }
}
