package ru.nvy.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import ru.nvy.record.State;
import ru.nvy.record.UnionService;

public class Machine {
	public LinkedHashSet<State> S;// алфавит состояний
	public LinkedHashSet<Character> P;//входной алфавит
	public LinkedHashSet<Character> Z;//алфавит магазинных символов, записываемых на вспомогательную ленту
	public State s0;// начальное состояние
	public Character h0;//маркер дна
	public LinkedHashSet<State> F;//множество конечных состояний.
	public LinkedHashSet<TransitionFunction> Sigma;//функция переходов

	public Machine() {
		S = new LinkedHashSet<>(); // алфавит состояний
		P = new LinkedHashSet<>(); // входной алфавит
		h0 = '_'; // маркер дна, записывается на дно магазина
		Z = new LinkedHashSet<>(); // алфавит магазинных символов, записываемых на вспомогательную ленту
		Z.add(h0);
		s0 = new State("s0"); // начальное состояние
		F = new LinkedHashSet<>(); // множество конечных состояний
		F.add(s0);
		Sigma = new LinkedHashSet<>(); // функция переходов
	}

	public void addTransitionFunctions(HashSet<TransitionFunction> transitionFunctions) {
		for (TransitionFunction tf : transitionFunctions) {
			Sigma.add(tf);
			S.add(tf.currentState);
			S.add(tf.nextState);
			int i;
			for (i = 0; i < tf.chain.length(); i++) {
				Z.add(tf.chain.charAt(i));
			}
			P.add(tf.symbolP);
		}
	}

	public boolean TrySolve(String inputStr) {
		State currentState = s0;
		Queue<UnionService> chooses = new LinkedList<>();

		Stack<Character> memory = new Stack<>();
		memory.push(h0);

		Stack<Character> reverseMemory = new Stack<>();
		reverseMemory.addAll(reverse(memory));

		State defaultState = new State("s0");
		TransitionFunction firstFunc = new TransitionFunction(defaultState, defaultState, '`', '_', "E_");
		UnionService union = new UnionService(0, firstFunc, currentState, reverseMemory);
		chooses.add(union);

		for (int i = 0; i < inputStr.length(); i++) {
			Character item;
			UnionService unionService = chooses.poll();
			if (unionService==null) {
				break;
			}

			i = unionService.number();
			TransitionFunction transitionFunction = unionService.transitionFunction();
			memory = new Stack<>();
			memory.addAll(reverse(unionService.stack()));
			currentState = transitionFunction.nextState;
			memory.pop();

			if (transitionFunction.chain.length() != 1 || transitionFunction.chain.charAt(0) != '`') {
				for (int j = transitionFunction.chain.length() - 1; j >= 0; j--) {
					char item2 = transitionFunction.chain.charAt(j);
					memory.push(item2);
				}
			}

			if (transitionFunction.symbolP == '`' || (i == inputStr.length() - 1 && memory.peek() != '_')) {
				i--;
			}

			if (memory.size() != 0) {
				if (i + 1 < inputStr.length()) {
					item = inputStr.charAt(i + 1);
				} else {
					continue;
				}
				ArrayList<TransitionFunction> functions = new ArrayList<>();
				for (TransitionFunction tf : Sigma) {
					if ((tf.symbolP == item || tf.symbolP == '`') && tf.currentState.equals(currentState)
							&& tf.symbolZ == memory.peek()) {
						functions.add(tf);
					}
				}

				for (int j = 0; j < functions.size(); j++) {
					reverseMemory = new Stack<>();
					reverseMemory.addAll(reverse(memory));
					TransitionFunction func = functions.stream().skip(j).findFirst().get();
					chooses.add(new UnionService(i + 1, func, currentState, reverseMemory));
				}

				if (i == inputStr.length() - 1 && memory.peek() == '_') {
					break;
				}
			}
		}

		if (!F.contains(currentState) || memory.size() == 0 || memory.peek() != '_') {
			return false;
		}
		return true;
	}

	// region Utils
	public static Stack<Character> reverse(Stack<Character> arr) {
		Stack<Character> extraStack = new Stack<Character>();
		int n = arr.size();
		Stack<Character> extraStack2= new Stack<Character>();
		extraStack2.addAll(arr);
		for (int i = 0; i < n; i++) {
			Character element = extraStack2.peek();
			extraStack2.pop();
			extraStack.push(element);
		}
		return extraStack;
	}

	public static void toStringCharacter(HashSet<Character> characters) {
		int count = 0;
		for (Character character : characters) {
			count++;
			if (count == characters.size()) {
				System.out.print(character);
				count = 0;
			} else {
				System.out.print(character + ", ");
			}
		}
		System.out.print("}\n");
	}

	public static void toStringState(HashSet<State> states) {
		int count = 0;
		for (State state : states) {
			count++;
			if (count == states.size()) {
				System.out.print(state.name());
				count = 0;
			} else {
				System.out.print(state.name() + ", ");
			}
		}
		System.out.print("}\n");
	}
	// endregion
}