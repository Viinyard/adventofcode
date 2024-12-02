package dev.vinyard.adventofcode.soluce.year2024.day2;

import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ASD {

    public record Root (List<Report> reports) {}

    public record Report(List<Long> values) {

        public LongSummaryStatistics getLongSummaryStatisticsOnDifference(List<Long> values) {
            return IntStream.range(1, values.size())
                    .mapToLong(i -> values.get(i) - values.get(i - 1))
                    .summaryStatistics();
        }

        public Stream<LongSummaryStatistics> getLongSummaryStatisticsOnEachVariation() {
            return IntStream.range(-1, values.size())
                    .mapToObj(i -> IntStream.iterate(0, x -> x < values.size(), x -> ++x).filter(x -> x != i).mapToObj(values::get).toList())
                    .map(this::getLongSummaryStatisticsOnDifference);
        }

        @SafeVarargs
        public final boolean isDifferenceIsBetween(Range<Long>... ranges) {
            return this.isDifferenceIsBetween(getLongSummaryStatisticsOnDifference(values), ranges);
        }

        @SafeVarargs
        public final boolean isMostOfDifferenceIsBetween(Range<Long>... ranges) {
            return this.isDifferenceIsBetween(getLongSummaryStatisticsOnEachVariation(), ranges);
        }

        @SafeVarargs
        private boolean isDifferenceIsBetween(Stream<LongSummaryStatistics> summaryStatisticsStream, Range<Long>... ranges) {
            return summaryStatisticsStream.anyMatch(s -> isDifferenceIsBetween(s, ranges));
        }

        @SafeVarargs
        public final boolean isDifferenceIsBetween(LongSummaryStatistics summaryStatistics, Range<Long>... ranges) {
            return Arrays.stream(ranges).anyMatch(range -> isDifferenceIsBetween(summaryStatistics, range));
        }

        private boolean isDifferenceIsBetween(LongSummaryStatistics summaryStatistics, Range<Long> range) {
            return Stream.of(
                    summaryStatistics.getMin(),
                    summaryStatistics.getMax()
            ).allMatch(range::contains);
        }
    }

}
