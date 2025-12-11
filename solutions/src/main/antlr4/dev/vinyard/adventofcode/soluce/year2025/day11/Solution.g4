grammar Solution;

options {
    language = Java;
}

@header {
import java.util.Map;
import java.util.HashMap;
}

root returns [ASD.Root out]
	@init {
		Map<String, List<String>> wirings = new HashMap<>();
	}
    : (wiring[wirings])* EOF {
	    $out = new ASD.Root(wirings);
	}
    ;

wiring [Map<String, List<String>> wirings]
	: device COLON devices {
		wirings.put($device.out, $devices.out);
	}
	;

devices returns [List<String> out]
	@init {
		$out = new ArrayList<>();
	}
	:
	(device {
		$out.add($device.out);
	})+
	;

device returns [String out]
	: ID {
		$out = $ID.text;
	}
	;

COLON
	: ':'
	;

ID
	: [a-z]+
	;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;