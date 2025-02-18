package helpers;

/**
 * Class that stores some common chess sequences to aid in testing 
 */
public class PawnChessSequences {
	
	
	public static final String[] PAWN_TWO_STEPS_FORWARD_FROM_START = {
			"A2:A4" 
	}; 
	
	public static final String[] PAWNS_BLOCK_EACH_OTHER = {
			"A2:A4", "A7:A5"
	}; 
	
	public static final String[] PAWN_CAPTURE_SEQUENCE = {
			"A2:A4", "B7:B5"
	};
	

}
