grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
			List<ASD.Machine> machines = new ArrayList<>();
		}
    : (machine { machines.add($machine.out); })+ EOF {
			$out = new ASD.Root(machines);
    }
    ;

machine returns [ASD.Machine out]
		: indicator_light button_wiring_schematics joltage_requirements {
				$out = new ASD.Machine($indicator_light.out, $button_wiring_schematics.out, $joltage_requirements.out);
		}
		;

button_wiring_schematics returns [List<ASD.ButtonWiringSchematic> out]
		@init {
				$out = new ArrayList<>();
		}
		: (button_wiring_schematic {
				$out.add($button_wiring_schematic.out);
		})+
		;

button_wiring_schematic returns [ASD.ButtonWiringSchematic out]
		: OPEN_PAREN buttons CLOSE_PAREN {
				$out = new ASD.ButtonWiringSchematic($buttons.out);
		}
		;

joltage_requirements returns [ASD.JoltageRequirements out]
		: OPEN_CURLY joltages CLOSE_CURLY {
				$out = new ASD.JoltageRequirements($joltages.out);
		}
		;

joltages returns [List<Integer> out]
		@init {
				$out = new ArrayList<>();
		}
		: joltage {
				$out.add($joltage.out);
		} (COMMA joltage {
				$out.add($joltage.out);
		})*
		;

joltage returns [Integer out]
		: INT {
				$out = Integer.parseInt($INT.text);
		}
		;

buttons returns [List<ASD.Button> out]
		@init {
				$out = new ArrayList<>();
		}
		: button {
				$out.add($button.out);
		} (COMMA button {
				$out.add($button.out);
		})*
		;

button returns [ASD.Button out]
		: INT {
				$out = new ASD.Button(Integer.parseInt($INT.text));
		}
		;

indicator_light returns [ASD.IndicatorLightDiagram out]
		: OPEN_BRACKET light_states CLOSE_BRACKET {
				$out = new ASD.IndicatorLightDiagram($light_states.out);
		}
		;

light_states returns [List<ASD.LightState> out]
		@init {
				$out = new ArrayList<>();
		}
		: (light_state {
				$out.add($light_state.out);
			})*
		;

light_state returns [ASD.LightState out]
		:
		INDICATOR_LIGHT_OFF {
				$out = ASD.LightState.OFF;
		}
		|
		INDICATOR_LIGHT_ON {
				$out = ASD.LightState.ON;
		}
		;

OPEN_PAREN
		: '('
		;

CLOSE_PAREN
		: ')'
		;

COMMA
		: ','
		;

OPEN_CURLY
		: '{'
		;

CLOSE_CURLY
		: '}'
		;

OPEN_BRACKET
		: '['
		;

CLOSE_BRACKET
		: ']'
		;

INDICATOR_LIGHT_OFF
		: '.'
		;

INDICATOR_LIGHT_ON
		: '#'
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;