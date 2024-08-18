package tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import logic.ChessConstants;
import logic.KingPiece;

public class KingPieceTest implements ChessConstants {
	
	private static KingPiece king; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		king = new KingPiece(WHITE); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		king = null; 
	}

	@Before
	public void setUp() throws Exception {
		king.setIsChecked(false);
		king.setHasMoved(false);
		king.setIsCaptured(false);
	}

	
	@Test
	public void testGetIsChecked() {
		assertEquals(false, king.isChecked()); 
		king.setIsChecked(true);
		assertEquals(true, king.isChecked()); 
	}
	
	@Test
	public void testGetStartingPos() {
		assertEquals(0, king.getPos().row()); 
		assertEquals(E, king.getPos().col()); 
	}
	


}
