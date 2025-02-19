package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
import logic.VerifyMoveRook;

public class VerifyMoveRookTest {
	
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMoveRook verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(ChessConstants.WHITE); 
		blackPieces = logic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMoveRook(); 
		
		
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
	@DisplayName("verifyMoveRook rook moves diagonally throws IllegalArgumentException")
	public void verifyMoveRook_MovesDiagonally_ThrowsIllegalArgumentException() {
		
		
		emulator.applySequence(ChessSequences.STATE1, board);
		ChessPiece rookToMove = logic.peek(board, new Pair(0,ChessConstants.E)); 
		
		assertThrows(IllegalArgumentException.class,()->{
			 verification.verifyMove(board, rookToMove, new Pair(1,ChessConstants.D)); 
		}, "Expected IllegalArgumentException to be thrown"); 
		
			
	}
	
	@Test
	@DisplayName("verifyMoveRook rook tries to jump piece on path throws IllegalArgumentException")
	public void verifyMoveRook_TriesToJumpPieceOnPath_ThrowsIllegalArgumentException() {
		
		
		emulator.applySequence(ChessSequences.STATE1, board);
		ChessPiece rookToMove = logic.peek(board, new Pair(7,ChessConstants.A)); 
		
		assertThrows(IllegalArgumentException.class,()->{
			 verification.verifyMove(board, rookToMove, new Pair(7,ChessConstants.F)); 
		}, "Expected IllegalArgumentException to be thrown"); 
		
			
	}
	
	@Test
	@DisplayName("verifyMoveRook valid move returns true")
	public void verifyMoveRook_ValidMove_ReturnsTrue() {
		
		
		emulator.applySequence(ChessSequences.STATE1, board);
		ChessPiece rookToMove = logic.peek(board, new Pair(7,ChessConstants.A)); 
		
		assertTrue(verification.verifyMove(board, rookToMove, new Pair(7,ChessConstants.C)), "Expected true to be returned"); 
			
	}
	
	
	
	

}
