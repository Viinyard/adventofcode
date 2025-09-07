grammar Solution;

options {
    language = Java;
}

@header {
import org.apache.commons.geometry.euclidean.threed.Vector3D;
import org.apache.commons.geometry.euclidean.threed.Bounds3D;
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init {
        LinkedList<ASD.Brick> bricks = new LinkedList<>();
    }
    : (brick[bricks])* {
        bricks.sort(ASD.Brick::compareTo);
        $out = new ASD.Root(bricks);
    }
    ;

brick[LinkedList<ASD.Brick> bricks] returns [ASD.Brick out]
    : min=point3d TILDE max=point3d {
        $bricks.add(new ASD.BrickProxy(Bounds3D.from($min.out, $max.out), $bricks));
    }
    ;

point3d returns [Vector3D out]
    : x=number ',' y=number ',' z=number {
        $out = Vector3D.of($x.out, $y.out, $z.out);
    }
    ;

number returns [int out]
    : INT { $out = Integer.parseInt($INT.text); }
    ;



INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

TILDE
    : '~'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;