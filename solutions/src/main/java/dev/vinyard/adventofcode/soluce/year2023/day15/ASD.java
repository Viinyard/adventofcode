package dev.vinyard.adventofcode.soluce.year2023.day15;

import java.util.List;

public class ASD {

    public static class Root {
        List<Word> words;

        public Root(List<Word> words) {
            this.words = words;
        }

        public long hash() {
            return this.words.stream().mapToLong(Word::hash).sum();
        }
    }

    public static class Word {
        public String value;

        public Word(String value) {
            this.value = value;
        }

        public long hash() {
            long currentValue = 0;
            for (char c : value.toCharArray()) {
                currentValue += (int) c;
                currentValue *= 17;
                currentValue %= 256;
            }
            return currentValue;
        }
    }
}
