package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessPiece;

public class ChessBoardTest implements ChessConstants {
	
	private static ChessBoard board; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		board = null; 
	}

	@BeforeEach
	public void setUp() throws Exception {
		board.resetBoard();
	}
	
	@Test
	public void testConstructor() {
		assertNotEquals(null, board.getBoard()); 
	}
	
	@Test 
	@DisplayName("checkSpace square outside board throws ArrayIndexOutOfBoundsException")
	public void checkSpace_SquareOutOfBounds_ThrowsArrayIndexOutOfBoundsException() {
		//throws an exception
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{board.checkSpace(8, A);}, "Expected ArrayIndexOutOfBoundsException to be thrown"); 
		
		
	}
	
	@Test
	@DisplayName("checkSpace empty square returns null")
	public void checkSpace_EmptySquare_ReturnsNull() {
		assertEquals(null, board.checkSpace(1, E)); 
	}
	
	@Test
	@DisplayName("checkSpace occupied square returns chess piece")
	public void checkSpace_OccupiedSquare_ReturnsChessPiece() {
		ChessPiece p = new ChessPiece(PAWN, BLACK); 
		board.getBoard()[0][A] = p; 
		assertEquals(p, board.checkSpace(0, A)); 
	}
	
	
	@Test
	@DisplayName("move moves chess piece and updates chess piece state successfully")
	public void move_UpdatesBoardAndChessPieceFlag() {
		ChessPiece p = new ChessPiece(PAWN, BLACK); 
		ChessPiece q = new ChessPiece(QUEEN, WHITE); 
		
		p.setPos(0, A);
		q.setPos(7, A);
		
		board.getBoard()[0][A] = p; 
		board.getBoard()[7][A] = q; 
		board.move(q.getPos(), p.getPos());
		assertEquals(q, board.checkSpace(0, A)); 
		assertEquals(null, board.checkSpace(7, 0));
		assertEquals(true, p.getIsCaptured()); 
		 
	}
	
	@Test
	@DisplayName("resetBoard resets the chess board")
	public void resetBoard_ResetsBoard() {
		ChessPiece p = new ChessPiece(PAWN, BLACK); 
		ChessPiece q = new ChessPiece(QUEEN, WHITE); 
		
		p.setPos(0, A);
		q.setPos(7, A);
		
		board.getBoard()[0][A] = p; 
		board.getBoard()[7][A] = q; 
		board.resetBoard();
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				assertEquals(null, board.checkSpace(i, j)); 
			}
		}
	}
	

	

}
