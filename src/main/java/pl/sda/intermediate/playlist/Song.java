package pl.sda.intermediate.playlist;

public class Song implements Media {
    private String title;
    public Song(String title){
        this.title = title;
    }
    @Override
    public void play() {
        System.out.println("Song: " + title);
    }
}
