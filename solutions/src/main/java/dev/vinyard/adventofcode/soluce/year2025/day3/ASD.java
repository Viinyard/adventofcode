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
                    .mapToLong(b -> b.getMaxJoltage(2))
                    .sum();
        }

        public Object solution2() {
            return banks.stream()
                    .mapToLong(b -> b.getMaxJoltage(12))
                    .sum();
        }
    }

    public static class Bank {

        @Getter
        private List<Long> joltages;

        public Bank(List<Long> joltages) {
            this.joltages = joltages;
        }

        public long getMaxJoltage(int nbDigits) {
            long maxJoltage = 0;
            int currentIndex = 0;

            for (int i = nbDigits; i > 0; i--) {
                currentIndex = getMaxDigitFromIndex(currentIndex, joltages.size() - i + 1);
                maxJoltage += joltages.get(currentIndex) * (long) Math.pow(10, i - 1);
                currentIndex++;
            }

            return maxJoltage;
        }

        public int getMaxDigitFromIndex(int startIndex, int endIndex) {
            long maxDigit = -1;
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
