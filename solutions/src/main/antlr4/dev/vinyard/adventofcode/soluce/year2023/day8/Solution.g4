grammar Solution;

options {
	language = Java;
}

@header {
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
}

maps returns [ASD.Maps out]
	: directionList NEWLINE+ network {
		$out = new ASD.Maps($directionList.out, $network.out);
	}
	;

directionList returns [LinkedList<ASD.Direction> out]
	@init {
		LinkedList<ASD.Direction> values = new LinkedList<>();
	}
	: (direction {
		values.add($direction.out);
	})+ {
		$out = values;
	}
	;

direction returns [ASD.Direction out]
	: LETTER {
		$out = switch ($LETTER.text) {
			case "R" -> ASD.Direction.RIGHT;
			case "L" -> ASD.Direction.LEFT;
			default -> throw new IllegalStateException("Unexpected value: " + $LETTER.text);
		};
	}
	;

network returns [Map<String, ASD.Node> out]
	@init {
		Map<String, ASD.Node> keyNodes = new HashMap<>();
	}
	: (key EQUALS node NEWLINE? {
		keyNodes.put($key.out, $node.out);
	})+ {
		$out = keyNodes;
	}
	;

node returns [ASD.Node out]
	: L_BRACKET left=key COMMA right=key R_BRACKET {
		$out = new ASD.Node($left.out, $right.out);
	}
	;

key returns [String out]
	@init {
		String text = "";
	}
	: (LETTER {
		text += $LETTER.text;
	})+ {
		$out = text;
	}
	;

L_BRACKET : '(';
R_BRACKET : ')';

COMMA : ',';

EQUALS : '=';

LETTER : [0-9A-Z];

NEWLINE : '\r'? '\n' ;

WS
	 : [ \t]+ -> skip
	 ;