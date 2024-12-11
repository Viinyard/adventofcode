package dev.vinyard.adventofcode.soluce.year2024.day9;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class DiskMap {

        private final List<Partition> disk;
        private List<Long> compressedDisk;

        public DiskMap(List<Partition> disk) {
            this.disk = disk;
        }

        public void compress() {

            List<Long> flattenDisk = disk.stream().map(Partition::getContent).flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));

            LinkedList<Long> files = flattenDisk.stream().filter(Objects::nonNull).collect(Collectors.toCollection(LinkedList::new));

            compressedDisk = new ArrayList<>(flattenDisk.size());

            flattenDisk.forEach(id -> {
                if (id == null)
                    compressedDisk.add(files.pollLast());
                else
                    compressedDisk.add(files.pollFirst());
            });
        }

        public void compressWithoutFragmentation() {
            LinkedList<Partition> orderedPartitions = new LinkedList<>(disk);

            while (!orderedPartitions.isEmpty()) {
                Partition partition = orderedPartitions.pollLast();

                if (!partition.isEmpty())
                    orderedPartitions.stream().filter(f -> f.getFreeSpace() >= partition.files.size()).findAny().ifPresent(f -> f.move(partition.files));
            }

            this.compressedDisk = disk.stream().map(Partition::getContent).flatMap(List::stream).collect(Collectors.toCollection(ArrayList::new));
        }

        public long checksum() {
            return Stream.iterate(0, i -> i+1).limit(compressedDisk.size()).mapToLong(i -> Optional.ofNullable(compressedDisk.get(i)).orElse(0L) * i).sum();
        }
    }

    @Getter
    public static class Partition {

        private final int size;
        private final LinkedList<Long> files = new LinkedList<>();

        public Partition(int size) {
            this.size = size;
        }

        public Partition withFile(Long ID) {
            files.addAll(Collections.nCopies(size - files.size(), ID));
            return this;
        }

        public List<Long> getContent() {
            List<Long> files = new ArrayList<>(this.files);
            files.addAll(Collections.nCopies(size - files.size(), null));
            return files;
        }

        public void move(LinkedList<Long> files) {
            while (!files.isEmpty())
                this.files.addLast(files.poll());
        }

        public long getFreeSpace() {
            return size - files.size();
        }

        public boolean isEmpty() {
            return files.isEmpty();
        }
    }
}
