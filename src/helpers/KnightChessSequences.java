package helpers;

public class KnightChessSequences {
	
	public static final String[] testSequence = {
		    "e2:e4",  // White pawn opens
		    "e7:e5",  // Black pawn responds
		    "g1:f3",  // White knight develops
		    "b8:c6",  // Black knight develops
		    "d2:d4",  // White fights for center
		    "d7:d6",  // Black stabilizes the center
		    "f3:d4",  // White knight captures in center
		    "c6:d4",  // Black knight trades
		    "d1:d4",  // White queen recaptures
		    "g8:f6",  // Black knight develops
		    "b1:c3",  // White knight develops
		    "f6:e4",  // Black knight jumps into center
		    "c3:e4",  // White knight captures
		    "f8:e7",  // Black develops a bishop
		    "e4:g3",  // White knight moves to test positions
		    "h7:h6",  // Black makes a waiting move
		    "g3:f5",  // White knight moves again for testing
		    "e7:f6"   // Black captures for more knight interactions
		};
	
	public static final String[] DEVELOP_WHITE_KNIGHT = {
			"G1:F3"
	}; 

}
