package com.chess_AI.util;

/**
 * Esta interface armazena as funcoes que podem ser utilizadas em qualquer camada do projeto.
 */
public interface Functions {

    /**
     * Retorna um vetor com os valores intermediarios de um valor ate outro.
     *
     * @param start Valor de inicio (excluido).
     * @param end Valor de fim (excluido).
     * @return Um vetor com os valores intermediarios, se nao existir retorna null.
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
