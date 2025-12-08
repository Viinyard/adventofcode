grammar Solution;

options {
    language = Java;
}

@header {
import org.apache.commons.geometry.euclidean.threed.Vector3D;
}

root returns [ASD.Root out]
		@init {
			List<ASD.JunctionBoxe> junctionBoxes = new ArrayList<>();
			int connections = 1000;
		}
    : (param { connections = $param.out; })? (junction_boxe { junctionBoxes.add($junction_boxe.out); })+ EOF {
      $out = new ASD.Root(junctionBoxes, connections);
    }
    ;

param returns [int out]
		: CONNECTIONS COLON INT {
				$out = Integer.parseInt($INT.text);
		}
		;

junction_boxe returns [ASD.JunctionBoxe out]
		: x=INT COMMA y=INT COMMA z=INT {
				$out = new ASD.JunctionBoxe(Vector3D.of(Integer.parseInt($x.text), Integer.parseInt($y.text), Integer.parseInt($z.text)));
		}
		;

CONNECTIONS
		: 'connections'
		;

COLON
		: ':'
		;

COMMA
		: ','
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;