package pl.sda.intermediate;

public enum Countries {
    USA("Stany Zjednoczone", "US"), //singleton
    POLAND("Polska", "PL"),
    UKRAINE("Ukraina", "UA"),
    FRANCE("Francja", "FR");

    private String name;
    private String symbol;

    Countries(String name, String symbol) {  //Fixme
        this.name = name;
        this.symbol = symbol;
    }
}
