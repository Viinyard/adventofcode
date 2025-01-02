grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.PinTumblerLock> lock = new ArrayList<>();
    }
    : ((lock {
        lock.add($lock.out);
    } | key {
        lock.add($key.out);
    }) NEWLINE*?)+ EOF {
        $out = new ASD.Root(lock);
    }
    ;

lock returns [ASD.Lock out]
    : filled_line {
        $out = $filled_line.out;
    }
    (line {
        $out.addRow($line.out);
    })+
    ;

key returns [ASD.Key out]
    : empty_line {
        $out = $empty_line.out;
    }
    (line {
        $out.addRow($line.out);
    })+
    ;

line returns [List<Integer> out]
    @init {
        List<Integer> row = new ArrayList<>();
    }
    : (FILLED {
        row.add(1);
    } | EMPTY {
        row.add(0);
    })+ NEWLINE {
        $out = row;
    }
    ;

filled_line returns [ASD.Lock out]
    @init {
        List<Integer> row = new ArrayList<>();
    }
    : (FILLED {
        row.add(1);
    })+ NEWLINE {
        $out = new ASD.Lock(row);
    }
    ;

empty_line returns [ASD.Key out]
    @init {
        List<Integer> row = new ArrayList<>();
    }
    : (EMPTY {
        row.add(0);
    })+ NEWLINE {
        $out = new ASD.Key(row);
    }
    ;

FILLED
    : '#'
    ;

EMPTY
    : '.'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;