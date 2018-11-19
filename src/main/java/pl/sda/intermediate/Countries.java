package pl.sda.intermediate;

public enum Countries {
    USA("Stany Zjednoczone", "US", "imperial"), //singleton
    POLAND("Polska", "PL", "metric"),
    UKRAINE("Ukraina", "UA", "metric"),
    FRANCE("Francja", "FR", "metric");

    private String name;
    private String symbol;
    private String units;

    Countries(String name, String symbol, String units) {
        this.name = name;
        this.symbol = symbol;
        this.units = units;
    }

    public String getName(){
        return name;
    }

    public String getSymbol(){
        return symbol;
    }

    public String getUnits(){
        return units;
    }

    public String getLanguage(){
        return symbol.equals("US")?"en":symbol.toLowerCase();
    }
}
