package com.ru.usty.threads;

public class Tile {
    int x;
    int y;
    int travel;

    public Tile(int x, int y, int travel) {
        this.x = x;
        this.y = y;
        this.travel = travel;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.travel = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTravel() {
		return travel;
	}

	public void setTravel(int travel) {
		this.travel = travel;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        if (x != tile.x) return false;
        return y == tile.y;

    }

    @Override
    public int hashCode() {
        return Problematic.PROBLEM_WIDTH * x + y;
    }
    
    @Override
    public String toString() {
    	return "(" + x + "," + y + ")";
    }
}
