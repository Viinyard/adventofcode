parser grammar SolutionParser;

options {
    tokenVocab = SolutionLexer;
    language = Java;
}

@header {
import java.util.Stack;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicInteger;
}

root returns [List<Integer> out]
    : program EOF {
        $out = $program.out;
    }
    ;

program returns [List<Integer> out]
    @init {
        AtomicInteger registerA = new AtomicInteger();
        AtomicInteger registerB = new AtomicInteger();
        AtomicInteger registerC = new AtomicInteger();
        AtomicInteger ptr = new AtomicInteger(0);
        $out = new ArrayList<>();
    }
    :
    REGISTER_A INT {
        registerA.set(Integer.parseInt($INT.text));
    }
    REGISTER_B INT {
        registerB.set(Integer.parseInt($INT.text));
    }
    REGISTER_C INT {
        registerC.set(Integer.parseInt($INT.text));
    }
    PROGRAM
    (instructions+=instruction[registerA, registerB, registerC, ptr, $out])+ {
        List<Runnable> executors = $instructions.stream().map(i -> i.out).toList();

        do {
            executors.get(ptr.get()).run();
        } while (ptr.get() < executors.size());
    }
    ;

instruction[AtomicInteger registerA, AtomicInteger registerB, AtomicInteger registerC, AtomicInteger ptr, List<Integer> output] returns [Runnable out]
    : opcode[registerA, registerB, registerC, ptr, output] operand[registerA, registerB, registerC] {
        $out = () -> $opcode.out.accept($operand.out);
    }
    ;

operand[AtomicInteger registerA, AtomicInteger registerB, AtomicInteger registerC] returns [Supplier<Integer> out]
    :
    LITERAL {
        $out = () -> Integer.parseInt($LITERAL.text);
    }
    | OPERAND_A {
        $out = () -> registerA.get();
    }
    | OPERAND_B {
        $out = () -> registerB.get();
    }
    | OPERAND_C {
        $out = () -> registerC.get();
    }
    | OPERAND_RESERVED {
        // reserved
    }
    | INT_LITERAL {
        $out = () -> Integer.parseInt($INT_LITERAL.text);
    }
    ;

opcode[AtomicInteger registerA, AtomicInteger registerB, AtomicInteger registerC, AtomicInteger ptr, List<Integer> output] returns [Consumer<Supplier<Integer>> out]
    @init {
        Consumer<Supplier<Integer>> incPtr = (a) -> ptr.incrementAndGet();
    }
    :
    OPCODE_ADV {
        Consumer<Supplier<Integer>> adv = (a) -> {
            registerA.set((int) (registerA.get() >> a.get()));
        };
        $out = adv.andThen(incPtr);
    }
    | OPCODE_BXL {
        Consumer<Supplier<Integer>> bxl = (a) -> {
            registerB.set((int) (registerB.get() ^ a.get()));
        };
        $out = bxl.andThen(incPtr);
    }
    | OPCODE_BST {
        Consumer<Supplier<Integer>> bst = (a) -> {
            registerB.set(a.get() % 8);
        };
        $out = bst.andThen(incPtr);
    }
    | OPCODE_JNZ {
        $out = (a) -> {
            if (registerA.get() != 0) {
                ptr.set(a.get());
            } else {
                incPtr.accept(a);
            }
        };
    }
    | OPCODE_BXC {
        Consumer<Supplier<Integer>> bxc = (a) -> {
            registerB.set(registerB.get() ^ registerC.get());
        };
        $out = bxc.andThen(incPtr);
    }
    | OPCODE_OUT {
        Consumer<Supplier<Integer>> out = (a) -> {
            output.add(a.get() % 8);
        };
        $out = out.andThen(incPtr);
    }
    | OPCODE_BDV {
        Consumer<Supplier<Integer>> bdv = (a) -> {
            registerB.set((int) (registerA.get() >> a.get()));
        };
        $out = bdv.andThen(incPtr);
    }
    | OPCODE_CDV {
        Consumer<Supplier<Integer>> cdv = (a) -> {
            registerC.set((int) (registerA.get() >> a.get()));
        };
        $out = cdv.andThen(incPtr);
    }
    ;

