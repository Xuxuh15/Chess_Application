package tests.logic;

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
import logic.VerifyMoveBishop;

public class VerifyMoveBishopTest {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMoveBishop verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = ChessLogic.generatePieces(ChessConstants.WHITE); 
		blackPieces = ChessLogic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMoveBishop(); 
		
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
		 
	}

	@BeforeEach
	public void setUp() throws Exception {
		logic = new ChessLogic(); 
		whitePieces = ChessLogic.generatePieces(ChessConstants.WHITE); 
		blackPieces = ChessLogic.generatePieces(ChessConstants.BLACK);
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
	@DisplayName("verifyMove vertial/horizontal move returns false")
	public void verifyMove_VerticalOrHorizontalMove_ReturnsFalse() {
		
		emulator.applySequence(ChessSequences.STATE1, board); 
		ChessPiece bishop = logic.peek(board, new Pair(5, ChessConstants.E)); 
		
		assertFalse(verification.verifyMove(board, bishop, new Pair(5, ChessConstants.D)), "Expected false"
				+ "to be returned"); 
		assertFalse(verification.verifyMove(board, bishop, new Pair(4, ChessConstants.E)), "Expected false"
				+ "to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove diagonal move returns true")
	public void verifyMove_DiagonalMove_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE1, board); 
		ChessPiece bishop = logic.peek(board, new Pair(5, ChessConstants.E)); 
		
		assertTrue(verification.verifyMove(board, bishop, new Pair(4, ChessConstants.F)), "Expected true"
				+ "to be returned"); 
		assertTrue(verification.verifyMove(board, bishop, new Pair(3, ChessConstants.G)), "Expected true"
				+ "to be returned"); 
		
		
	}
	
	@Test
	@DisplayName("verifyMove chess piece blocking diagonal path returns false")
	public void verifyMove_ObstructionInPath_ReturnsFalse() {
		emulator.applySequence(ChessSequences.STATE1, board); 
		ChessPiece bishop = logic.peek(board, new Pair(5, ChessConstants.E)); 
		

		assertFalse(verification.verifyMove(board, bishop, new Pair(7, ChessConstants.G)), "Expected false"
				+ "to be returned"); 
		
	}
	

	
	
	
	
	
	
	

}
