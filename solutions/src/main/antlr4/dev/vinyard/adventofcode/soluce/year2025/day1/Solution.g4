grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
			List<ASD.DialRotation> rotations = new ArrayList<>();
		}
    : (dial_rotation { rotations.add($dial_rotation.out); })+ EOF {
      $out = new ASD.Root(rotations);
    }
    ;

dial_rotation returns [ASD.DialRotation out]

		: direction steps=INT
			{ $out = new ASD.DialRotation($direction.out, Integer.parseInt($steps.text)); }
		;

direction returns [ASD.Direction out]
		: LEFT { $out = ASD.Direction.LEFT; }
		| RIGHT { $out = ASD.Direction.RIGHT; }
		;


LEFT
		: 'L';

RIGHT
		: 'R';

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;