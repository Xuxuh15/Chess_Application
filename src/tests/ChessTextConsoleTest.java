package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import helpers.Pair;
import logic.ChessBoard;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.ChessConstants;
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
	
	@Test
	@DisplayName("selectPiece input out of bounds throws ArrayIndexOutOfBoundsException")
	public void selectPiece_InputOutOfBounds_ThrowsArrayIndexOutOfBoundsException() {
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
			game.selectPiece(new Pair(9,9)); 
		}, "Expected ArrayIndexOutofBoundsException to be thrown"); 
	}
	
	@Test
	@DisplayName("selectPiece input out empty square returns null")
	public void selectPiece_EmptySquare_ReturnsNull() throws ArrayIndexOutOfBoundsException {
		ChessPiece selectedPiece = game.selectPiece(new Pair(ChessConstants.D,5)); 
		assertNull(selectedPiece); 
	}
	
	@Test
	@DisplayName("selectPiece valid input returns correct Chess Piece")
	public void selectPiece_ValidInput_ReturnsCorrectChessPiece() {
		ChessPiece selectedPiece = game.selectPiece(new Pair(ChessConstants.B,1)); 
		assertNotNull(selectedPiece); 
		assertEquals(ChessConstants.WHITE, selectedPiece.getColor(), "Expected chess pieceto be white"); 
		assertEquals(ChessConstants.PAWN, selectedPiece.getRank(), "Expected chess piece to be PAWN rank"); 
	}
	

	
}
