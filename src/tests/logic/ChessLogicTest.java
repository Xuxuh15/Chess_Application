package tests.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import logic.KingPiece;
import logic.VerificationStrategy;
import logic.VerifyMoveKnight;

public class ChessLogicTest {
	
	
	private static ChessBoard board; 
	private static ChessLogic logic; 
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 
	private static ChessEmulator emulator; 
	private static VerificationStrategy verification; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = logic.generatePieces(ChessConstants.WHITE); 
		blackPieces = logic.generatePieces(ChessConstants.BLACK);
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
	@DisplayName("checkmate king in check returns false")
	public void checkmate_KingInCheck_ReturnsFalse() {
		
		emulator.applySequence(ChessSequences.STATE2, board);
		
		ChessPiece pieceToMove = logic.peek(board, new Pair(4,ChessConstants.H)); 
		logic.moveAndUpdate(board, pieceToMove, new Pair(7,ChessConstants.F));
		KingPiece king = (KingPiece) logic.peek(board, new Pair(7, ChessConstants.E)); 
		
		assertFalse(logic.checkmate(board, blackPieces, king)); 
		
	}
	
	@Test
	@DisplayName("checkmtae checkmate returns true")
	public void checkmate_ValidCheckmate_ReturnsTrue() {
		emulator.applySequence(ChessSequences.STATE3, board);
		KingPiece king = (KingPiece) logic.peek(board, new Pair(0, ChessConstants.E)); 
		
		assertTrue(logic.checkmate(board, blackPieces, king)); 
	}
	
	@Test
	@DisplayName("convertLetterToNum null value returns -1")
	public void convertLetterToNum_NullValue_ReturnsNegativeOne() {
		assertEquals(-1,logic.convertLettertoNum(null), "Expected -1 to be returned"); 
	}
	
	@Test
	@DisplayName("convertLetterToNum invalid letter returns -1")
	public void convertLetterToNum_InvalidLetter_ReturnsNegativeOne() {
		assertEquals(-1,logic.convertLettertoNum("z"), "Expected -1 to be returned"); 
	}
	
	@Test
	@DisplayName("convertLetterToNum valid letter returns integer equivalent")
	public void convertLetterToNum_ValidLetter_ReturnsIntegerEquivalent() {
		assertEquals(ChessConstants.E,logic.convertLettertoNum("E"),String.format("Expected %d to be returned",
				ChessConstants.E)); 
	}
	
	@Test
	@DisplayName("verifyMove invalid move returns false")
	public void verifyMove_InvalidMove_ReturnsFalse() {
		ChessPiece pawn = logic.peek(board, new Pair(1,ChessLogic.A)); 
		assertFalse(logic.verifyMove(board, pawn, new Pair(4,ChessLogic.A)), "Expected false to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove invalid move returns false")
	public void verifyMove_NullParameter_ReturnsFalse() {
		assertFalse(logic.verifyMove(board, null, new Pair(4,ChessLogic.A)), "Expected false to be returned"); 
		
	}
	
	@Test
	@DisplayName("verifyMove valid move returns true")
	public void verifyMove_ValidMove_ReturnsTrue() {
		ChessPiece knight = logic.peek(board, new Pair(0,ChessConstants.B)); 
		assertFalse(logic.verifyMove(board, null, new Pair(3,ChessLogic.C)), "Expected true to be returned"); 
		
	}
	
	@Test
	@DisplayName("isEmpty empty square returns true")
	public void isEmpty_EmptySquare_ReturnsTrue() {
		boolean isEmpty = logic.isEmpty(board, 5, ChessConstants.A); 
		assertTrue(isEmpty, "Expected isEmpty to be true"); 
	}
	
	@Test
	@DisplayName("isEmpty occupied square returns false")
	public void isEmpty_EmptySquare_ReturnsFalse() {
		boolean isEmpty = logic.isEmpty(board, 0, ChessConstants.A); 
		assertFalse(isEmpty, "Expected isEmpty to be false"); 
	}
	
	@Test
	@DisplayName("isEmpty square out of bounds throws ArrayIndexOutOfBoundsException")
	public void isEmpty_SquareOutOfBounds_ReturnsFalse() {
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
			 logic.isEmpty(board, 10, ChessConstants.A);
		}, "Expected ArrayIndexOutOfBoundsException to be thrown"); 
	}
	
	@Test
	@DisplayName("isCapturable capturable square returns true")
	public void isCapturable_CapturableSquare_ReturnsTrue() {
		emulator.applySequence(ChessSequences.STATE1, board);
		
		ChessPiece p = logic.peek(board, new Pair(3, ChessConstants.A)); 
		boolean isCapturable = logic.isCapturable(board, p, new Pair(5, ChessConstants.C)); 
		assertTrue(isCapturable, "Expected isCapturable to be true"); 
	}
	
	@Test
	@DisplayName("isCapturable uncapturable square returns false")
	public void isCapturable_UncapturableSquare_ReturnsFalse() {
		emulator.applySequence(ChessSequences.STATE1, board);
		
		ChessPiece p = logic.peek(board, new Pair(1, ChessConstants.E)); 
		boolean isCapturable = logic.isCapturable(board, p, new Pair(0, ChessConstants.E)); 
		assertFalse(isCapturable, "Expected isCapturable to be false"); 
	}
	
	@Test
	@DisplayName("isCapturable same color returns false")
	public void isCapturable_SameColor_ReturnsFalse() {
		
		ChessPiece p = logic.peek(board, new Pair(0, ChessConstants.E)); 
		boolean isCapturable = logic.isCapturable(board, p, new Pair(1, ChessConstants.E)); 
		assertFalse(isCapturable, "Expected isCapturable to be false"); 
	}
	
	@Test
	@DisplayName("isCapturable square out of bounds throws ArrayIndexOutOfBoundsException")
	public void isCapturable_SquareOutOfBounds_ThrowsArrayIndexOutOfBoundsException() {
		
		ChessPiece p = logic.peek(board, new Pair(1, ChessConstants.A)); 
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
			logic.isCapturable(board, p, new Pair(-1, ChessConstants.A)); 
		}, "Expected ArrayIndexOutOfBoundsException to be thrown"); 
	}
	
	@Test
	@DisplayName("moveAndUpdate moves and updates chess piece")
	public void moveAndUpdate_MovesAndUpdatesChessPiece() {
		
		ChessPiece pawn = logic.peek(board, new Pair(1, ChessConstants.A)); 
		logic.moveAndUpdate(board, pawn, new Pair(2, ChessConstants.A));
		
		assertEquals(pawn, logic.peek(board, new Pair(2, ChessConstants.A)), "Expected true to be returned"); 
		assertTrue(pawn.hasMoved(), "Expected hasMoved() to return true"); 
		Pair newPos = pawn.getPos(); 
		assertTrue(pawn.getPos().equals(newPos), "Expected newPos to equal pawn.getPos()"); 
		
		
	}
	
	@Test
	@DisplayName("generatePieces populates ChessPiece array")
	public void generatePieces_PopulatesChessPieceArray() {
		ChessPiece[] arr = logic.generatePieces(ChessConstants.BLACK); 
		
		assertNotNull(arr, "Expected arr not to be null"); 
		
		for(int i = 0; i < arr.length; i++) {
			assertNotNull(arr[i], "Expected a ChessPiece object"); 
			assertTrue(arr[i].getColor() == ChessConstants.BLACK); 
		}
		
	}
	
	@Test
	@DisplayName("peek occupied square returns ChessPiece")
	public void peek_OccupiedSquare_ReturnsChessPiece() {
		ChessPiece pawn = logic.peek(board, new Pair(1,ChessConstants.A)); 
		assertNotNull(pawn, "Expected ChessPiece object to be returned"); 
	}
	
	@Test
	@DisplayName("peek empty square returns null")
	public void peek_EmptySquare_ReturnsNull() {
		ChessPiece pawn = logic.peek(board, new Pair(2,ChessConstants.A)); 
		assertEquals(null, pawn, "Expected null to be returned");  
	}
	
	
	@Test
	@DisplayName("peek square out of bounds throws ArrayIndexOutOfBoundsException")
	public void peek_SquareOutOfBounds_ThrowsArrayIndexOutOfBoundsException() {
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{
			logic.peek(board,new Pair(-1,ChessConstants.B)); 
		}, "Expected ArrayIndexOutOfBoundsException to be thrown"); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
