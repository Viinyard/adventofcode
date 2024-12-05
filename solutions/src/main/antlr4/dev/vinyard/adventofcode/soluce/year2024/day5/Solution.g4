grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.PageOrderingRule> pageOrderingRules = new ArrayList<>();
        List<ASD.Update> updates = new ArrayList<>();
    }
    : (pageOrderingRule {
        pageOrderingRules.add($pageOrderingRule.out);
    })* (update {
        updates.add($update.out);
    } | NEWLINE)* EOF {
        $out = new ASD.Root(pageOrderingRules, updates);
    }
    ;

pageOrderingRule returns [ASD.PageOrderingRule out]
    : page1=INT PIPE page2=INT NEWLINE {
        $out = new ASD.PageOrderingRule(Integer.valueOf($page1.text), Integer.valueOf($page2.text));
    }
    ;

update returns [ASD.Update out]
    @init {
        List<Integer> pages = new ArrayList<>();
    }
    : (page=INT {
        pages.add(Integer.valueOf($page.text));
    } COMMA?)+ NEWLINE {
        $out = new ASD.Update(pages);
    }
    ;

PIPE
    : '|'
    ;

COMMA
    : ','
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t]+ -> skip
    ;