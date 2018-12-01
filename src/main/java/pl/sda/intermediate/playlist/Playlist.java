package pl.sda.intermediate.playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist implements Media {
    private List<Media> playlist1 = new ArrayList<>();
    private PlaybackOption pbOption = PlaybackOption.SEQUENTIAL;
    private int loopOption = 2;

    public void add(Media media) {
        playlist1.add(media);
    }

    public void setPlaybackOption(PlaybackOption pbOption) {
        this.pbOption = pbOption;
    }

    public void setLoopOption(int loopOption) {
        if (loopOption > 5 || loopOption < 1) {
            System.out.println("Value not allowed! Default loop set to 2.");
        } else {
            this.loopOption = loopOption;
        }
    }

    public void play() {
        switch (pbOption) {
            case SEQUENTIAL:
                for (Media media : playlist1) {
                    media.play();
                }
                break;
            case LOOP:
                for (int i = 0; i < loopOption; i++) {
                    System.out.println("LOOP: " + i);
                    for (Media media : playlist1) {
                        media.play();
                    }
                }
                break;
            case SHUFFLE:
                List<Media> shuffledList = new ArrayList<>(playlist1);
                Collections.shuffle(shuffledList);
                for (Media media : shuffledList) {
                    media.play();
                }
                break;
        }
    }
}
