package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import helpers.Pair;
import logic.ChessConstants;
import logic.ChessPiece;

public class ChessPieceTest implements ChessConstants {
	
	private static ChessPiece pW; 
	private static ChessPiece pB; 
	
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pW = new ChessPiece(PAWN,WHITE); 
		pB = new ChessPiece(ROOK, BLACK); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		pW = null; 
		pB = null; 
	}

	@Before
	public void setUp() throws Exception {
		pW.setHasMoved(false);
		pW.setIsCaptured(false);
		pW.setPos(0, A);
		
		pB.setHasMoved(false);
		pB.setIsCaptured(false);
		pB.setPos(0, A);
	}


	
	@Test
	public void testGetPos() {
		Pair pos = pW.getPos(); 
		assertEquals(0,pos.row()); 
		assertEquals(A,pos.col()); 
		
	}
	
	@Test
	public void testSetPos() {
		pW.setPos(2, C); 
		assertEquals(2,pW.getPos().row()); 
		assertEquals(C,pW.getPos().col()); 
	}
	
	@Test
	public void testToString() {
		assertEquals("Pawn", pW.toString()); 
		assertEquals("Rook", pB.toString()); 
	}
	
	@Test
	public void testGetToken() {
		assertEquals('\u2659', pW.getToken()); 
		assertEquals('\u2656', pB.getToken()); 
	}
	
	@Test
	public void testGetRank() {
		assertEquals(PAWN, pW.getRank()); 
		pW.setRank(QUEEN);
		assertEquals(QUEEN, pW.getRank());
	}
	
	@Test
	public void testGetValue() {
		assertEquals(1, pW.getValue()); 
		assertEquals(5,pB.getValue()); 
		pW.setValue(0); 
		assertEquals(0,pW.getValue()); 
	}
	
	@Test
	public void testHasMoved() {
		assertFalse(pW.hasMoved()); 
		pW.setHasMoved(true);
		assertTrue(pW.hasMoved()); 
	}
	
	@Test
	public void testConsoleDisplay() {
		//checks to see if console displays unicode characters
		System.out.println("Testing if console supports unicode"); 
		System.out.println(pW.getToken()); 
	}
	
	

}
