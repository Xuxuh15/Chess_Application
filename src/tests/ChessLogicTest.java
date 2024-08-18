package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import helpers.Pair;
import logic.ChessBoard;
import logic.ChessConstants;
import logic.ChessLogic;
import logic.ChessPiece;

public class ChessLogicTest implements ChessConstants {
	
	private static ChessBoard board; 
	private static ChessLogic logic; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
	}

	@Before
	public void setUp() throws Exception {
		board.resetBoard();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testVerifyMovePawn() {
		for(int i = 1; i < 2; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				board.getBoard()[i][j] = new ChessPiece(PAWN,WHITE); 
				board.checkSpace(i, j).setPos(i, j);
				board.getBoard()[ROWS-i][j] = new ChessPiece(PAWN, BLACK); 
				board.checkSpace(ROWS-i, j).setPos(ROWS-i, j);
			}
		}
		
		//pawn moving two spaces
		assertEquals(true, logic.verifyMovePawn(board, board.checkSpace(1, A), new Pair(3,A)));
		//illegal capture (empty space)
		assertEquals(false, logic.verifyMovePawn(board, board.checkSpace(1, A), new Pair(2,B)));
		//pawn moving forward single space (legally)
		assertEquals(true, logic.verifyMovePawn(board, board.checkSpace(1, A), new Pair(2,A))); 
		
		//set a black piece in thirs row first column
		board.getBoard()[2][A] = new ChessPiece(PAWN,BLACK); 
		board.getBoard()[2][A].setPos(2, A);
		//pawn moving forward single space (ilegally)
		assertEquals(false, logic.verifyMovePawn(board, board.checkSpace(1, A), new Pair(2,A))); 
		
		//set a black piece in thirs row first column
		board.getBoard()[2][B] = new ChessPiece(PAWN,BLACK); 
		board.getBoard()[2][B].setPos(2, B);
		
		//legal capture
		assertEquals(true, logic.verifyMovePawn(board, board.checkSpace(1, A), new Pair(2,B))); 
		
	}

	

}
