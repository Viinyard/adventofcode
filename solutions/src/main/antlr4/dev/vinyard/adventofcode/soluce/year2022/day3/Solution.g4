grammar Solution;

options {
    language = Java;
}

@header {

}

part2 returns [ASD.Part2 out]
    @init {
        List<ASD.Group> groups = new ArrayList<>();
    }
    : group[groups]+ NEWLINE* EOF {
        $out = new ASD.Part2(groups);
    }
    ;

part1 returns [ASD.Part1 out]
    @init {
        List<ASD.Rucksack> rucksacks = new ArrayList<>();
    }
    : rucksack[rucksacks]+ NEWLINE* EOF {
        $out = new ASD.Part1(rucksacks);
    }
    ;



group [List<ASD.Group> groups]
    @init {
        List<ASD.Rucksack> rucksacks = new ArrayList<>();
    }
    : rucksack[rucksacks] rucksack[rucksacks] rucksack[rucksacks] {
        $groups.add(new ASD.Group(rucksacks));
    }
    ;

rucksack [List<ASD.Rucksack> rucksacks]
    @init {
        List<ASD.Item> items = new ArrayList<>();
    }
    : item[items]+ NEWLINE? {
        $rucksacks.add(new ASD.Rucksack(items));
    }
    ;

item [List<ASD.Item> out]
    : ITEM { $out.add(new ASD.Item($ITEM.text)); }
    ;

ITEM
    : [a-zA-Z]
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t]+ -> skip
    ;