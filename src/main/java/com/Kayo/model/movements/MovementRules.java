package com.Kayo.model.movements;

import com.Kayo.model.Board;

public abstract class MovementRules {

    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);
}
