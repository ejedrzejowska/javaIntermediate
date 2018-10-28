package pl.sda.intermediate;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProteinsChecker {
    @Test
    public void parseAndCheck() {
        try {
            Scanner scanner = new Scanner(new File("src\\test\\resources\\dane.txt"));
            while (scanner.hasNextLine()) {
                String oddLine = scanner.nextLine();
                String evenLine = scanner.nextLine();
                List<Integer> oddList = oddLine.chars().sorted().mapToObj(a->Integer.valueOf(a)).collect(Collectors.toList());
                List<Integer> evenList = evenLine.chars().mapToObj(a->Integer.valueOf(a)).collect(Collectors.toList());
                System.out.println(oddList.equals(evenList));
//                char[] ch1 = oddLine.toCharArray();
//                char[] ch2 = evenLine.toCharArray();
//                Arrays.sort(ch1);
//                Arrays.sort(ch2);
//                System.out.println(Arrays.equals(ch1, ch2));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
