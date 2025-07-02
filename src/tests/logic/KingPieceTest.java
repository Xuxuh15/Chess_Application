package tests.logic;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import logic.ChessConstants;
import logic.KingPiece;

public class KingPieceTest implements ChessConstants {
	
	private static KingPiece king; 

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		king = new KingPiece(WHITE); 
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		king = null; 
	}

	@BeforeEach
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
