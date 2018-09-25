package org.hc.learning.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process exec = null;
		try {
			runtime.exec("cmd.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		String nextLine = sc.nextLine();
		exec = runtime.exec(nextLine);
		InputStream in = exec.getInputStream();
		
		LineNumberReader input = new LineNumberReader(new InputStreamReader(in));
		String line;
		while ((line = input.readLine()) != null) {
		System.out.println(line);
		}
		in.close();
		exec.destroy();
		runtime.freeMemory();
		sc.close();
	}
}
