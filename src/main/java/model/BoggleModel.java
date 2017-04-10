package main.java.model;

import main.java.util.*;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BoggleModel extends Model implements ModelController {
    private Board board;
    private Trie trie;
    private WorkerManager workerManager = new WorkerManager();

    private Word previousWord;
    private Word currentWord;
    private ObservableList<Word> wordList = FXCollections.observableArrayList();

    public BoggleModel() {
        board = new Board(4);
    }

    public void loadTrie() throws IOException {
        trie = new WordlistReader("res/woordenlijst.txt", 3).read();
        findWords();
    }

    public ObservableList<Word> getWordList() {
        return wordList;
    }

    public void setSize(int size) {
        if (size > 1) {
            board = new Board(size);

            findWords();
            this.hasChanged = true;
            this.updateViews();
        }
    }

    public void setLetters(String letters) {
        Board temp = Board.boardFromString(letters);

        if (temp != null) {
            board = temp;

            findWords();
            this.hasChanged = true;
            this.updateViews();
        }
    }

    private void findWords() {
        wordList.clear();
        wordList.addAll(workerManager.executeBoard(board));
        System.out.println("wordList = " + wordList);
    }

    public void stopThreads() {
        workerManager.stop();
        workerManager.awaitTermination();
    }

    public void updateWord(Word newWord) {
        previousWord = currentWord;
        currentWord = newWord;
        this.updateViews();
    }

    public Word getPreviousWord() {
        return previousWord;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    @Override
    public char charAt(int x, int y) {
        return board.get(x, y).getValue();
    }

    @Override
    public int getSize() {
        return board.getSize();
    }


    /**
     * Deze private class is verantwoordelijk voor het parallel zoeken naar woorden.
     */
    private class WorkerManager {
        private ExecutorService executorService;

        private WorkerManager() {
            executorService = Executors.newCachedThreadPool();
        }

        private ArrayList<Word> executeBoard(Board board) {
            ArrayList<Callable<ArrayList<Word>>> callables = new ArrayList<>(board.getSize() * board.getSize());

            board.getNodes().forEach(bn -> {
                if (trie.hasChild(bn.getValue()))
                    callables.add(() -> new TrieWalker(trie.getChild(bn.getValue()), bn).getWords());
            });

            ArrayList<Word> returnList = new ArrayList<>();

            try {
                executorService.invokeAll(callables)
                        .stream()
                        .map(future -> {
                            try {
                                return future.get();
                            } catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        })
                        .forEach(returnList::addAll);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return returnList;
        }

        private void stop() {
            executorService.shutdownNow();
        }

        private void awaitTermination() {
            try {
                executorService.awaitTermination(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
