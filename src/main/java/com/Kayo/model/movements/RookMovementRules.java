package com.Kayo.model.movements;

import com.Kayo.model.Board;

public class RookMovementRules extends MovementRules{
    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        return false;
    }
}