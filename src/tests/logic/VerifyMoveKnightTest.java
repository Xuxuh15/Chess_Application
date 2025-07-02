package tests.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import helpers.ChessEmulator;
import helpers.KnightChessSequences;
import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.VerifyMoveKnight;

public class VerifyMoveKnightTest {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMoveKnight verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = ChessLogic.generatePieces(ChessConstants.WHITE); 
		blackPieces = ChessLogic.generatePieces(ChessConstants.BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMoveKnight(); 
		
		
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
	@DisplayName("verifyMoveKnight knight moves more than 2 spaces vertically returns false")
	public void verifyMoveKnight_MovesMoreThanTwoSpacesVertically_ReturnsFalse() { 
		
		ChessPiece knightToMove = logic.peek(board, new Pair(0,ChessConstants.G)); 
		
		assertFalse(verification.verifyMove(board, knightToMove, new Pair(3,ChessConstants.F)), "Expected false to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMoveKnight knight moves more than 2 spaces horizontally returns false")
	public void verifyMoveKnight_MovesMoreThanTwoSpacesHorizontally_ReturnsFalse() {
		
		emulator.applySequence(KnightChessSequences.DEVELOP_WHITE_KNIGHT, board);
		
		ChessPiece knightToMove = logic.peek(board, new Pair(2,ChessConstants.F)); 
		
		assertFalse(verification.verifyMove(board, knightToMove, new Pair(3,ChessConstants.C)), "Expected false to be returned"); 
		
		
	}
	
	@Test
	@DisplayName("verifyMoveKnight knight does not move in L-seqeunce returns false")
	public void verifyMoveKnight_DoesNotMoveInL_ReturnsFalse() {
		
		ChessPiece knightToMove = logic.peek(board, new Pair(0,ChessConstants.G)); 
		
		assertFalse(verification.verifyMove(board, knightToMove, new Pair(3,ChessConstants.G)), "Expected false to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyKnight knight moves in L-Seqeunce returns true")
	public void verifyMoveKnight_MovesInLSeqeunce_ReturnsTrue() {
		ChessPiece knightToMove = logic.peek(board, new Pair(0,ChessConstants.G)); 
		
		assertEquals(true, verification.verifyMove(board, knightToMove, new Pair(2, ChessConstants.F)), 
				"Expected true to be returned"); 
	}
	
	
	
	
	
	
	
	

}
