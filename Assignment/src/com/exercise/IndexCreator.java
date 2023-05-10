package com.exercise;

import java.io.*;
import java.util.*;

public class IndexCreator {
	public static void main(String[] args) {

        String file1 = "src/page1.txt";
        String file2 = "src/page2.txt";
        String file3 = "src/page3.txt";
        String indexFile = "src/index.txt";

        try {
            Map<String, Set<Integer>> index = createIndex(file1, file2, file3);
            writeIndexToFile(index, indexFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Set<Integer>> createIndex(String... files) throws IOException {
        Map<String, Set<Integer>> index = new TreeMap<>();

        for (int i = 0; i < files.length; i++) {
            try (BufferedReader reader = new BufferedReader(new FileReader(files[i]))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        if (index.containsKey(word)) {
                            index.get(word).add(i + 1);
                        } else {
                            Set<Integer> pages = new HashSet<>();
                            pages.add(i + 1);
                            index.put(word, pages);
                        }
                    }
                }
            }
        }

        return index;
    }

    private static void writeIndexToFile(Map<String, Set<Integer>> index, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String word : index.keySet()) {
                Set<Integer> pages = index.get(word);
                List<Integer> sortedPages = new ArrayList<>(pages);
                Collections.sort(sortedPages);

                StringBuilder sb = new StringBuilder(word);
                sb.append(" : ");
                for (int i = 0; i < sortedPages.size(); i++) {
                    sb.append(sortedPages.get(i));
                    if (i < sortedPages.size() - 1) {
                        sb.append(",");
                    }
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        }
    }
}


