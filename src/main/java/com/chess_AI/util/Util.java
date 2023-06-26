package com.chess_AI.util;

/**
 * Esta classe armazena as funcoes que nao estao associadas a nenhuma camada e podem ser utilizadas em qualquer camada do projeto
 */
public abstract class Util {

    /**
     * Retorna um vetor com os valores intermediarios de um valor ate outro.
     *
     * @param start Valor de inicio
     * @param end Valor de fim
     * @return Um vetor com os valores intermediarios, se nao existir retorna null
     */
    public static int[] createIntermediateValues(int start, int end){
        // calcula quantos itermediarios existem
        int intermediateLength = Math.abs(start - end) - 1;

        // se existir intermediarios
        if(intermediateLength > 0){
            // cria lista para inserir os valores
            int[] numbers = new int[Math.abs(intermediateLength)];
            // insere em ordem crescente
            if(start < end) {
                int num = start + 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num++;
                }
            }
            // insere em ordem decrescente
            else{
                int num = start - 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num--;
                }
            }
            // retorna valores
            return numbers;
        }
        // nao existe valores intermediarios
        return null;
    }
}
