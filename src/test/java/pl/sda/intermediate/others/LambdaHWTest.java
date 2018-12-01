package pl.sda.intermediate.others;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class LambdaHWTest {

    private String[] removeWhiteSpaces(String[] collection){
        return Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).toArray(String[]::new);
    }

    private String[] removeWhiteSpacesAndSort(String[] collection){
        return Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).sorted().toArray(String[]::new);
    }

    private String[] removeDuplicates(String[] collection){
        return Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).distinct().sorted().toArray(String[]::new);
    }

    private String[] specialSort(String[] collection){
        return Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).distinct().sorted((a, b) -> a.length() - b.length()).toArray(String[]::new);
    }

    private LinkedHashSet<String> toSet(String[] collection){
        return Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private int quickFind(String[] collection, String find){
        return (int)Arrays.stream(collection).filter(c -> StringUtils.isNotBlank(c)).map(c -> c.trim()).filter(c -> c.equals(find)).count();
    }

    @Test
    public void shouldModifyAnimalCollection(){
        String[] animals = new String[]{"cat", "dog ", "mouse", "rat", "pig", "rabbit", "hamster", " ",
                "parrot", "cat","", "dog", "cat", "  pig", "dog",null};

//        Arrays.stream(removeDuplicates(animals)).forEach(c -> System.out.println(c)); //z joining moze
        System.out.println(quickFind(animals, "pig"));
        System.out.println(toSet(animals));
    }
}
