package dev.vinyard.adventofcode.soluce.year2025.day3;

import lombok.Getter;

import java.util.List;

public class ASD {

    public static class Root {

        private List<Bank> banks;

        public Root(List<Bank> banks) {
            this.banks = banks;
        }

        public long solution1() {
            return banks.stream()
                    .mapToLong(Bank::getMaxJoltage)
                    .sum();
        }

    }

    public static class Bank {

        @Getter
        private List<Integer> joltages;

        public Bank(List<Integer> joltages) {
            this.joltages = joltages;
        }

        public long getMaxJoltage() {
            int firstMaxIndex = getMaxDigitFromIndex(0, joltages.size() - 1);
            int secondMaxIndex = getMaxDigitFromIndex(firstMaxIndex + 1, joltages.size());

            return joltages.get(firstMaxIndex) * 10 + joltages.get(secondMaxIndex);
        }

        public int getMaxDigitFromIndex(int startIndex, int endIndex) {
            int maxDigit = -1;
            int index = -1;
            for (int i = startIndex; i < endIndex; i++) {
                if (joltages.get(i) > maxDigit) {
                    maxDigit = joltages.get(i);
                    index = i;
                }
            }

            return index;
        }
    }

}
