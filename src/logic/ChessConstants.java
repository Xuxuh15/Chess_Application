package logic;

public interface ChessConstants {
	
	final static int ROWS = 8; 
	final static int COLUMNS = 8; 
	final static int PAWN = 0; 
	final static int KNIGHT = 1; 
	final static int BISHOP = 2;
	final static int ROOK =  3; 
	final static int QUEEN = 4;
	final static int KING = 5; 
	final static int A = 0; 
	final static int B = 1;
	final static int C = 2; 
	final static int D = 3; 
	final static int E = 4; 
	final static int F = 5; 
	final static int G = 6; 
	final static int H = 7;
	
	final static int NUMPIECES = 16; 
	
	final static int numPawns = 8; 
	final static int numServants = 2; 
	final static int numRoyalty = 1; 
	
	
	final static int STARTWHITE = 1;
	final static int STARTBLACK = 6; 
	final static int UNIT_VECTOR_WHITE = 1; 
	final static int UNIT_VECTOR_BLACK = -1; 
	
	//index of the king in chess piece array
	final static int INDEXKING = 12; 

	
	
	final static char WHITE = 'W'; 
	final static char BLACK = 'B'; 
	final static String[] LAYOUT = {"white_castle", "white_knight", "white_bishop", "white_queen", 
			"white_king", "white_bishop", "white_knight", "white_castle"};  
}
