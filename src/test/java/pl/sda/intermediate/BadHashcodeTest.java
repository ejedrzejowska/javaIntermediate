package pl.sda.intermediate;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BadHashcodeTest {
    @AllArgsConstructor
    private class Car{
        private String vin;
        private String model;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Car car = (Car) o;
            return Objects.equals(vin, car.vin) &&
                    Objects.equals(model, car.model);
        }

        @Override
        public int hashCode() {
            //return new Random().nextInt(30); jedno auto wpada jako wiele instancji w secie
            //return 2; performance spada
            return vin.hashCode();
        }
    }
    @RepeatedTest(100)
    public void checkTwoObjectsWithBadHashcode(){
        Set<Car> carSet = new HashSet<>();
        carSet.add(new Car("123123123",  "Audi"));
        carSet.add(new Car("123123123",  "Audi"));
        carSet.add(new Car("123123123",  "Audi"));
        carSet.add(new Car("123123123",  "Audi"));

        System.out.println("Rozmiar seta: " + carSet.size());
    }
}
