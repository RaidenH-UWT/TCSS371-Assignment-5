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
		String[] program = {
			"0001 001 001 1 00011", // ADD R1 + #3
			"0000 001 0000 00001", // BRp + #1
			"0001 001 001 1 01000", // ADD R1 + #8
			"1111 0000 00100101" // HALT
		};

		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(4, myComputer.getRegisters()[1].get2sCompValue());
	}

	/**
	 * Test method for {@link simulator.Computer#executeLoad()}.
	 */
	@Test
	void testExecuteLoad() {
		String[] program = {
			"0010 000 000000001", // LD + #1
			"1111 0000 00100101", // HALT
			"0000 0000 0100 0001" // #65
		};

		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(65, myComputer.getRegisters()[0].get2sCompValue());
	}
	
	/**
	 * Test method for {@link simulator.Computer#executeStore()}.
	 */
	@Test
	void testExecuteStore() {
		String[] program = {
			"0001 001 001 1 00111", // ADD R1 + #7
			"0011 001 000000010", // ST R1 to + #1
			"1111 0000 00100101" // HALT
		};

		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(8, myComputer.getMemory()[4].get2sCompValue());
	}

	/**
	 * Test method for {@link simulator.Computer#executeAnd()}.
	 */
	@Test
	void testExecuteAnd() {
		String[] program = {
			"0001 001 001 1 00110", // ADD R1 + #6
			"0101 001 001 1 11101", // AND R1 * #1
			"1111 0000 00100101" // HALT
		};

		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(5, myComputer.getRegisters()[1].get2sCompValue());
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
			{"0001 000 010100011",  // R0 <- R2 + #3
		     "1111 000000100101"}; // HALT
		
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
			{"0001 000 010 1 11101",  // R0 <- R2 + #-3
		     "1111 0000 00100101"}; // HALT
		
		myComputer.loadMachineCode(program);
		myComputer.execute();

		assertEquals(-1, myComputer.getRegisters()[0].get2sCompValue());
		
		// Check that CC was set correctly
		BitString expectedCC = new BitString();
		expectedCC.setBits("100".toCharArray());
		assertEquals(expectedCC.get2sCompValue(), myComputer.getCC().get2sCompValue());
	}

	/**
	 * This method tests Load as well as both the Trap vectors.
	 * Should print the character 'A' to the output.
	 */
	@Test
	void testLoadTrap() {
		String[] program = {
			"0010 000 000000010", // LD + #2
			"1111 0000 00100001", // OUT 0x21
			"1111 0000 00100101", // HALT 
			"0000 0000 0100 0001" // ASCII 'A'
		};

		myComputer.loadMachineCode(program);
		myComputer.execute();
	}
}
