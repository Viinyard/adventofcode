grammar Solution;

options {
	language = Java;
}

@header {
import java.util.LinkedList;
}

races returns [ASD.Races out]
	: TIME times=listInt DISTANCE distances=listInt {
		$out = new ASD.Races($times.out, $distances.out);
	}
	;

race returns [ASD.Race out]
	: TIME time=listString DISTANCE distance=listString {
		$out = new ASD.Race(Long.parseLong($time.out), Long.parseLong($distance.out));
	}
	;

listString returns [String out]
	@init {
		String value = "";
	}
	: (value=INT {
		value += $value.text;
	})+ {
		$out = value;
	}
	;

listInt returns [LinkedList<Long> out]
	@init {
		LinkedList<Long> values = new LinkedList<Long>();
	}
	: (value=INT {
		values.add(Long.parseLong($value.text));
	})+ {
		$out = values;
	}
	;

TIME : 'Time' COLON;
DISTANCE : 'Distance' COLON;

fragment COLON : ':';

INT
   // integer part forbis leading 0s (e.g. `01`)
   : '0' | [1-9][0-9]*
   ;

WS
	 : [ \t\r\n]+ -> skip
	 ;