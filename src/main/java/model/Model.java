package main.java.model;

import javafx.collections.ObservableList;
import main.java.util.Word;
import main.java.view.View;

import java.util.ArrayList;

public abstract class Model {
    private ArrayList<View> views;
    protected boolean hasChanged = true;

    public Model() {
        views = new ArrayList<>();
    }

    protected void updateViews() {
        views.forEach(View::update);
    }

    public void addView(View view) {
        views.add(view);
    }

    public void removeView(View view) {
        views.remove(view);
    }

    public abstract int getSize();

    public abstract char charAt(int x, int y);

    public boolean hasChanged() {
        return hasChanged;
    }

    public abstract Word getPreviousWord();

    public abstract Word getCurrentWord();

    public abstract ObservableList<Word> getWordList();
}
