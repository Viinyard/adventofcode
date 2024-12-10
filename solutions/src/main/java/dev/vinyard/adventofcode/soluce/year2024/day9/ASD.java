package dev.vinyard.adventofcode.soluce.year2024.day9;

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
            LinkedList<File> newFiles = disk.stream().filter(File::isUsed).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<File> freeSpaces = disk.stream().filter(f -> !f.isUsed()).collect(Collectors.toCollection(LinkedList::new));

            List<File> compressedDisk = new ArrayList<>(disk.size());

            for (File file : disk) {
                if (file.isUsed()) {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollFirst()).orElseGet(freeSpaces::poll));
                } else {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollLast()).orElseGet(freeSpaces::poll));
                }
            }

            disk = compressedDisk;
        }

        public void compressWithoutFragmentation() {
            LinkedList<File> newFiles = disk.stream().filter(File::isUsed).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<File> freeSpaces = disk.stream().filter(f -> !f.isUsed()).collect(Collectors.toCollection(LinkedList::new));

            List<File> compressedDisk = new ArrayList<>(disk.size());

            for (File file : disk) {
                if (file.isUsed()) {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollFirst()).orElseGet(freeSpaces::poll));
                } else {
                    compressedDisk.add(Optional.ofNullable(newFiles.pollLast()).orElseGet(freeSpaces::poll));
                }
            }

            disk = compressedDisk;
        }

        public long checksum() {
            return Stream.iterate(0, i -> i + 1).limit(disk.size()).mapToLong(i -> disk.get(i).isUsed() ? (long) i * disk.get(i).ID : 0).sum();
        }

        @Override
        public String toString() {
            return disk.stream().map(File::toString).collect(Collectors.joining());
        }
    }

    public static class File {
        private Integer ID;

        public File(Integer ID) {
            this.ID = ID;
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
