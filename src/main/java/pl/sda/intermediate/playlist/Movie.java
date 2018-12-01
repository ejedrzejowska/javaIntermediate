package pl.sda.intermediate.playlist;

public class Movie implements Media {
    private String title;

    public Movie(String title){
        this.title = title;
    }
    @Override
    public void play() {
        System.out.println("Movie: " + title);
    }
}
