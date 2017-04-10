package main.java.util;

import java.util.ArrayList;

public class Word {
    private String value;
    private ArrayList<CoordPair> path;

    public Word(char[] chars, ArrayList<CoordPair> path) {
        value = new String(chars);
        this.path = path;
    }

    public String getWord() {
        return value;
    }

    public ArrayList<CoordPair> getPath() {
        return path;
    }

    public String toPrintString() {
        return "{" + value + ", " + path.toString() + "}";
    }

    @Override
    public String toString() {
        return value;
    }
}
