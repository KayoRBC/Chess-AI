package com.Kayo.model.movements;

import com.Kayo.model.Board;

public class QueenMovementRules extends MovementRules{
    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        MovementRules diagonalMovement = new BishopMovementRules();
        MovementRules frontMovement = new RookMovementRules();

        boolean canMove1 = diagonalMovement.isValidMove(board, fromLine, fromColumn, toLine, toColumn);
        boolean canMove2 = frontMovement.isValidMove(board, fromLine, fromColumn, toLine, toColumn);

        return canMove1 || canMove2;
    }
}
