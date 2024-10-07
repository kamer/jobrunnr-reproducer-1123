package dev.kamer;

import jakarta.inject.Singleton;

/**
 * Created on October, 2024
 *
 * @author kamer
 */
@Singleton
public class SomeService implements SomeApi {

	public void doSomething(String str1, String str2) {
		System.out.println("str1: " + str1 + ", str2: " + str2);
	}

}
