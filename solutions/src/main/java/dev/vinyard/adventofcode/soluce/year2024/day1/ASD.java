package dev.vinyard.adventofcode.soluce.year2024.day1;

import java.util.List;

public class ASD {

    public record Root(List<Long> left, List<Long> right) { }

    public record LocationIdPair(Long left, Long right) { }
}
