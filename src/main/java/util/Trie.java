package main.java.util;

import java.util.ArrayList;
import java.util.Stack;

public class Trie {
    private char value;
    private boolean isWord = false;
    private Trie[] children;

    public Trie() {
        value = 0;
        children = new Trie[26];
    }

    private Trie(char c) {
        value = c;
        children = new Trie[26];
    }

    public void add(String word) {
        if (word.isEmpty()) {
            isWord = true;
            return;
        }

        int alphabetIndex = alphabetIndexOf(word.charAt(0));

        if (children[alphabetIndex] == null)
            children[alphabetIndex] = new Trie(word.charAt(0));

        children[alphabetIndex].add(word.substring(1));
    }

    public ArrayList<Trie> get(String word) {
        Trie child = children[alphabetIndexOf(word.charAt(0))];

        if (child != null)
            return new ArrayList<>( child.get(word.substring(1), new Stack<>()) );

        return new ArrayList<>();
    }

    private Stack<Trie> get(String word, Stack<Trie> st) {
        st.push(this);

        if (!word.isEmpty()) {
            int index = alphabetIndexOf(word.charAt(0));
            if (children[index] != null)
                children[index].get(word.substring(1), st);
        }

        if (!isWord && st.peek().equals(this))
            st.pop();

        return st;
    }

    public boolean isWord() {
        return isWord;
    }

    public boolean hasWord(String word) {
        if (word.isEmpty())
            return isWord;

        if (children[alphabetIndexOf(word.charAt(0))] == null)
            return false;

        return children[alphabetIndexOf(word.charAt(0))].hasWord(word.substring(1));
    }

    public char getValue() {
        return value;
    }

    public boolean hasChild(char c) {
        return children[alphabetIndexOf(c)] != null;
    }

    public Trie getChild(char c) {
        return children[alphabetIndexOf(c)];
    }

    private static int alphabetIndexOf(char c) {
        return "abcdefghijklmnopqrstuvwxyz".indexOf(c);
    }

    public String nodesToString() {
        String str = "";
        for (int i = 0; i < children.length; i++) {
            if (!(children[i] == null))
                str += i + ": " + children[i].getValue() + ", ";
            else
                str += i + ": " + "null, ";
        }

        return str;
    }

    @Override
    public String toString() {
        return "(" + value + ", " + isWord + ")";
    }
}