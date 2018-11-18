package pl.sda.intermediate.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GenericHWTest {
    @AllArgsConstructor
    private class TestClass {
        private Integer number;
        private String name;

        @Override
        public String toString() {
            return "TestClass{" + number +
                    ": " + name + '}';
        }
    }
    @Getter
    private class Pair<T, E>{
        private T firstValue;
        private E secondValue;

        public Pair (T var1, E var2){
            this.firstValue = var1;
            this.secondValue = var2;
        }

        public boolean equals(Pair pair) {
            if (pair == null) {
                return false;
            }
            return firstValue.equals(pair.firstValue) &&
                    secondValue.equals(pair.secondValue);
        }
    }

    public static <T> void printGenericList(List<T> list){
        for (T t : list) {
            System.out.print(t + ", ");
        }
        System.out.println();
    }

    public static <T extends Number> double printSum(List<T> list){
        return list.stream().mapToDouble(d -> d.doubleValue()).sum();
    }

//    public static <T, S extends Number> double printSum(List<T> list, T parameter){
//        return list.stream().filter(l -> l < (T)parameter).mapToDouble(d -> d.doubleValue()).sum();
//    }

    @Test
    public void shouldPrintAllElements() {
        List<String> listString = Arrays.asList("Jan", "Adam", "Piotr");
        List<Integer> listInteger = Arrays.asList(1, 2, 3);
        List<TestClass> listTestClass = Arrays.asList(new TestClass(1, "pierwszy"), new TestClass(2, "drugi"));
        printGenericList(listString);
        printGenericList(listInteger);
        printGenericList(listTestClass);
    }

    @Test
    public void shouldAddValues(){
        List<Integer> listInteger = Arrays.asList(1, 2, 3);
        List<Double> listDouble = Arrays.asList(1.0, 2.3, 4.5);
        System.out.println(printSum(listInteger));
        System.out.println(printSum(listDouble));
    }
}
