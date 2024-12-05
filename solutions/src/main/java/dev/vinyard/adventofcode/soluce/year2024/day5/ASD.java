package dev.vinyard.adventofcode.soluce.year2024.day5;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        public List<PageOrderingRule> pageOrderingRules;
        public List<Update> pageUpdates;

        public Root(List<PageOrderingRule> pageOrderingRules, List<Update> pageUpdates) {
            this.pageOrderingRules = pageOrderingRules;
            this.pageUpdates = pageUpdates;
        }

        public List<Update> getAllValidUpdate() {
            return this.pageUpdates.stream()
                    .filter(update -> this.pageOrderingRules.stream().filter(update::isRuleApplied).allMatch(update::isValidUpdate))
                    .toList();
        }

        public List<Update> getAllInvalidUpdate() {
            return this.pageUpdates.stream()
                    .filter(update -> this.pageOrderingRules.stream().filter(update::isRuleApplied)
                            .anyMatch(rule -> !update.isValidUpdate(rule)))
                    .toList();
        }

        public void correct(Update update) {
            update.correct(this.pageOrderingRules);
        }
    }

    public record PageOrderingRule(int before, int after) {
    }

    public record Update(List<Integer> pages) {

        public int getMiddlePage() {
            return pages.get(pages.size() / 2);
        }

        public boolean isValidUpdate(PageOrderingRule rule) {
            return pages.indexOf(rule.before()) < pages.indexOf(rule.after());
        }

        public boolean isRuleApplied(PageOrderingRule rule) {
            return pages.contains(rule.before()) && pages.contains(rule.after());
        }

        public void correct(List<PageOrderingRule> pageOrderingRules) {
            Map<Integer, List<Integer>> beforeAllOfMap = pageOrderingRules.stream().filter(this::isRuleApplied).collect(Collectors.groupingBy(PageOrderingRule::before, Collectors.mapping(PageOrderingRule::after, Collectors.toList())));

            beforeAllOfMap.forEach((key, value) -> {
                int indexBefore = pages.indexOf(key);
                int newIndex = value.stream().mapToInt(pages::indexOf).min().orElseThrow();
                if (newIndex < indexBefore) {
                    pages.remove(indexBefore);
                    pages.add(newIndex, key);
                }
            });
        }
    }
}
