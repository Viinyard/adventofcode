package dev.vinyard.adventofcode.soluce.year2024.day17;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 17, part = 1, description = "Chronospatial Computer", link = "https://adventofcode.com/2024/day/17", tags = "unsolved")
public class Day17Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 17: Chronospatial Computer ---</h2>
    * <p>The Historians push the button on their strange device, but this time, you all just feel like you're <a href="/2018/day/6">falling</a>.</p>
    * <p>"Situation critical", the device announces in a familiar voice. "Bootstrapping process failed. Initializing debugger...."</p>
    * <p>The small handheld device suddenly unfolds into an entire computer! The Historians look around nervously before one of them tosses it to you.</p>
    * <p>This seems to be a 3-bit computer: its program is a list of 3-bit numbers (0 through 7), like 0,1,2,3. The computer also has three <b>registers</b> named A, B, and C, but these registers aren't limited to 3 bits and can instead hold any integer.</p>
    * <p>The computer knows <b>eight instructions</b>, each identified by a 3-bit number (called the instruction's <b>opcode</b>). Each instruction also reads the 3-bit number after it as an input; this is called its <b>operand</b>.</p>
    * <p>A number called the <b>instruction pointer</b> identifies the position in the program from which the next opcode will be read; it starts at 0, pointing at the first 3-bit number in the program. Except for jump instructions, the instruction pointer increases by 2 after each instruction is processed (to move past the instruction's opcode and its operand). If the computer tries to read an opcode past the end of the program, it instead <b>halts</b>.</p>
    * <p>So, the program 0,1,2,3 would run the instruction whose opcode is 0 and pass it the operand 1, then run the instruction having opcode 2 and pass it the operand 3, then halt.</p>
    * <p>There are two types of operands; each instruction specifies the type of its operand. The value of a <b>literal operand</b> is the operand itself. For example, the value of the literal operand 7 is the number 7. The value of a <b>combo operand</b> can be found as follows:</p>
    * <ul>
    *  <li>Combo operands 0 through 3 represent literal values 0 through 3.</li>
    *  <li>Combo operand 4 represents the value of register A.</li>
    *  <li>Combo operand 5 represents the value of register B.</li>
    *  <li>Combo operand 6 represents the value of register C.</li>
    *  <li>Combo operand 7 is reserved and will not appear in valid programs.</li>
    * </ul>
    * <p>The eight instructions are as follows:</p>
    * <p>The <b>adv</b> instruction (opcode <b>0</b>) performs <b>division</b>. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's <b>combo</b> operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is <b>truncated</b> to an integer and then written to the A register.</p>
    * <p>The <b>bxl</b> instruction (opcode <b>1</b>) calculates the <a href="https://en.wikipedia.org/wiki/Bitwise_operation#XOR">bitwise XOR</a> of register B and the instruction's <b>literal</b> operand, then stores the result in register B.</p>
    * <p>The <b>bst</b> instruction (opcode <b>2</b>) calculates the value of its <b>combo</b> operand <a href="https://en.wikipedia.org/wiki/Modulo">modulo</a> 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.</p>
    * <p>The <b>jnz</b> instruction (opcode <b>3</b>) does <b>nothing</b> if the A register is 0. However, if the A register is <b>not zero</b>, it <b>jumps</b> by setting the instruction pointer to the value of its <b>literal</b> operand; if this instruction jumps, the instruction pointer is <b>not</b> increased by 2 after this instruction.</p>
    * <p>The <b>bxc</b> instruction (opcode <b>4</b>) calculates the <b>bitwise XOR</b> of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but <b>ignores</b> it.)</p>
    * <p>The <b>out</b> instruction (opcode <b>5</b>) calculates the value of its <b>combo</b> operand modulo 8, then <b>outputs</b> that value. (If a program outputs multiple values, they are separated by commas.)</p>
    * <p>The <b>bdv</b> instruction (opcode <b>6</b>) works exactly like the adv instruction except that the result is stored in the <b>B register</b>. (The numerator is still read from the A register.)</p>
    * <p>The <b>cdv</b> instruction (opcode <b>7</b>) works exactly like the adv instruction except that the result is stored in the <b>C register</b>. (The numerator is still read from the A register.)</p>
    * <p>Here are some examples of instruction operation:</p>
    * <ul>
    *  <li>If register C contains 9, the program 2,6 would set register B to 1.</li>
    *  <li>If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.</li>
    *  <li>If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.</li>
    *  <li>If register B contains 29, the program 1,7 would set register B to 26.</li>
    *  <li>If register B contains 2024 and register C contains 43690, the program 4,0 would set register B to 44354.</li>
    * </ul>
    * <p>The Historians' strange device has finished initializing its debugger and is displaying some <b>information about the program it is trying to run</b> (your puzzle input). For example:</p>
    * <pre>
    * Register A: 729
    * Register B: 0
    * Register C: 0
    * 
    * Program: 0,1,5,4,3,0
    * </pre>
    * <p>Your first task is to <b>determine what the program is trying to output</b>. To do this, initialize the registers to the given values, then run the given program, collecting any output produced by out instructions. (Always join the values produced by out instructions with commas.) After the above program halts, its final output will be <b>4,6,3,5,6,3,5,2,1,0</b>.</p>
    * <p>Using the information provided by the debugger, initialize the registers to the given values, then run the program. Once it halts, <b>what do you get if you use commas to join the values it output into a single string?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
