grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
			List<ASD.Wiring> wirings = new ArrayList<>();
		}
    : (wiring {
			wirings.add($wiring.out);
		})* EOF {
			$out = new ASD.Root(wirings);
		}
    ;

wiring returns [ASD.Wiring out]
		: device COLON devices {
			$out = new ASD.Wiring($device.out, $devices.out);
		}
		;

devices returns [List<ASD.Device> out]
	@init {
		$out = new ArrayList<>();
	}
	:
	(device {
		$out.add($device.out);
	})+
	;

device returns [ASD.Device out]
	: ID {
		$out = new ASD.Device($ID.text);
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