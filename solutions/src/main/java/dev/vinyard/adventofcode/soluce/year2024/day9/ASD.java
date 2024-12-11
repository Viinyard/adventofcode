package dev.vinyard.adventofcode.soluce.year2024.day9;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class DiskMap {

        private List<File> disk;

        public DiskMap(List<File> disk) {
            this.disk = disk;
        }

        public void compress() {

            List<File> flattenDisk = disk.stream().peek(File::flatten).map(File::getPartition).flatMap(LinkedList::stream).collect(Collectors.toCollection(LinkedList::new));

            LinkedList<File> newFiles = flattenDisk.stream().filter(File::isUsed).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<File> freeSpaces = flattenDisk.stream().filter(f -> !f.isUsed()).collect(Collectors.toCollection(LinkedList::new));

            List<File> compressedDisk = new ArrayList<>(flattenDisk.size());

            for (File file : flattenDisk) {
                if (file.isUsed()) {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollFirst()).orElseGet(freeSpaces::poll));
                } else {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollLast()).orElseGet(freeSpaces::poll));
                }
            }

            disk = compressedDisk;
        }

        public void compressWithoutFragmentation() {
            LinkedList<File> orderedFiles = new LinkedList<>(disk);

            while (!orderedFiles.isEmpty()) {
                File file = orderedFiles.pollLast();

                if (file.isUsed())
                    orderedFiles.stream().filter(f -> !f.isUsed()).filter(f -> f.getFreeSpace() >= file.size).findFirst().ifPresent(f -> f.move(file.getPartition()));
            }

            this.disk = disk.stream().peek(File::flatten).map(File::getPartition).flatMap(LinkedList::stream).toList();
        }

        public long checksum() {
            return Stream.iterate(0, i -> i + 1).limit(disk.size()).parallel().mapToLong(i -> disk.get(i).isUsed() ? (long) i * disk.get(i).ID : 0).sum();
        }

        @Override
        public String toString() {
            return disk.stream().map(File::toString).collect(Collectors.joining());
        }
    }

    @Getter
    public static class File {

        private Integer ID;
        private int size;
        private LinkedList<File> partition;

        public File() {
            this(0, null);
        }

        public File(int size, Integer ID) {
            this.size = size;
            this.ID = ID;
            this.partition = Stream.generate(() -> this).limit(size).filter(File::isUsed).collect(Collectors.toCollection(LinkedList::new));
        }

        public File(int size) {
            this(size, null);
        }

        public void move(LinkedList<File> files) {
            if (files.size() > getFreeSpace())
                throw new IllegalArgumentException("Not enough space to move files");
            while (!files.isEmpty())
                partition.addLast(files.pollFirst());
        }

        public void flatten() {
            this.partition = Stream.generate(() -> Optional.ofNullable(partition.pollFirst()).orElseGet(File::new)).limit(size).collect(Collectors.toCollection(LinkedList::new));
        }

        public long getFreeSpace() {
            return size - partition.size();
        }

        public boolean isUsed() {
            return ID != null;
        }

        @Override
        public String toString() {
            return isUsed() ? ID.toString() : ".";
        }
    }
}
