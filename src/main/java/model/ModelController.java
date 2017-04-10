package main.java.model;

import main.java.util.Word;

public interface ModelController {
    void setSize(int size);

    void setLetters(String letters);

    void updateWord(Word newWord);
}
