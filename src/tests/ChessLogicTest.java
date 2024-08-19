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
	private static ChessPiece[] whitePieces; 
	private static ChessPiece[] blackPieces; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		board = new ChessBoard(); 
		logic = new ChessLogic(); 
		whitePieces = new ChessPiece[16]; 
		blackPieces = new ChessPiece[16]; 
		logic.generatePieces(blackPieces, BLACK);
		logic.generatePieces(whitePieces, WHITE);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		board = null; 
		logic = null; 
		 
	}

	@Before
	public void setUp() throws Exception {
		logic.setUpBoard(board, whitePieces, blackPieces);
		
	}

	@After
	public void tearDown() throws Exception {
		board.resetBoard();
	}
	
	@Test
	public void testVerifyMovePawn() {
		
		board.resetBoard();
		
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
	
	@Test
	public void testVerifyMoveKnight() {
		
		
		ChessPiece knight = board.checkSpace(0, B); 
		
		//knight at (B,1) moving to square (C,3) //L movement; vertically first -- valid
		assertEquals(true, logic.verifyMoveKnight(board, knight, new Pair(2,C))); 
		
		logic.moveAndUpdate(board, knight, new Pair(2,C)); //update the board
		
		
		//knight at (C,3) moving to occupied square (of ally) (D,1) -- invalid
		assertEquals(false,logic.verifyMoveKnight(board, knight, new Pair(0,D))); 
		
		// knight at (C,3) moving to square (A,4) //L movement; horizontal first -- valid 
		assertEquals(true, logic.verifyMoveKnight(board, knight, new Pair(3,A))); 
		
		logic.moveAndUpdate(board, knight, new Pair(3,A)); //update the board
		
		
		//knight at (A,4) moving to square (A,5) //backwards movement -- invalid
		assertEquals(false, logic.verifyMoveKnight(board, knight, new Pair(2,A))); 
		
		//knight at (A,4) moving to square (A,5) //forwards movement -- invalid
		assertEquals(false, logic.verifyMoveKnight(board, knight, new Pair(4,A))); 
		
		//knight at (A,4) moving out of bounds to (A-1,6) -- invalid
		assertEquals(false, logic.verifyMoveKnight(board, knight, new Pair(6,0))); 
		
		logic.moveAndUpdate(board, knight, new Pair(5,B)); //update the board
		
		//test capture of enemies rook -- valid
		assertEquals(true, logic.verifyMoveKnight(board, knight, new Pair(7,A))); 
		
		
	}

	

}
