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
import logic.VerifyMoveKing;


public class VerifyMoveKingTest {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMoveKing verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(ChessConstants.WHITE); 
		blackPieces = logic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = null; 
		
		
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
		verification = new VerifyMoveKing(blackPieces); 
		
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
		
		emulator.applySequence(ChessSequences.STATE4, board);
		ChessPiece king = whitePieces[ChessConstants.INDEXKING]; 
		assertFalse(verification.verifyMove(board, king, new Pair(0,ChessConstants.G)),
				"Expected false to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove valid diagonal move returns true")
	public void verifyMove_ValidDiaginal_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		ChessPiece king = whitePieces[ChessConstants.INDEXKING]; 
		assertTrue(verification.verifyMove(board, king, new Pair(1,ChessConstants.D)), 
				"Expected true to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove valid horizontal move returns true")
	public void verifyMove_ValidHorizontal_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		ChessPiece king = whitePieces[ChessConstants.INDEXKING]; 
		assertTrue(verification.verifyMove(board, king, new Pair(0,ChessConstants.F)),
				"Expected true to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove valid vertical move returns true")
	public void verifyMove_ValidVertical_ReturnsTrue() {
		
		emulator.applySequence(ChessSequences.STATE4, board);
		ChessPiece king = whitePieces[ChessConstants.INDEXKING]; 
		assertTrue(verification.verifyMove(board, king, new Pair(1,ChessConstants.E))
				,"Expected true to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove king moves into check returns false")
	public void verifyMove_KingMovesIntoCheck_ReturnsFalse() {
		
		emulator.applySequence(ChessSequences.STATE5, board);
		ChessPiece king = whitePieces[ChessConstants.INDEXKING]; 
		assertFalse(verification.verifyMove(board, king, new Pair(2,ChessConstants.E))
				,"Expected true to be returned"); 
		
	}
	
	
	
	
	
	
	
	

}
