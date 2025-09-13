package dev.vinyard.adventofcode.soluce.year2022.day6;

import lombok.Getter;

import java.util.LinkedList;

public class ASD {

    public static class Root {

        private final LinkedList<String> datastream;

        public Root(LinkedList<String> datastream) {
            this.datastream = datastream;
        }

        public long findStartOfPacket() {
            Buffer buffer = new Buffer();


            while (!buffer.isStartOfPacket()) {
                buffer.add(this.datastream.removeFirst());
            }

            return buffer.getIndex();
        }
    }

    public static class Buffer {

        private final LinkedList<String> buffer;

        @Getter
        private long index = 0;

        public Buffer() {
            this.buffer = new LinkedList<>();
        }

        public void add(String s) {
            this.buffer.addLast(s);
            this.index++;

            while (this.buffer.size() > 4)
                this.buffer.removeFirst();
        }

        public boolean isStartOfPacket() {
            return this.buffer.stream().distinct().count() == 4;
        }

    }

}
