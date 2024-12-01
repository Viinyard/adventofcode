package dev.vinyard.adventofcode.soluce.year2023.day5;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ASD {

    @Data
    public static class Almanach {
        private List<Range> seedRanges;
        private List<Mapping> seedToSoil;
        private final UnaryOperator<List<Range>> seedToSoilFunction = seed -> mapping(seed, seedToSoil);
        private List<Mapping> soilToFertilizer;
        private final UnaryOperator<List<Range>> soilToFertilizerFunction = soil -> mapping(soil, soilToFertilizer);
        private List<Mapping> fertilizerToWater;
        private final UnaryOperator<List<Range>> fertilizerToWaterFunction = fertilizer -> mapping(fertilizer, fertilizerToWater);
        private List<Mapping> waterToLight;
        private final UnaryOperator<List<Range>> waterToLightFunction = water -> mapping(water, waterToLight);
        private List<Mapping> lightToTemperature;
        private final UnaryOperator<List<Range>> lightToTemperatureFunction = light -> mapping(light, lightToTemperature);
        private List<Mapping> temperatureToHumidity;
        private final UnaryOperator<List<Range>> temperatureToHumidityFunction = temperature -> mapping(temperature, temperatureToHumidity);
        private List<Mapping> humidityToLocation;
        private final UnaryOperator<List<Range>> humidityToLocationFunction = humidity -> mapping(humidity, humidityToLocation);

        private final Function<List<Range>, List<Range>> seedToLocationFunction = seedToSoilFunction.andThen(soilToFertilizerFunction).andThen(fertilizerToWaterFunction).andThen(waterToLightFunction).andThen(lightToTemperatureFunction).andThen(temperatureToHumidityFunction).andThen(humidityToLocationFunction);

        public static List<Range> mapping(List<Range> source, List<Mapping> mappings) {
            return source.stream().flatMap(s -> mappings.stream().map(m -> m.toDestination(s)).filter(r -> r.getLength() > 0)).collect(Collectors.toList());
        }

        private void addDefaultMappings(List<Mapping> mappings) {
            List<Range> defaultRange = new ArrayList<>();

            long start = 0L;

            Collections.sort(mappings);
            for (Mapping mapping : mappings) {
                defaultRange.add(Range.of(start, mapping.getSourceRange().getStart()));
                start = mapping.getSourceRange().getEnd();
            }

            defaultRange.add(Range.of(start, Long.MAX_VALUE));

            defaultRange.stream().filter(r -> r.getLength() > 0).map(r -> new Mapping(r, r)).forEach(mappings::add);
        }

        public void setSeedToSoil(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.seedToSoil = mappings;
        }

        public void setSoilToFertilizer(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.soilToFertilizer = mappings;
        }

        public void setFertilizerToWater(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.fertilizerToWater = mappings;
        }

        public void setWaterToLight(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.waterToLight = mappings;
        }

        public void setLightToTemperature(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.lightToTemperature = mappings;
        }

        public void setTemperatureToHumidity(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.temperatureToHumidity = mappings;
        }

        public void setHumidityToLocation(List<Mapping> mappings) {
            addDefaultMappings(mappings);
            this.humidityToLocation = mappings;
        }
    }

    @Data
    public static class Mapping implements Comparable<Mapping> {
        private Range sourceRange;
        private Range destinationRange;

        private Mapping(Range sourceRange, Range destinationRange) {
            this.sourceRange = sourceRange;
            this.destinationRange = destinationRange;
        }

        public Mapping(long destination, long source, long range) {
            this(Range.of(source, source + range), Range.of(destination, destination + range));
        }

        public long getTranslation() {
            return -sourceRange.getStart() + destinationRange.getStart();
        }

        public Range toDestination(Range range) {
            return sourceRange.intersectionWith(range).translate(getTranslation());
        }

        @Override
        public int compareTo(Mapping o) {
            return sourceRange.compareTo(o.sourceRange);
        }
    }

    @Data
    public static class Range implements Comparable<Range> {
        private long start;
        private long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public static Range of(long start, long end) {
            return new Range(start, end);
        }

        public Range intersectionWith(Range range) {
            return Range.of(Math.max(start, range.start), Math.min(end, range.end));
        }

        public Range translate(long offset) {
            return Range.of(start + offset, end + offset);
        }

        public long getLength() {
            return end - start;
        }

        @Override
        public int compareTo(Range o) {
            return Long.compare(start, o.start);
        }
    }
}
