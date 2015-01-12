package com.aplayer.main;

import java.io.File;
import java.io.IOException;

public class TestMain {
	public static void main(String[] args) throws IOException {
		File file = new File("C:/users/Jun/Desktop/a/d/ss/cs/a.txt");
		System.out.println(file.getParentFile().exists());
//		file.getParentFile().mkdirs();  
//		file.createNewFile();  
	}
}
