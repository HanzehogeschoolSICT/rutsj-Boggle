package main.java.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;

public class WordlistReader {
    private String fileName;
    private Trie trie;
    private int minWordSize;

    public WordlistReader(String fileName, int minWordSize) {
        this.fileName = fileName;
        trie = new Trie();
        this.minWordSize = minWordSize;
    }

    // The closing of the reader has roughly been taken from here:
    // http://stackoverflow.com/a/884182
    public Trie read() throws IOException {
        BufferedReader reader = null;
        Throwable error = null;

        try {
            reader = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() < minWordSize) continue;

                line = line.toLowerCase();

                trie.add(
                        // Thanks! http://stackoverflow.com/a/4122207
                        Normalizer.normalize(line, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "")
                );
            }
        } catch (IOException e) {
            error = e;
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                if (error == null)
                    error = e;
            }
        }

        if (error != null) {
            throw (IOException) error;
        }

        return trie;
    }
}
