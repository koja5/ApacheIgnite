package com.test.test;

import java.util.Scanner;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

public class pokreniOstale {
	public static void main(String[] args) throws IgniteException {
		try (Ignite ignite = Ignition.start()) {
			new Scanner(System.in).nextLine();
	    }
	}
}
