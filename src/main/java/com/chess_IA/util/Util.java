package com.chess_IA.util;

public abstract class Util {

    // retorna um vetor com os valores intermediarios de um valor ate outro
    public static int[] createIntermediateValues(int start, int end){
        // calculando quanto itermediarios
        int intermediateLength = Math.abs(start - end) - 1;

        // se existir intermediarios
        if(intermediateLength > 0){
            // criando lista para inserir os valores
            int[] numbers = new int[Math.abs(intermediateLength)];
            // inserindo em ordem crescente
            if(start < end) {
                int num = start + 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num++;
                }
            }
            // inserindo em ordem decrescente
            else{
                int num = start - 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num--;
                }
            }
            // retornando valores
            return numbers;
        }
        // nao existe valores intermediarios
        return null;
    }
}
