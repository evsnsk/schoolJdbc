package com.foxminded.SchoolSpringJdbc.service.impl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.SchoolSpringJdbc.service.UserInputOutput;

@Component
public class UserInputOutputImpl implements UserInputOutput {
	private final Scanner scanner;
	private final PrintStream out;

	@Autowired
	public UserInputOutputImpl() {
		this(System.in, System.out);
	}

	public UserInputOutputImpl(InputStream in, PrintStream out) {
		scanner = new Scanner(in);
		this.out = out;
	}

	@Override
	public void print(String s) {
		out.print(s);
	}

	@Override
	public String read() {
		return scanner.nextLine();
	}

	@Override
	public Integer readInt() {
		return scanner.nextInt();
	}

	@Override
	public void destroy() {
		scanner.close();
		out.close();
	}

}
