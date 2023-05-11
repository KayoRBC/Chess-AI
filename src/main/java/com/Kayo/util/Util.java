package com.Kayo.util;

public abstract class Util {
    public static int[] createIntermediateValues(int start, int end){
        int intermediateLength = Math.abs(start - end) - 1;
        if(intermediateLength > 0){
            int[] numbers = new int[Math.abs(intermediateLength)];
            if(start < end) {
                int num = start + 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num++;
                }
            }
            else{
                int num = start - 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num--;
                }
            }
            return numbers;
        }
        return null;
    }
}
