grammar Solution;

options {
	language=Java;
}

@header {
import java.util.function.Function;
}

games [Function<String, ASD.Card> cardMapping] returns [List<ASD.Game> out]
	@init {
		List<ASD.Game> games = new ArrayList<ASD.Game>();
	}
	: (game[cardMapping] {
		games.add($game.out);
	})+ {
		$out = games;
	}
	;

game [Function<String, ASD.Card> cardMapping] returns [ASD.Game out]
	: hand[cardMapping] SPACE bid NEWLINE? {
		$out = new ASD.Game($hand.out, $bid.out);
	}
	;

hand [Function<String, ASD.Card> cardMapping] returns [List<ASD.Card> out]
	@init {
		List<ASD.Card> cards = new ArrayList<ASD.Card>();
	}
	: (card [cardMapping] {
		cards.add($card.out);
	})+ {
		$out = cards;
	}
	;

card [Function<String, ASD.Card> cardMapping] returns [ASD.Card out]
	: HEAD_CARD  { $out = cardMapping.apply($HEAD_CARD.text); }
	| N { $out = cardMapping.apply($N.text); }
	;

bid returns [long out]
	@init {
		String n = "";
	}
	: (N { n += $N.text; })+ {
		$out = Long.parseLong(n);
	}
	;

HEAD_CARD : A | K | Q | J | T ;

N : [0-9] ;

fragment A : 'A' ;
fragment K : 'K' ;
fragment Q : 'Q' ;
fragment J : 'J' ;
fragment T : 'T' ;

SPACE
	 : [ \t]+
	 ;

NEWLINE
	 : [\r\n]+
	 ;

