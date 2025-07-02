package helpers;

public class ChessSequences {
	
	public static final String[] STATE1 = {
			"E2:E4",  // White pawn advances
		    "E7:E5",  // Black pawn advances
		    "G1:F3",  // White knight develops
		    "B8:C6",  // Black knight develops
		    "F1:B5",  // White bishop pins knight
		    "A7:A6",  // Black pawn challenges bishop
		    "B5:A4",  // White bishop retreats
		    "G8:F6",  // Black knight develops
		    "E1:G1",  // White castles kingside
		    "E8:G8",  // Black castles kingside
		    "D2:D4",  // White central pawn push
		    "D7:D6",  // Black central pawn push
		    "C2:C3",  // White supports center
		    "C7:C6",  // Black supports center
		    "D1:E2",  // White queen moves
		    "C8:E6",  // Black bishop develops
		    "A1:E1",  // White rook moves to the center
		    "F8:E7",  // Black bishop develops
		    "H1:E1",  // White rook centralizes
		    "H8:E8"   // Black rook centralizes
		};
	
	public static final String[] STATE2 = {
			"E2:E4",
			"E7:E5",
			"D1:H5",
			"A7:A5",
			
	};
	
	/**
	 * Fools mate chess sequence
	 */
	public static final String[] STATE3 = {
		    "F2:F3",  // White makes a weak pawn move
		    "E7:E5",  // Black opens the center
		    "G2:G4",  // White weakens their kingside further
		    "D8:H4"   // Black's queen delivers checkmate
		};
	
	public static final String[] STATE4 = {
			"D2:D4", 
            "C1:F4",  
            "G1:F3",  
            "E2:E3", 
            "C2:C3",
            "D1:D3"
	}; 
	
	public static final String[] STATE5 = {
			"D8:D5",
			"E8:B5",
			"D2:D4",
			"D1:D2"
	}; 

}
