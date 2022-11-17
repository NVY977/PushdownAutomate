package ru.nvy.record;

import java.util.Stack;

import ru.nvy.machine.TransitionFunction;

public record UnionService(int number, TransitionFunction transitionFunction, State state, Stack<Character> stack) { }
