package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import helpers.ChessEmulator;
import helpers.ChessSequences;
import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.VerifyMoveQueen;

public class VerifyMoveQueenTest {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMoveQueen verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(ChessConstants.WHITE); 
		blackPieces = logic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMoveQueen(); 
		
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
		 
	}

	@BeforeEach
	public void setUp() throws Exception {
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(ChessConstants.WHITE); 
		blackPieces = logic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		logic.setUpBoard(board);
		emulator.setLogic(logic);
		
	}

	@AfterEach
	public void tearDown() throws Exception {
		board.resetBoard();
		emulator.setLogic(null);
		whitePieces = null; 
		blackPieces= null; 
	}
	
	@Test
	@DisplayName("verifyMove invalid move returns false")
	public void verifyMove_InvalidMove_ReturnsFalse() {
		
		ChessPiece queen = logic.peek(board, new Pair(0,ChessConstants.E)); 
		assertFalse(verification.verifyMove(board, queen, new Pair(3, ChessConstants.E)), "Expected false to be returned"); 
	
	}
	
	@Test
	@DisplayName("verifyMove valid diagonal movement returns true")
	public void verifyMove_ValidDiagonal_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		
		ChessPiece queen = logic.peek(board, new Pair(0, ChessConstants.D)); 
		assertTrue(verification.verifyMove(board, queen, new Pair(1, ChessConstants.E)), "Expected true to be returned"); 
	}
	
	@Test
	@DisplayName("verifyMove valid vertical movement returns true")
	public void verifyMove_ValidVertical_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		
		ChessPiece queen = logic.peek(board, new Pair(0, ChessConstants.D)); 
		assertTrue(verification.verifyMove(board, queen, new Pair(2, ChessConstants.D)), "Expected true to be returned"); 
	}
	
	@Test
	@DisplayName("verifyMove valid horizontal movement returns true")
	public void verifyMove_ValidGHorizontal_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		ChessPiece queen = logic.peek(board, new Pair(0, ChessConstants.D)); 
		logic.moveAndUpdate(board, queen, new Pair(1, ChessConstants.D));
		assertTrue(verification.verifyMove(board, queen, new Pair(1, ChessConstants.E)), "Expected true to be returned"); 
	}
	
	
	


}
