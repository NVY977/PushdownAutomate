package ru.nvy.machine;
import ru.nvy.record.State;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;

public class TransitionFunction implements Comparable<TransitionFunction> {
	public State currentState;
	public State nextState;
	public Character symbolP;
	public Character symbolZ;
	public String chain;

	public TransitionFunction(State currentState, State nextState, Character symbolP, Character symbolZ, String chain) {
		this.currentState = currentState;
		this.nextState = nextState;
		this.symbolP = symbolP;
		this.symbolZ = symbolZ;
		this.chain = chain;
	}

	public static HashSet<TransitionFunction> TryParse(String inputStr) {
		State startState = new State("s0");

		LinkedHashSet<TransitionFunction> transitionFunction;
		if (inputStr.isEmpty()) {
			throw new IllegalArgumentException();
		}

		inputStr = inputStr.replace(" ", "");

		int i = 0;
		if (!Character.isUpperCase(inputStr.charAt(i))) {
			throw new IllegalArgumentException();
		}
		char symbolZ = inputStr.charAt(i);
		i++;

		if (inputStr.charAt(i) != '>') {
			throw new IllegalArgumentException();
		}
		i++;
		int j = i;

		transitionFunction = new LinkedHashSet<>();
		while (i < inputStr.length()) {
			StringBuilder term = new StringBuilder();
			while (i < inputStr.length() && inputStr.charAt(i) != '|') {
				term.append(inputStr.charAt(i));
				i++;
			}
			TransitionFunction func = new TransitionFunction(startState, startState, '`', symbolZ, term.toString());
			transitionFunction.add(func);
			i++;
		}

		if (transitionFunction.size() == 0) {
			throw new IllegalArgumentException();
		}

		while (j < inputStr.length()) {
			if (!Character.isUpperCase(inputStr.charAt(j)) && inputStr.charAt(j) != '|') {
				TransitionFunction func = new TransitionFunction(startState, startState, inputStr.charAt(j),
						inputStr.charAt(j), "`");
				transitionFunction.add(func);
			}
			j++;
		}

		TransitionFunction f = new TransitionFunction(startState, startState, '`', '_', "`");
		transitionFunction.add(f);
		return transitionFunction;
	}

	public String toString() {
		return "(" + currentState.toString() + "," + symbolP + "," + symbolZ + ") = (" +
				nextState.toString() + "," + chain + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransitionFunction other = (TransitionFunction) obj;
		return Objects.equals(currentState, other.currentState) && Objects.equals(nextState, other.nextState)
				&& Objects.equals(symbolZ, other.symbolZ) && Objects.equals(symbolP, other.symbolP)
				&& Objects.equals(chain, other.chain);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentState, nextState, symbolP, symbolZ, chain);
	}

	@Override
	public int compareTo(TransitionFunction o) {
		int rez = currentState.compareTo(o.currentState);
		if (rez == 0)
			rez = nextState.compareTo(o.nextState);
		if (rez == 0)
			rez = symbolP.compareTo(o.symbolP);
		if (rez == 0)
			rez = symbolZ.compareTo(o.symbolZ);
		if (rez == 0)
			rez = chain.compareTo(o.chain);
		return rez;
	}
}
