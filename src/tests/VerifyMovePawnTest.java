package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import helpers.ChessEmulator;
import helpers.PawnChessSequences;
import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;
import logic.VerifyMovePawn;

public class VerifyMovePawnTest implements ChessConstants {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerifyMovePawn verification; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(WHITE); 
		blackPieces = logic.generatePieces(BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMovePawn(); 
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
		 
	}

	@Before
	public void setUp() throws Exception {
		logic = new ChessLogic();
		whitePieces = logic.generatePieces(WHITE); 
		blackPieces = logic.generatePieces(BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		logic.setUpBoard(board);
		emulator.setLogic(logic);
		
	}

	@After
	public void tearDown() throws Exception {
		board.resetBoard();
		emulator.setLogic(null);
		whitePieces = null; 
		blackPieces= null; 
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn move backwards throws IllegalArgumentException")
	public void verifyMovePawn_PawnMovesBackwards_ThrowsIllegalArgumentException() {
		
		emulator.applySequence(PawnChessSequences.PAWN_TWO_STEPS_FORWARD_FROM_START, board);
		verification = new VerifyMovePawn(); 
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertThrows(IllegalArgumentException.class, ()->{
			verification.verifyMove(board, pieceToMove, new Pair(2,A)); 
			},"Expected IllegalArgumentException to be thrown"); 
		
	
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn moves forward two spaces from start returns true")
	public void verifyMovePawn_TwoSquaresForwardFromStart_ReturnsTrue() {
		
		verification = new VerifyMovePawn(); 
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(1,A)); 
		
		assertTrue(verification.verifyMove(board, pieceToMove, new Pair(3,A)), "Expected true but returned false"); 
		
		
	}
	

	@Test
	@DisplayName("verifyMovePawn pawn moves forward two spaces throws IllegalArgumentException")
	public void verifyMovePawn_TwoSquaresForward_ThrowsIllegalArgumentException() {
		
		verification = new VerifyMovePawn(); 
		emulator.applySequence(PawnChessSequences.PAWN_TWO_STEPS_FORWARD_FROM_START, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertThrows(IllegalArgumentException.class, ()->{
			verification.verifyMove(board, pieceToMove, new Pair(5,A)); 
		}, "Expected IllegalArgumentException to be thrown"); 
		
		
		
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn tries to move forward into occupied square returns IllegalArgumentException")
	public void verifyMovePawn_PawnTriesToMoveForwardIntoOccupiedSquare_ThrowsIllegalArgumentException() {
		
		verification = new VerifyMovePawn(); 
		emulator.applySequence(PawnChessSequences.PAWNS_BLOCK_EACH_OTHER, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertThrows(IllegalArgumentException.class, ()->{
			verification.verifyMove(board, pieceToMove, new Pair(4,A)); 
		}, "Expected IllegalArgumentException to be thrown"); 
		
		
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn captures piece diagonally returns true")
	public void verifyMovePawn_PawnMovesDiagonalToCapturePiece_ReturnsTrue() {
		
		verification = new VerifyMovePawn(); 
		emulator.applySequence(PawnChessSequences.PAWN_CAPTURE_SEQUENCE, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertTrue(verification.verifyMove(board, pieceToMove, new Pair(4,B)), "Expected true to be returned"); 
	}
	
	
	
	
	
	
	
	
	
	
	

}
