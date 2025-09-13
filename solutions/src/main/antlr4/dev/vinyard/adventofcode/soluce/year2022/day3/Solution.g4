grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Rucksack> rucksacks = new ArrayList<>();
    }
    : rucksack[rucksacks]+ EOF {
        $out = new ASD.Root(rucksacks);
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
    : [ \t\n\r]+ -> skip
    ;