package tests.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessPiece;
import ui.ChessTextConsoleGame;

public class ChessTextConsoleTest {


	private static ChessTextConsoleGame game; 
	private static ChessBoard board = new ChessBoard(); 
	
	

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		game = new ChessTextConsoleGame(board); 
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		game = null; 
		
		 
	}

	@BeforeEach
	public void setUp() throws Exception {
		game.setUpGame();
		
	}
	
	
	@Test
	@DisplayName("setUpGame resets game parameters to default and sets up the game board")
	public void setUpGame_ResetsGameParameters() {
		game.setUpGame();
		
		assertEquals(false, game.getCheckMate(), "Expected game.checkMate to be false"); 
		assertEquals(ChessConstants.WHITE, game.getCurrentPlayer(), "Expected game.currentPlayer to be equal to ChessConstants.WHITE"); 
		assertEquals(false, game.getHasMoved(), "Expected game.hasMoved to be false"); 
		
		
	}
	
	@Test
	@DisplayName("setUpGame successfully generates game pieces")
	public void setUpGame_SuccessfullyGeneratesGamePieces() {
		game.setUpGame();
		
		ChessPiece[] whitePieces = game.getWhiteChessPieces(); 
		
		ChessPiece[] blackPieces = game.getBlackChessPieces(); 
		
		assertNotNull(whitePieces); 
		assertNotNull(blackPieces); 
		
		
	}
	
	
	
	
	

	
	
	
	
}
