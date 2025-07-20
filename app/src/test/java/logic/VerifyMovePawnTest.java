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
import helpers.Pair;
import helpers.PawnChessSequences;
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

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = ChessLogic.generatePieces(WHITE); 
		blackPieces = ChessLogic.generatePieces(BLACK);
		logic.setBlackPlayerChessArray(blackPieces);
		logic.setWhitePlayerChessArray(whitePieces);
		emulator = new ChessEmulator(logic);
		verification = new VerifyMovePawn(); 
		
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
		 
	}

	@BeforeEach
	public void setUp() throws Exception {
		logic = new ChessLogic();
		whitePieces = ChessLogic.generatePieces(WHITE); 
		blackPieces = ChessLogic.generatePieces(BLACK);
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
	@DisplayName("verifyMovePawn pawn move backwards returns false")
	public void verifyMovePawn_PawnMovesBackwards_ReturnsFalse() {
		
		emulator.applySequence(PawnChessSequences.PAWN_TWO_STEPS_FORWARD_FROM_START, board); 
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertFalse(verification.verifyMove(board, pieceToMove, new Pair(2,A)), "Expected false to be returned"); 
		
	
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn moves forward two spaces from start returns true")
	public void verifyMovePawn_TwoSquaresForwardFromStart_ReturnsTrue() {
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(1,A)); 
		
		assertTrue(verification.verifyMove(board, pieceToMove, new Pair(3,A)), "Expected true but returned false"); 
		
		
	}
	

	@Test
	@DisplayName("verifyMovePawn pawn moves forward two spaces returns false")
	public void verifyMovePawn_TwoSquaresForward_ReturnsFalse() {
		emulator.applySequence(PawnChessSequences.PAWN_TWO_STEPS_FORWARD_FROM_START, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertFalse(verification.verifyMove(board, pieceToMove, new Pair(5,A)), "Expected false to be returned"); 
		
		
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn tries to move forward into occupied square returns false")
	public void verifyMovePawn_PawnTriesToMoveForwardIntoOccupiedSquare_ReturnsFalse() {

		emulator.applySequence(PawnChessSequences.PAWNS_BLOCK_EACH_OTHER, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertFalse(verification.verifyMove(board, pieceToMove, new Pair(4,A)), "Expected false to be returned"); 
		
		
		
	}
	
	@Test
	@DisplayName("verifyMovePawn pawn captures piece diagonally returns true")
	public void verifyMovePawn_PawnMovesDiagonalToCapturePiece_ReturnsTrue() {

		emulator.applySequence(PawnChessSequences.PAWN_CAPTURE_SEQUENCE, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(3,A)); 
		
		assertTrue(verification.verifyMove(board, pieceToMove, new Pair(4,B)), "Expected true to be returned"); 
	}
	

	

}
