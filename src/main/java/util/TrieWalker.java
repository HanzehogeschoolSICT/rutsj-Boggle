package main.java.util;

import java.util.ArrayList;

public class TrieWalker {
    private BoardNode boardNode;
    private Trie baseNode;

    public TrieWalker(Trie trie, BoardNode boardNode) {
        baseNode = trie;
        this.boardNode = boardNode;
    }

    public ArrayList<Word> getWords() {
        return new RecursiveWalker(boardNode, baseNode, new ArrayList<>()).findWords();
    }

    /**
     * The RecursiveWalker class finds the path to the words.
     */
    private class RecursiveWalker {
        private BoardNode boardNode;
        private Trie trieNode;
        private ArrayList<BoardNode> history;

        private RecursiveWalker(BoardNode boardNode, Trie trieNode, ArrayList<BoardNode> history) {
            this.boardNode = boardNode;
            this.trieNode = trieNode;
            this.history = history;
            history.add(boardNode);
        }

        private ArrayList<Word> findWords() {
            ArrayList<Word> returnList = new ArrayList<>();

            if (trieNode.isWord())
                returnList.add(newWord());

            for (BoardNode neighbour : boardNode.getNeighbours()) {
                if (history.contains(neighbour)) continue;

                if (trieNode.hasChild(neighbour.getValue()))
                    returnList.addAll(new RecursiveWalker(neighbour, trieNode.getChild(neighbour.getValue()), new ArrayList<>(history)).findWords());
            }

            return returnList;
        }

        private Word newWord() {
            ArrayList<CoordPair> coords = new ArrayList<>(history.size());
            char[] chars = new char[history.size()];

            for (int i = 0; i < history.size(); i++) {
                BoardNode bn = history.get(i);
                coords.add(bn.getCoords());
                chars[i] = bn.getValue();
            }

            return new Word(chars, coords);
        }
    }
}
