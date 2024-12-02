grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
			List<ASD.Report> reports = new ArrayList<>();
		}
    : (report {
      reports.add($report.out);
    })* EOF {
      $out = new ASD.Root(reports);
    }
    ;

report returns [ASD.Report out]
	@init {
		List<Long> values = new ArrayList<>();
	}
	: (value=INT {
		values.add(Long.parseLong($value.text));
	})* NEWLINE {
		$out = new ASD.Report(values);
	}
	;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\r]+ -> skip
    ;

NEWLINE
    : '\r'? '\n'
    ;