grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
				List<ASD.Machine> machines = new ArrayList();
		}
    : (machine {
      machines.add($machine.out);
    })+ {
      $out = new ASD.Root(machines);
    }
    ;

machine returns [ASD.Machine out]
		:
		a=buttonA b=buttonB p=prize {
			$out = new ASD.Machine($a.out, $b.out, $p.out);
		}
		;

buttonA returns [ASD.Button out]
		:
		BUTTON_A X x=INT COMMA Y y=INT {
			$out = new ASD.Button(Long.parseLong($x.text), Long.parseLong($y.text));
		}
		;

buttonB returns [ASD.Button out]
		:
		BUTTON_B X x=INT COMMA Y y=INT {
			$out = new ASD.Button(Long.parseLong($x.text), Long.parseLong($y.text));
		}
		;

prize returns [ASD.Prize out]
		:
		PRIZE X EQUAL x=INT COMMA Y EQUAL y=INT {
			$out = new ASD.Prize(Long.parseLong($x.text), Long.parseLong($y.text));
		}
		;

fragment SEMICOLON
		: ':'
		;

BUTTON_A
		: 'Button A' SEMICOLON
		;

BUTTON_B
		: 'Button B' SEMICOLON
		;

PRIZE
		: 'Prize' SEMICOLON
		;

X
		: 'X'
		;

Y
		: 'Y'
		;

COMMA
		: ','
		;

EQUAL
		: '='
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : ('+' | '-')? ('0' | [1-9][0-9]*)
    ;

WS
    : [ \t\n\r]+ -> skip
    ;
