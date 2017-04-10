package main.java.util;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardNode {
    private char value;
    private ArrayList<BoardNode> neighbours = new ArrayList<>();
    private CoordPair coords;


    public BoardNode(char value, CoordPair coords, BoardNode[] neighbours) {
        this.value = value;
        this.coords = coords;

        if (neighbours != null)
            this.neighbours.addAll(Arrays.asList(neighbours));
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public void addNeighbour(BoardNode neighbour) {
        neighbours.add(neighbour);
    }

    public boolean removeNeighbour(BoardNode neighbour) {
        return neighbours.remove(neighbour);
    }

    public  ArrayList<BoardNode> getNeighbours() {
        return neighbours;
    }

    public boolean hasNeighbour(BoardNode neighbour) {
        return neighbours.contains(neighbour);
    }

    public CoordPair getCoords() {
        return coords;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
