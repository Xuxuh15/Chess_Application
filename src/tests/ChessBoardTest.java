package tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessPiece;

public class ChessBoardTest implements ChessConstants {
	
	private static ChessBoard board; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		board = null; 
	}

	@Before
	public void setUp() throws Exception {
		board.resetBoard();
	}
	
	@Test
	public void testConstructor() {
		assertNotEquals(null, board.getBoard()); 
	}
	
	@Test (expected = ArrayIndexOutOfBoundsException.class)
	public void testCheckSpace() {
		ChessPiece p = new ChessPiece(PAWN, BLACK); 
		board.getBoard()[0][A] = p; 
		assertEquals(p, board.checkSpace(0, A)); 
		assertEquals(null, board.checkSpace(1, E)); 
		//throws an exception
		board.checkSpace(8, A); 
		
		
	}
	
	@Test
	public void testMove() {
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
	public void testResetBoard() {
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
