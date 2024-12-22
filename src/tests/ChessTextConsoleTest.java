package tests;




import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;



import logic.ChessBoard;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.ChessConstants;
import ui.ChessTextConsoleGame;

public class ChessTextConsoleTest {


	private static ChessTextConsoleGame game; 
	private static ChessBoard board = new ChessBoard(); 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		game = new ChessTextConsoleGame(board); 
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		game = null; 
		
		 
	}

	@Before
	public void setUp() throws Exception {
		game.setUpGame();
		
	}
	
	
	@Test
	@DisplayName("setUpGame resets game parameters to default and sets up the game board")
	public void setUpGame_resetsGameParameters() {
		game.setUpGame();
		
		assertEquals(false, game.getCheckMate(), "Expected game.checkMate to be false"); 
		assertEquals(ChessConstants.WHITE, game.getCurrentPlayer(), "Expected game.currentPlayer to be equal to ChessConstants.WHITE"); 
		assertEquals(false, game.getHasMoved(), "Expected game.hasMoved to be false"); 
		
	}
	
	

	
	
	
	
	
}
