grammar Solution;

options {
	language = Java;
}

games returns [ASD.Games out]
  @init {
		List<ASD.Game> games = new ArrayList<>();
  }
	: (game {
	   games.add($game.out);
	})* EOF {
		$out = new ASD.Games(games);
	}
	;



game returns [ASD.Game out]
 : GAME value=INT COLON hides {
 		$out = new ASD.Game($value.text, $hides.out);
 }
 ;

hides returns [List<ASD.Hide> out]
 @init {
		List<ASD.Hide> hides = new ArrayList<>();
 }
 : (cubes {
    hides.add(new ASD.Hide($cubes.out));
 }) (SEMICOLON cubes {
 	 hides.add(new ASD.Hide($cubes.out));
 })* {
	 $out = hides;
 }
 ;

cubes returns [List<ASD.Cube> out]
 @init {
		List<ASD.Cube> cubes = new ArrayList<>();
 }
 : cube {
 	 cubes.add($cube.out);
 } (COMMA cube {
 	 cubes.add($cube.out);
 })* {
	 $out = cubes;
 }
 ;

cube returns [ASD.Cube out]
 : value=INT color {
 	 $out = new ASD.Cube($value.text, $color.out);
 }
 ;

color returns [ASD.Color out]
	: GREEN {
		$out = ASD.Color.GREEN;
	}
	| RED {
		$out = ASD.Color.RED;
	}
	| BLUE {
		$out = ASD.Color.BLUE;
	}
	;

COLON : ':';
SEMICOLON : ';';
COMMA : ',';

GREEN : 'green';
RED : 'red';
BLUE : 'blue';

INT
   // integer part forbis leading 0s (e.g. `01`)
   : '0' | [1-9] [0-9]*
   ;

GAME : 'Game';

WS
   : [ \t\n\r] + -> skip
   ;