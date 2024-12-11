grammar Solution;

options {
    language = Java;
}

@header {
}

root returns [ASD.DiskMap out]
    : disk NEWLINE*? {
        $out = new ASD.DiskMap($disk.out);
    }
    ;

disk returns [List<ASD.Partition> out]
    @init {
        $out = new ArrayList<>();
        long ID = 0;
    }
    : ((file[ID] {
        $out.add($file.out);
        ID++;
    }) (partition {
        $out.add($partition.out);
    })?)+
    ;

file[long ID] returns [ASD.Partition out]
    : value=DIGIT {
        $out = new ASD.Partition(Integer.valueOf($value.text)).withFile(ID);
    }
    ;

partition returns [ASD.Partition out]
    : value=DIGIT {
        $out = new ASD.Partition(Integer.valueOf($value.text));
    }
    ;

DIGIT
    : [0-9]
    ;

NEWLINE : '\r'? '\n' ;

WS
    : [ \t]+ -> skip
    ;