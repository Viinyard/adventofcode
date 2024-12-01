grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<Long> left = new ArrayList<>();
        List<Long> right = new ArrayList<>();
    }
    : (locationIdPair {
        left.add($locationIdPair.out.left());
        right.add($locationIdPair.out.right());
    })* EOF {
        $out = new ASD.Root(left, right);
    }
    ;

locationIdPair returns [ASD.LocationIdPair out]
    : left=INT right=INT {
        $out = new ASD.LocationIdPair(Long.valueOf($left.text), Long.valueOf($right.text));
    }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;