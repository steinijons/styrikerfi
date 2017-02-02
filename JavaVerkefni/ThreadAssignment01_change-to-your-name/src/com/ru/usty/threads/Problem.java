package com.ru.usty.threads;

public class Problem {
    private Tile start;
    private Tile end;

    public Problem(Tile start, Tile end) {
        this.start = start;
        this.end = end;
    }

    public Tile getStart() {
        return start;
    }

    public void setStart(Tile start) {
        this.start = start;
    }

    public Tile getEnd() {
        return end;
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    @Override
    public String toString() {
    	return start + " - " + end;
    }
}
