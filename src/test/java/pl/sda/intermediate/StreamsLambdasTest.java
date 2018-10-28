package pl.sda.intermediate;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamsLambdasTest {
    private List<PersonForTest> people = Arrays.asList(
            new PersonForTest(1, "Anna", "Nowak", 33),
            new PersonForTest(2, "Beata", "Kowalska", 22),
            new PersonForTest(3, "Marek", "Nowak", 25),
            new PersonForTest(4, "Adam", "Twardowski", 33),
            new PersonForTest(5, "Monika", "Kos", 25),
            new PersonForTest(6, "Adam", "Rudy", 45),
            new PersonForTest(7, "Marek", "Rudy", 15)
    );

    private List<String> animals = Arrays.asList("cat", " dog ", "  ", "mouse", null, "rat", "pig", "rabbit", "  hamster", "parrot");

    @Test
    public void lambdas1() {
        int counter = 0;
        Comparator<String> comparator = (a, b) -> String.valueOf(a.charAt(1)).compareTo(String.valueOf(b.charAt(1)));
        List<String> list = animals.stream().filter(a -> a != null && !a.trim().isEmpty()).map(a -> a.trim()).sorted(comparator).collect(Collectors.toList());
        for (String animal : list) {
            counter++;
            if (animal != null && !animal.trim().isEmpty()) {
                System.out.print(animal.trim() + (counter < list.size() ? ", " : "."));
            }
        }

        System.out.println();

        System.out.println(animals.stream()
                // .filter(animal -> animal != null && !animal.trim().isEmpty())
                .filter(animal -> StringUtils.isNotBlank(animal))
                .map(animal -> animal.trim())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining(", ", "", ".")));

        System.out.println(people.stream()
                .filter(p -> p.getName().startsWith("A"))
                .map(p -> p.getName().toUpperCase())
                .distinct()
                .sorted((a, b) -> String.valueOf(a.charAt(a.length() - 1)).compareTo(String.valueOf(b.charAt(b.length() - 1))))
                .collect(Collectors.joining(", ", "", ".")));

        System.out.println(people.stream()
                .sorted((a, b) -> a.getAge().compareTo(b.getAge()))
                .map(p -> (p.getName() + " " + p.getLastName()))
                .collect(Collectors.joining(", ", "", ".")));

        System.out.println(people.stream()
                .collect(Collectors.groupingBy(p -> p.getAge())));

        Map<Integer, PersonForTest> collect = people.stream()
                .collect(Collectors.toMap(e -> e.getIndex(), v -> v));
        Function<PersonForTest, String> getAgewithEMark = a -> a.getAge() + "!";
        Function<PersonForTest, String> anonymous = new Function<PersonForTest, String>() {
            @Override
            public String apply(PersonForTest personForTest) {
                return personForTest.getAge() + "!";
            }
        };
    }

    @Getter

    private class PersonForTest {
        private final Integer index;
        private final String name;
        private final String lastName;
        private final Integer age;

        public PersonForTest(Integer index, String name, String lastName, Integer age) {
            this.index = index;
            this.name = name;
            this.lastName = lastName;
            this.age = age;
        }

        @Override
        public String toString() {
            return index + ": " + name + " " + lastName + ", age " + age;
        }
    }

    @FunctionalInterface
    public interface SuperChecker<T> {
        boolean check(T number);
    }

    private SuperChecker<Integer> checkIfOddLambda = a -> a % 2 != 0;
    private SuperChecker<Integer> checkIfOddAnonymous = new SuperChecker<Integer>() {
        @Override
        public boolean check(Integer number) {
            return number % 2 != 0;
        }
    };
    private SuperChecker<Integer> checkIfLessThan5 = a -> a < 5;
    private SuperChecker getCheckIfLessThan5Anonymous = new SuperChecker<Integer>() {
        @Override
        public boolean check(Integer number) {
            return number < 5;
        }
    };

    @Test
    public void checker() {
        System.out.println(checkIfOddLambda.check(5));
        System.out.println(checkIfOddAnonymous.check(6));
    }

    @FunctionalInterface
    public interface MathOperation {
        Integer calculate(Integer num1, Integer num2);
    }

    private MathOperation add = (a, b) -> a + b;
    private MathOperation subtract = (a, b) -> a - b;
    private MathOperation multiply = (a, b) -> a * b;
    @Test
    public void math(){
        System.out.println(add.calculate(2, 3));
        System.out.println(subtract.calculate(2, 3));
        System.out.println(multiply.calculate(2, 3));
    }
}
