package dev.vinyard.adventofcode.soluce.year2024.day25;

import java.util.Iterator;
import java.util.List;

public class ASD {

    public static class Root {

        private final List<PinTumblerLock> locks;

        public Root(List<PinTumblerLock> locks) {
            this.locks = locks;
        }

        public long part1() {
            List<Lock> locks = this.locks.stream()
                    .filter(lock -> lock instanceof Lock)
                    .map(lock -> (Lock) lock).toList();

            List<Key> keys = this.locks.stream()
                    .filter(lock -> lock instanceof Key)
                    .map(lock -> (Key) lock).toList();

            return locks.stream().mapToLong(lock -> keys.stream().filter(lock::match).count()).sum();
        }

    }

    public static class PinTumblerLock {

        protected final List<Integer> rows;
        private Integer size;

        public PinTumblerLock(List<Integer> rows) {
            this.rows = rows;
            this.size = 1;
        }

        public boolean match(PinTumblerLock lock) {
            Iterator<Integer> origineIterator = rows.iterator();
            Iterator<Integer> rowIterator = lock.rows.iterator();

            return rows.stream().mapToInt(i -> origineIterator.next() + rowIterator.next())
                    .allMatch(r -> r <= size);
        }

        protected void addRow(List<Integer> row) {
            Iterator<Integer> origineIterator = rows.iterator();
            Iterator<Integer> rowIterator = row.iterator();

            size++;

            rows.replaceAll(i -> origineIterator.next() + rowIterator.next());
        }
    }

    public static class Lock extends PinTumblerLock {

        public Lock(List<Integer> rows) {
            super(rows);
        }
    }

    public static class Key extends PinTumblerLock {

        public Key(List<Integer> rows) {
            super(rows);
        }
    }

}
