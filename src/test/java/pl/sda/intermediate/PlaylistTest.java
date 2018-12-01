package pl.sda.intermediate;

import org.junit.jupiter.api.Test;
import pl.sda.intermediate.playlist.*;

public class PlaylistTest {

    @Test
    public void shouldPlayDefault(){
        Media m1 = new Song("pierwsza piosenka");
        Media m2 = new Movie("pierwszy film");
        Media m3 = new Song("druga piosenka");
        Media m4 = new Song("trzecia piosenka");
        Media m5 = new Song("czwarta piosenka");
        Media m6 = new Song("Piosenka z drugiej PL");
        Media m7 = new Movie("Film z drugiej PL");

        Playlist playlist = new Playlist();
        Playlist playlist2 = new Playlist();
        playlist.add(m1);
        playlist.add(m2);
        playlist.add(m3);
        playlist.add(m4);
        playlist.add(m5);
        playlist.setPlaybackOption(PlaybackOption.SHUFFLE);
//        playlist.setLoopOption(3);
        playlist2.add(m6);
        playlist2.add(m7);
        playlist2.add(playlist);
        playlist2.play();
    }
}
