package dev.vinyard.adventofcode.soluce.year2024.day5;

import java.util.List;

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
    }

    public record PageOrderingRule(int before, int after) { }

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

    }
}
