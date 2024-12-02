package dev.vinyard.adventofcode.soluce.year2024.day2;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ASD {

    public record Root (List<Report> reports) {}

    public record Report(List<Long> values) {

        public LongSummaryStatistics getLongSummaryStatisticsOnDifference() {
            return IntStream.range(1, values.size())
                    .mapToLong(i -> values.get(i) - values.get(i - 1))
                    .summaryStatistics();
        }

        @SafeVarargs
        public final boolean isDifferenceIsBetween(Range<Long>... ranges) {
            return Arrays.stream(ranges).anyMatch(this::isDifferenceIsBetween);
        }

        public boolean isDifferenceIsBetween(Range<Long> range) {
            final LongSummaryStatistics summaryStatistics = getLongSummaryStatisticsOnDifference();

            return Stream.of(
                    summaryStatistics.getMin(),
                    summaryStatistics.getMax()
            ).allMatch(range::contains);
        }

    }

}
