package simulator;

/**
 * The Simulator class is used to load and execute all of the instructions in a machine code program.
 * A sample machine code program is provided which outputs the characters 9 to 1 to the console.
 *  
 * @author mmuppa
 * @author acfowler
 * @version 1.2
 */
public class Simulator {

	public static void main(String[] args) {

		Computer myComputer;

		/************************************** */
		/** The next variable - "program" */
		/** allows someone using the simulator (such as a grader) */
		/** to decide what program will be simulated. */
		/** The simulation must load and execute */
		/** instructions found in the "program" array. */
		/**
		/** Recompile your program without further changes */
		/** and see the simulator load and execute the new program. */
		/************************************** */
		
		/*
		 * To run the machine code program shown below in the actual LC3 simulator we need to append the following
		 * to the top of the program:
		 * 
		 * "0011000000000000", // Use address x3000 as the start location in memory for the program
		 */

		String program[] = {	
			"0010000000001000",  // LD into R0 x39 which is ASCII 9
			"0010001000001000",  // LD into R1 x-30
			"0001010000000001",  // ADD R2 <- R0 + R1 ; #9 ; R2 is the counter
			"0000010000000100",  // BR if zero skip down to code after the loop
			"1111000000100001",  // TRAP - vector x21 - OUT R0
			"0001000000111111",  // ADD - decrement R0 - the character
			"0001010010111111",  // ADD - decrement R2 - the counter
			"0000111111111011",  // BR - Loop back
			"1111000000100101",  // TRAP - vector x25 - HALT
			"0000000000111001",  // x39
			"1111111111010000"}; // x-30

		/*
		 * This is the assembly program version of the binary
		 * program shown above. 
		 * 		 .ORIG x3000
		 * 
		 * 		 LD R0 START 
		 * 		 LD R1 END 
		 *  	 ADD R2 R0 R1 
		 * TOP   BRZ DONE 
		 * 		 OUT 
		 * 		 ADD R0 R0 -1
		 *       ADD R2 R2 -1
		 * 		 BRNZP TOP 
		 * DONE  HALT
		 * 
		 * START .FILL x39 
		 * END   .FILL x-30
		 * 
		 * 		 .END
		 */
		
		
//		// Here is another program for testing:
//		String program[] = {
//		//A program that uses a loop to add 1 + 2 + 3 + 4 + 5 and save the result (15) in RO
//				"0101000000100000",   //; clear R0 - R0 will be the SUM
//				"0101001001100000",   //; clear R1 - R1 will be the loop counter
//				"0001001001100101",   //; R1 <- R1 + 5 : set the loop counter to 5 - result = 15
//				"0001000000000001",   //; R0 <- R0 + R1 ; add the loop counter value to the sum
//				"0001001001111111",	  //; decrement the counter
//				"0000001111111101",   //; BRnzp do it again if the counter is not yet zero
//				"1111000000100101"};  //; halt - TRAP with vector x25
		
		
		
		
		
		myComputer = new Computer();
		
		/* Show the initial configuration of the computer. */
		//myComputer.display();
		
		myComputer.loadMachineCode(program);

		/* Execute the program. */
		/* During execution, the only output to the screen should be */
		/* the result of executing OUT. */

		myComputer.execute();
		
		/* Show the final configuration of the computer. */
		//System.out.println();
		//myComputer.display();
	}

}
