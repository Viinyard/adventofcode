grammar Solution;

options {
    language = Java;
}

@header {

}

part1 returns [ASD.Part1 out]
    @init {
        List<ASD.Round> rounds = new ArrayList<>();
    }
    : round[rounds]* EOF {
        $out = new ASD.Part1(rounds);
    }
    ;

part2 returns [ASD.Part2 out]
    @init {
        List<ASD.Strategy> strategies = new ArrayList<>();
    }
    : strategy[strategies]* EOF {
        $out = new ASD.Part2(strategies);
    }
    ;

strategy [List<ASD.Strategy> strategies]
    : opponent=shape player=outcome NEWLINE? {
        $strategies.add(new ASD.Strategy($opponent.out, $player.out));
    }
    ;

round [List<ASD.Round> rounds]
    : opponent=shape player=shape NEWLINE? {
        $rounds.add(new ASD.Round($opponent.out, $player.out));
    }
    ;

outcome returns [ASD.Outcome out]
    : PLAYER_ROCK { $out = ASD.Outcome.LOSE; }
    | PLAYER_PAPER { $out = ASD.Outcome.DRAW; }
    | PLAYER_SCISSORS { $out = ASD.Outcome.WIN; }
    ;

shape returns [ASD.Shape out]
    : (PLAYER_ROCK | OPPONENT_ROCK) { $out = new ASD.Rock(); }
    | (PLAYER_PAPER | OPPONENT_PAPER) { $out = new ASD.Paper(); }
    | (PLAYER_SCISSORS | OPPONENT_SCISSORS) { $out = new ASD.Scissors(); }
    ;


OPPONENT_ROCK
    : 'A'
    ;

PLAYER_ROCK
    : 'X'
    ;

OPPONENT_PAPER
    : 'B'
    ;

PLAYER_PAPER
    : 'Y'
    ;

OPPONENT_SCISSORS
    : 'C'
    ;

PLAYER_SCISSORS
    : 'Z'
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;
