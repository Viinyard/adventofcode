grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
			List<ASD.Bank> banks = new ArrayList<>();
		}
    : (bank { banks.add($bank.out); } )+ {
				$out = new ASD.Root(banks);
		}
    ;

bank returns [ASD.Bank out]
		@init {
			List<Long> joltages = new ArrayList<>();
		}
		: (INT {
				joltages.add(Long.parseLong($INT.text));
			} )+ NEWLINE?{
				$out = new ASD.Bank(joltages);
			}
		;

INT
    : [1-9]
    ;

NEWLINE
		: '\r'? '\n'
		;

WS
    : [ \t\n\r]+ -> skip
    ;