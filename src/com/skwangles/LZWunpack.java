package com.skwangles;
//Alexander Stokes - 1578409, Liam Labuschagne - 1575313
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZWunpack {
    public static void main(String[] args){

    }

    private static void readInInput(){
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
}
