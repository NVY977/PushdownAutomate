package ru.nvy.service;

import ru.nvy.machine.Machine;
import ru.nvy.machine.TransitionFunction;

import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class GeneralService {

    public static void runApp(String PATH) {
        List<String> fileText;
        try {
            fileText = FileInput.readFile(PATH);
        } catch (NoSuchFileException exception) {
            throw new RuntimeException(exception);
        }

        Machine machine = new Machine();

        for (String element : fileText) {
            HashSet<TransitionFunction> transitionFunctions = TransitionFunction.TryParse(element);
            machine.addTransitionFunctions(transitionFunctions);
        }

        System.out.print("P (input alphabet): {");
        Machine.toStringCharacter(machine.P);

        System.out.print("Z (extra alphabet): {");
        Machine.toStringCharacter(machine.Z);

        System.out.print("S (state alphabet): {");
        Machine.toStringState(machine.S);

        System.out.print("F (final alphabet): {");
        Machine.toStringState(machine.F);

        System.out.println("\nList of rules: ");
        for (TransitionFunction transitionFunction : machine.Sigma) {
            System.out.println(transitionFunction.toString());
        }
        String inputStr;
        do {
            System.out.println("Input string: ");
            Scanner scan = new Scanner(System.in);
            inputStr = scan.next();
            if (inputStr.length() != 0) {
                System.out.println("Result: " + machine.TrySolve(inputStr) + "\n============");
            }
        }
        while (inputStr.length() != 0);
    }
}
