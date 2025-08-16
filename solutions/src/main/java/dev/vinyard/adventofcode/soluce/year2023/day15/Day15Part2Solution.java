package dev.vinyard.adventofcode.soluce.year2023.day15;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 15, part = 2, description = "Lens Library", link = "https://adventofcode.com/2023/day/15", tags = "unsolved")
public class Day15Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You convince the reindeer to bring you the page; the page confirms that your HASH algorithm is working.</p>
    * <p>The book goes on to describe a series of 256 <b>boxes</b> numbered 0 through 255. The boxes are arranged in a line starting from the point where light enters the facility. The boxes have holes that allow light to pass from one box to the next all the way down the line.</p>
    * <pre>
    *       +-----+  +-----+         +-----+
    * Light | Box |  | Box |   ...   | Box |
    * -----------------------------------------&gt;
    *       |  0  |  |  1  |   ...   | 255 |
    *       +-----+  +-----+         +-----+
    * </pre>
    * <p>Inside each box, there are several <b>lens slots</b> that will keep a lens correctly positioned to focus light passing through the box. The side of each box has a panel that opens to allow you to insert or remove lenses as necessary.</p>
    * <p>Along the wall running parallel to the boxes is a large library containing lenses organized by <b>focal length</b> ranging from 1 through 9. The reindeer also brings you a small handheld <a href="https://en.wikipedia.org/wiki/Label_printer">label printer</a>.</p>
    * <p>The book goes on to explain how to perform each step in the initialization sequence, a process it calls the Holiday ASCII String Helper Manual Arrangement Procedure, or <b>HASHMAP</b> for short.</p>
    * <p>Each step begins with a sequence of letters that indicate the <b>label</b> of the lens on which the step operates. The result of running the HASH algorithm on the label indicates the correct box for that step.</p>
    * <p>The label will be immediately followed by a character that indicates the <b>operation</b> to perform: either an equals sign (=) or a dash (-).</p>
    * <p>If the operation character is a <b>dash</b> (-), go to the relevant box and remove the lens with the given label if it is present in the box. Then, move any remaining lenses as far forward in the box as they can go without changing their order, filling any space made by removing the indicated lens. (If no lens in that box has the given label, nothing happens.)</p>
    * <p>If the operation character is an <b>equals sign</b> (=), it will be followed by a number indicating the <b>focal length</b> of the lens that needs to go into the relevant box; be sure to use the label maker to mark the lens with the label given in the beginning of the step so you can find it later. There are two possible situations:</p>
    * <ul>
    *  <li>If there is already a lens in the box with the same label, <b>replace the old lens</b> with the new lens: remove the old lens and put the new lens in its place, not moving any other lenses in the box.</li>
    *  <li>If there is <b>not</b> already a lens in the box with the same label, add the lens to the box immediately behind any lenses already in the box. Don't move any of the other lenses when you do this. If there aren't any lenses in the box, the new lens goes all the way to the front of the box.</li>
    * </ul>
    * <p>Here is the contents of every box after each step in the example initialization sequence above:</p>
    * <pre>
    * After "rn=1":
    * Box 0: [rn 1]
    * 
    * After "cm-":
    * Box 0: [rn 1]
    * 
    * After "qp=3":
    * Box 0: [rn 1]
    * Box 1: [qp 3]
    * 
    * After "cm=2":
    * Box 0: [rn 1] [cm 2]
    * Box 1: [qp 3]
    * 
    * After "qp-":
    * Box 0: [rn 1] [cm 2]
    * 
    * After "pc=4":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [pc 4]
    * 
    * After "ot=9":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [pc 4] [ot 9]
    * 
    * After "ab=5":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [pc 4] [ot 9] [ab 5]
    * 
    * After "pc-":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [ot 9] [ab 5]
    * 
    * After "pc=6":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [ot 9] [ab 5] [pc 6]
    * 
    * After "ot=7":
    * Box 0: [rn 1] [cm 2]
    * Box 3: [ot 7] [ab 5] [pc 6]
    * </pre>
    * <p>All 256 boxes are always present; only the boxes that contain any lenses are shown here. Within each box, lenses are listed from front to back; each lens is shown as its label and focal length in square brackets.</p>
    * <p>To confirm that all of the lenses are installed correctly, add up the <b>focusing power</b> of all of the lenses. The focusing power of a single lens is the result of multiplying together:</p>
    * <ul>
    *  <li>One plus the box number of the lens in question.</li>
    *  <li>The slot number of the lens within the box: 1 for the first lens, 2 for the second lens, and so on.</li>
    *  <li>The focal length of the lens.</li>
    * </ul>
    * <p>At the end of the above example, the focusing power of each lens is as follows:</p>
    * <ul>
    *  <li>rn: 1 (box 0) * 1 (first slot) * 1 (focal length) = <b>1</b></li>
    *  <li>cm: 1 (box 0) * 2 (second slot) * 2 (focal length) = <b>4</b></li>
    *  <li>ot: 4 (box 3) * 1 (first slot) * 7 (focal length) = <b>28</b></li>
    *  <li>ab: 4 (box 3) * 2 (second slot) * 5 (focal length) = <b>40</b></li>
    *  <li>pc: 4 (box 3) * 3 (third slot) * 6 (focal length) = <b>72</b></li>
    * </ul>
    * <p>So, the above example ends up with a total focusing power of <b>145</b>.</p>
    * <p>With the help of an over-enthusiastic reindeer in a hard hat, follow the initialization sequence. <b>What is the focusing power of the resulting lens configuration?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        root.performOperations();

        return root.getFocusingPower();
    }
}
