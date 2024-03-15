package com.foxminded.SchoolSpringJdbc.service;

public interface UserInputOutput {
	void print(String s);

	String read();

	Integer readInt();

	default void println(String s) {
		print(s);
		print("\n");
	}

	void destroy();
}
