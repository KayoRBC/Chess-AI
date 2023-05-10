package com.Kayo.controller;

import com.Kayo.model.Board;
import com.Kayo.view.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    @Test
    void movePawnFront() {
        BoardController controller = new BoardController();
        boolean front0 = controller.move(1, 0, 1, 0);
        Assertions.assertTrue(controller.getTypeOf(1, 0) == PieceType.PAWN);
        Assertions.assertFalse(front0);


        // movendo 1 para frente
        boolean front1 = controller.move(1, 0, 2, 0);
        Assertions.assertTrue(controller.getTypeOf(1, 0) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(2, 0) == PieceType.PAWN);
        Assertions.assertTrue(front1);

        // movendo 2 para frente
        boolean front2 = controller.move(1, 7, 3, 7);
        Assertions.assertTrue(controller.getTypeOf(1, 7) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(3, 7) == PieceType.PAWN);
        Assertions.assertTrue(front2);

        // movendo errado para frente
        boolean front5 = controller.move(1, 1, 5, 1);
        Assertions.assertTrue(controller.getTypeOf(1, 1) == PieceType.PAWN);
        Assertions.assertTrue(controller.getTypeOf(5, 1) == PieceType.NULL);
        Assertions.assertFalse(front5);

        // preparando ambiente de teste
        controller.move(1, 6, 3, 6);
        controller.move(3, 6, 4, 6);
        controller.move(4, 6, 5, 6);

        // movendo errado um para frente
        boolean front11 = controller.move(6, 6, 5, 6);
        Assertions.assertTrue(controller.getTypeOf(6, 6) == PieceType.PAWN);
        Assertions.assertTrue(controller.getTypeOf(5, 6) == PieceType.PAWN);
        Assertions.assertFalse(front11);

        // movendo errado dois para frente
        boolean front12 = controller.move(6, 6, 4, 6);
        Assertions.assertTrue(controller.getTypeOf(6, 6) == PieceType.PAWN);
        Assertions.assertTrue(controller.getTypeOf(4, 6) == PieceType.NULL);
        Assertions.assertFalse(front12);
    }

    @Test
    void movePawnDiagonal(){
        BoardController controller = new BoardController();

        // movimento errado para a diagonal
        boolean diagonal1 = controller.move(1, 0, 2, 1);
        Assertions.assertTrue(controller.getTypeOf(1, 0) == PieceType.PAWN);
        Assertions.assertTrue(controller.getTypeOf(2, 1) == PieceType.NULL);
        Assertions.assertFalse(diagonal1);

        // preparando ambiente de teste
        controller.move(1, 6, 3, 6);
        controller.move(3, 6, 4, 6);
        controller.move(4, 6, 5, 6);

        // movimento certo diagonal
        boolean diagonal2 = controller.move(5,6, 6, 7);
        Assertions.assertTrue(controller.getTypeOf(5, 6) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(6, 7) == PieceType.PAWN);
        Assertions.assertTrue(diagonal2);

    }

    @Test
    void moveBishop(){
        BoardController controller = new BoardController();

        // movimento errado
        boolean diagonal0 = controller.move(7, 2, 7, 2);
        Assertions.assertTrue(controller.getTypeOf(7, 2) == PieceType.BISHOP);
        Assertions.assertFalse(diagonal0);

        // movimento errado
        boolean diagonal1 = controller.move(7, 2, 2, 7);
        Assertions.assertTrue(controller.getTypeOf(7, 2) == PieceType.BISHOP);
        Assertions.assertTrue(controller.getTypeOf(2, 7) == PieceType.NULL);
        Assertions.assertFalse(diagonal1);

        // preparando ambiente
        controller.move(6, 3, 5, 3);

        // movimento certo
        boolean diagonal2 = controller.move(7, 2, 2, 7);
        Assertions.assertTrue(controller.getTypeOf(7, 2) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(2, 7) == PieceType.BISHOP);
        Assertions.assertTrue(diagonal2);

        // movimento certo
        boolean diagonal3 = controller.move(2, 7, 1, 6);
        Assertions.assertTrue(controller.getTypeOf(2, 7) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(1, 6) == PieceType.BISHOP);
        Assertions.assertTrue(diagonal3);
    }

    @Test
    void moveRook(){
        BoardController controller = new BoardController();

        // movimento errado
        boolean front0 = controller.move(7, 0, 7, 0);
        Assertions.assertTrue(controller.getTypeOf(7, 0) == PieceType.ROOK);
        Assertions.assertFalse(front0);

        // preparando ambiente
        controller.move(6, 0, 4, 0);

        // movimento errado
        boolean front1 = controller.move(7, 0, 3, 0);
        Assertions.assertTrue(controller.getTypeOf(7, 0) == PieceType.ROOK);
        Assertions.assertTrue(controller.getTypeOf(3, 0) == PieceType.NULL);
        Assertions.assertFalse(front1);

        // movimento certo
        boolean front2 = controller.move(7, 0, 5, 0);
        Assertions.assertTrue(controller.getTypeOf(7, 0) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(5, 0) == PieceType.ROOK);
        Assertions.assertTrue(front2);

        // movimento certo
        boolean front3 = controller.move(5, 0, 5, 7);
        Assertions.assertTrue(controller.getTypeOf(5, 0) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(5, 7) == PieceType.ROOK);
        Assertions.assertTrue(front2);
    }

    @Test
    void moveKnight() {
        BoardController controller = new BoardController();

        // movimento certo
        boolean l1 = controller.move(7, 1, 5, 2);
        Assertions.assertTrue(controller.getTypeOf(7, 1) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(5, 2) == PieceType.KNIGHT);
        Assertions.assertTrue(l1);

        // movimento errado
        boolean l2 = controller.move(7, 6, 5, 6);
        Assertions.assertTrue(controller.getTypeOf(7, 6) == PieceType.KNIGHT);
        Assertions.assertTrue(controller.getTypeOf(5, 6) == PieceType.NULL);
        Assertions.assertFalse(l2);

        // movimento certo
        boolean l3 = controller.move(7, 6, 5, 5);
        Assertions.assertTrue(controller.getTypeOf(7, 6) == PieceType.NULL);
        Assertions.assertTrue(controller.getTypeOf(5, 5) == PieceType.KNIGHT);
        Assertions.assertTrue(l3);
    }
}