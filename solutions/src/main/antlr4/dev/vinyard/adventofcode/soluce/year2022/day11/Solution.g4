grammar Solution;

options {
    language = Java;
}

@header {
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init {
        Map<Integer, ASD.Monkey> monkeys = new HashMap<>();
        List<ASD.Monkey> monkeyList = new ArrayList<>();
    }
    : (monkey[monkeys] { monkeyList.add($monkey.out); } )+ EOF {
        $out = new ASD.Root(monkeyList);
    }
    ;

monkey_ref [Map<Integer, ASD.Monkey> monkeys] returns [ASD.Monkey out]
    : INT {
        int id = Integer.parseInt($INT.text);
        $monkeys.putIfAbsent(id, new ASD.Monkey(id));
        $out = $monkeys.get(id);
    }
    ;

monkey [Map<Integer, ASD.Monkey> monkeys] returns [ASD.Monkey out]
    : MONKEY monkey_ref[monkeys] COLON starting_items operation test if_true[monkeys] if_false[monkeys] {
        $monkey_ref.out.setItems($starting_items.out);
        $monkey_ref.out.setOperation($operation.out);
        $monkey_ref.out.setTest($test.out);
        $monkey_ref.out.setIfTrue($if_true.out);
        $monkey_ref.out.setIfFalse($if_false.out);
        $out = $monkey_ref.out;
    }
    ;

starting_items returns [LinkedList<ASD.Item> out]
    : START items {
        $out = $items.out;
    }
    ;

items returns [LinkedList<ASD.Item> out]
    @init {
        $out = new LinkedList<>();
    }
    : item { $out.add($item.out); } (COMMA item { $out.add($item.out); })*
    ;

item returns [ASD.Item out]
    : INT { $out = new ASD.Item(Integer.parseInt($INT.text)); }
    ;

operation returns [Function<Integer, Integer> out]
    : OPERATION calculation {
        $out = $calculation.out;
    }
    ;

calculation returns [Function<Integer, Integer> out]
    : OLD PLUS INT {
        $out = a -> a + Integer.parseInt($INT.text);
    }
    | OLD MULTIPLY INT {
        $out = a -> a * Integer.parseInt($INT.text);
    }
    | OLD PLUS OLD {
        $out = a -> a + a;
    }
    | OLD MULTIPLY OLD {
        $out = a -> a * a;
    }
    ;

test returns [Predicate<Integer> out]
    : TEST DIVISIBLE INT {
        $out = a -> a % Integer.parseInt($INT.text) == 0;
    }
    ;

if_true [Map<Integer, ASD.Monkey> monkeys] returns [ASD.Monkey out]
    : IF_TRUE monkey_ref[monkeys] {
        $out = $monkey_ref.out;
    }
    ;

if_false [Map<Integer, ASD.Monkey> monkeys] returns [ASD.Monkey out]
    : IF_FALSE monkey_ref[monkeys] {
        $out = $monkey_ref.out;
    }
    ;

START
    : 'Starting items:'
    ;

COMMA
    : ','
    ;

OPERATION
    : 'Operation: new ='
    ;

PLUS
    : '+'
    ;

OLD
    : 'old'
    ;

MULTIPLY
    : '*'
    ;

TEST
    : 'Test:'
    ;

DIVISIBLE
    : 'divisible by'
    ;

IF_TRUE
    : 'If true: throw to monkey'
    ;

IF_FALSE
    : 'If false: throw to monkey'
    ;

MONKEY
    : 'Monkey'
    ;

COLON
    : ':'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;