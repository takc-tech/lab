package com.takc_tech;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.takc_tech.bean.Item;

public class Main {

	public static void main(String args[]) throws ReflectiveOperationException, IOException {

		List<String> lines = Files.readAllLines(Path.of("./sample.dat"));

		var mapper = new Mapper<Item>(Item.class);

		for (String line : lines) {
			System.out.println(mapper.generate(line));
		}

	}

}
