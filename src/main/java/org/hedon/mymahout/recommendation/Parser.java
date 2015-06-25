package org.hedon.mymahout.recommendation;

import java.io.*;
import java.util.*;

public class Parser {
	
	public static final String PARSED_OUTPUT = "parsed.csv";
	
	public static void parse(String fileName, TreeMap<Long, String> idVisitor, TreeMap<Long, String> idVin) {
		File file = new File(fileName);
		int visitorCount = 0;
		int vinCount = 0;
		Map<String, Long> visitorId = new TreeMap<String, Long>();
		Map<String, Long> vinId = new TreeMap<String, Long>();
		try {
			PrintWriter writer = new PrintWriter(PARSED_OUTPUT, "UTF-8");
			Scanner input = new Scanner(file);
			while (input.hasNextLine()) {
				String lineInput = input.nextLine();
				String[] inputPair = lineInput.split(",");
				if (visitorId.containsKey(inputPair[0])) {
					writer.print(visitorId.get(inputPair[0]) + ",");
				} else {
					visitorCount++;
					writer.print(visitorCount + ",");
					visitorId.put(inputPair[0], (long) visitorCount);
					idVisitor.put((long) visitorCount, inputPair[0]);
				}
				if (vinId.containsKey(inputPair[1])) {
					writer.println(vinId.get(inputPair[1]));
				} else {
					vinCount++;
					writer.println(vinCount);
					vinId.put(inputPair[1], (long) vinCount);
					idVin.put((long) vinCount, inputPair[1]);
				}
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
