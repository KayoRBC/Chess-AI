package com.Kayo.model.ai;

public class MovePositions {
    private final int fromLine;
    private final int fromColumn;
    private final int toLine;
    private final int toColumn;

    public MovePositions(int fromLine, int fromColumn, int toLine, int toColumn) {
        this.fromLine = fromLine;
        this.fromColumn = fromColumn;
        this.toLine = toLine;
        this.toColumn = toColumn;
    }

    public int getFromLine() {
        return fromLine;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public int getToLine() {
        return toLine;
    }

    public int getToColumn() {
        return toColumn;
    }
}
