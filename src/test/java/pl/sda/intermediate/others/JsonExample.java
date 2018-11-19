package pl.sda.intermediate.others;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsonExample {
    @Test
    public void serializeToJson() {
        SomeObject someObject = new SomeObject();
        someObject.setName("Jason");
        someObject.setAge(33);
        someObject.setSalary(BigDecimal.valueOf(1000.2));
        OtherObject otherObject = new OtherObject();
        otherObject.setId(1);
        otherObject.setText("text");
        OtherObject otherObject2 = new OtherObject();
        otherObject.setId(2);
        otherObject.setText("text2");
        List<OtherObject> list = new ArrayList<>();
        list.add(otherObject2);
        list.add(otherObject);
        someObject.setOtherObjectList(list);

        Gson gson = new Gson();
        String json = gson.toJson(someObject);
        System.out.println(json);
        try {
            FileWriter writer = new FileWriter("c:/projects/someObject.txt");
            gson.toJson(someObject, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deserializeFromJson() {
        Gson gson = new Gson();
        try {
            SomeObject fromJson = gson.fromJson(new FileReader("c:/projects/someObject.txt"), SomeObject.class);
            System.out.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    private class SomeObject {
        private String name;
        private Integer age;
        private BigDecimal salary;
        private List<OtherObject> otherObjectList;
    }

    @Getter
    @Setter
    private class OtherObject {
        private Integer id;
        private String text;
    }

    @Test
    public void table() {
        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/A/last?format=json");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String inputLine;
            String result = "";
            while ((inputLine = bufferedReader.readLine()) != null){
                result = result + inputLine;
            }
            bufferedReader.close();
            Gson gson = new Gson();
            RatesWrapper[] ratesWrappers = gson.fromJson(result, RatesWrapper[].class);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    private class RatesWrapper {
        private String table;
        private String no;
        private String effectiveDate;
        private List<Rate> rates;
    }

    @Getter
    @Setter
    private class Rate {
        private String currency;
        private String code;
        private double mid;

    }
}
