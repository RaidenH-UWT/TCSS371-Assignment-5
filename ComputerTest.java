/*
 * Unit tests for the Computer class. 
 */

package simulator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Alan Fowler
 * @version 1.3
 */
class ComputerTest {
	
	// An instance of the Computer class to use in the tests.
	private Computer myComputer;

	@BeforeEach
	void setUp() {
		myComputer = new Computer();
	}
	
	
	
	
	/*
	 * NOTE:
	 * Programs in unit tests should ideally have one instruction per line
	 * with a comment for each line.
	 */

	/**
	 * Test method for {@link simulator.Computer#executeBranch()}.
	 */
	@Test
	void testExecuteBranch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link simulator.Computer#executeLoad()}.
	 */
	@Test
	void testExecuteLoad() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link simulator.Computer#executeStore()}.
	 */
	@Test
	void testExecuteStore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link simulator.Computer#executeAnd()}.
	 */
	@Test
	void testExecuteAnd() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link simulator.Computer#executeNot()}.
	 */
	@Test
	void testExecuteNot5() {
	
		//myComputer.display();
		
		// NOTE: R5 contains #5 initially when the Computer is instantiated
		// So, iF we execute R4 <- NOT R5, then R4 should contain 1111 1111 1111 1010    (-6)
		// AND CC should be 100
		
		String program[] = {
			"1001100101111111",    // R4 <- NOT R5
			"1111000000100101"     // TRAP - vector x25 - HALT
		};
		
		myComputer.loadMachineCode(program);
		myComputer.execute();
		
		assertEquals(-6, myComputer.getRegisters()[4].get2sCompValue());
		
		// Check that CC was set correctly
		BitString expectedCC = new BitString();
		expectedCC.setBits("100".toCharArray());
		assertEquals(expectedCC.get2sCompValue(), myComputer.getCC().get2sCompValue());
		
		//myComputer.display();
	}
	
	/**
	 * Test method for {@link simulator.Computer#executeAdd()}. <br>
	 * Computes 2 + 2. R0 <- R2 + R2
	 */
	@Test
	void testExecuteAddR2PlusR2() {
		
		String[] program =
			{"0001000010000010",  // R0 <- R2 + R2 (#4)
		     "1111000000100101"}; // HALT
		
		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(4, myComputer.getRegisters()[0].get2sCompValue());
		
		// Check that CC was set correctly
		BitString expectedCC = new BitString();
		expectedCC.setBits("001".toCharArray());
		assertEquals(expectedCC.get2sCompValue(), myComputer.getCC().get2sCompValue());
	}
	
	/**
	 * Test method for {@link simulator.Computer#executeAdd()}. <br>
	 * Computes 2 + 3. R0 <- R2 + #3
	 */
	@Test
	void testExecuteAddR2PlusImm3() {
		
		String[] program =
			{"0001000010100011",  // R0 <- R2 + #3
		     "1111000000100101"}; // HALT
		
		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(5, myComputer.getRegisters()[0].get2sCompValue());
		
		// Check that CC was set correctly
		BitString expectedCC = new BitString();
		expectedCC.setBits("001".toCharArray());
		assertEquals(expectedCC.get2sCompValue(), myComputer.getCC().get2sCompValue());
	}
	
	/**
	 * Test method for {@link simulator.Computer#executeAdd()}. <br>
	 * Computes 2 - 3. R0 <- R2 + #-3
	 */
	@Test
	void testExecuteAddR2PlusImmNeg3() {
		
		String[] program =
			{"0001000010111101",  // R0 <- R2 + #-3
		     "1111000000100101"}; // HALT
		
		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(-1, myComputer.getRegisters()[0].get2sCompValue());
		
		// Check that CC was set correctly
		BitString expectedCC = new BitString();
		expectedCC.setBits("100".toCharArray());
		assertEquals(expectedCC.get2sCompValue(), myComputer.getCC().get2sCompValue());
	}

}
