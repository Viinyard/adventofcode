grammar Solution;

options {
	language = Java;
}

scratchcards returns [ASD.Scratchcard out]
	@init {
	    ASD.Scratchcard scratchcard = null;
	}
  : (scratchcard[scratchcard] {
  	scratchcard = $scratchcard.out;
	})+ {
		$out = scratchcard;
	}
	;

scratchcard [ASD.Scratchcard previous] returns [ASD.Scratchcard out]
	: (CARD cardNumber=INT COLON num=numbers PIPE winNum=numbers) {
		$out = new ASD.Scratchcard(previous, Long.parseLong($cardNumber.text), $num.out, $winNum.out);
	}
	;

numbers returns [List<Long> out]
	@init {
	    List<Long> numbers = new ArrayList<Long>();
	}
	: (number=INT {
		numbers.add(Long.parseLong($number.text));
	})+ {
		$out = numbers;
	}
	;

INT
   // integer part forbis leading 0s (e.g. `01`)
   : '0' | [1-9][0-9]*
   ;

COLON : ':';
CARD : 'Card';
PIPE : '|';

WS
   : [ \t\n\r] + -> skip
   ;