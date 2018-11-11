package pl.sda.intermediate.others;

import lombok.Getter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ProteinChecker {
    @Getter
    private List<String> proteinList;

    private ProteinChecker() {
        proteinList = initializeProteinList();
//        for (String s : proteinList) {
//            System.out.println(s);
//        }
    }

    private List<String> initializeProteinList() {
        try {
            List<String> strings = Files.readAllLines(Paths.get(
                    this.getClass().getClassLoader().getResource("dane.txt").toURI()), Charset.forName("UNICODE"));
            return strings;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean changePossible(String s1, String s2){
        if (s1.length() != s2.length()){
            return false;
        } else {
            char[] ch1 = s1.toCharArray();
            char[] ch2 = s2.toCharArray();
            Arrays.sort(ch1);
            Arrays.sort(ch2);
            return Arrays.equals(ch1, ch2);
        }
    }

    private void printResult(){
        for(int index = 0; index < proteinList.size() - 1; index++) {
            System.out.println(index + "-" + (index + 1) + ": " + changePossible(proteinList.get(index), proteinList.get(index + 1)));
            index++;
        }
    }

    public static void main(String[] args) {
        ProteinChecker proteinChecker = new ProteinChecker();
        proteinChecker.printResult();
    }
}
