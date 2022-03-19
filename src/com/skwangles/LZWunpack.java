package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
//Liam developed this part
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWunpack {
    private static final int initalDictSize = 17;//Is 17, because we are operating with non-zerobased values (0 is escape)
    private static int bitsRequired;//Holds the expected size of the next phrase number
    private static int countOfPhrases;//Holds the count of values that have been parsed

    public static void main(String[] args){
        //Initalising values
        bitsRequired = 5;
        countOfPhrases = 0;

        //
        //Read In
        //
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            while ((text = br.readLine()) != null) {
                //Dosomething
            }
        }
        catch (Exception e){
            System.out.println("Exception Thrown: " + e);
        }
    }


    private static void updateBitsRequired(){//Updates and Returns the current number of bits a phrase number should take up.
        bitsRequired = (int) Math.ceil(Math.log(countOfPhrases + initalDictSize)/Math.log(2));//Gets the no of bits required
        countOfPhrases++;//Increments the amount of phrase numbers passed through
    }
}
