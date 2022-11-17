package ru.nvy.record;
import java.util.Objects;

public record State(String name) implements Comparable<State> {
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int compareTo(State currentState) {
		return name.compareTo(currentState.name);
	}
}
