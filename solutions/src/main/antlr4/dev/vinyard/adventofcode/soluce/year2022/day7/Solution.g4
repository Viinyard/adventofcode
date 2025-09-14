grammar Solution;

options {
    language = Java;
}

@header {
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init { LinkedList<ASD.Command> cmds = new LinkedList<>(); }
    : (command { cmds.add($command.out); } )+ EOF { $out = new ASD.Root(cmds); }
    ;

command returns [ASD.Command out]
    : CD ROOT {
        $out = new ASD.CdCommand($ROOT.text);
    }
    | CD NAME {
        $out = new ASD.CdCommand($NAME.text);
    }
    | LS {
        $out = new ASD.LsCommand();
    }
    | size=INT name=NAME {
        $out = new ASD.FileCommand(Integer.parseInt($size.text), $name.text);
    }
    | DIR name=NAME {
        $out = new ASD.DirCommand($name.text);
    }
    ;

DIR
    : 'dir'
    ;

NAME
    : [a-zA-Z_.-]+
    ;

CD
    : '$ cd'
    ;

LS
    : '$ ls'
    ;

ROOT
    : '/'
    ;

PRE
    : '..'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;