grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Move> moves = new ArrayList<>();
    }
    : (m=move { moves.add($m.out); })+ EOF {
          $out = new ASD.Root(moves);
      }
    ;

move returns [ASD.Move out]
    : d=direction n=INT {
          $out = new ASD.Move($d.out, Integer.parseInt($n.text));
      }
    ;

direction returns [ASD.Direction out]
    : d=UP    { $out = ASD.Direction.UP; }
    | d=DOWN  { $out = ASD.Direction.DOWN; }
    | d=LEFT  { $out = ASD.Direction.LEFT; }
    | d=RIGHT { $out = ASD.Direction.RIGHT; }
    ;

UP
    : 'U'
    ;

DOWN
    : 'D'
    ;

LEFT
    : 'L'
    ;

RIGHT
    : 'R'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;