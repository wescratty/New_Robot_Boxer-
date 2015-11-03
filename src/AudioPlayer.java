/**
 * Created by wescratty on 11/1/15.
 */

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


// Please transition to the API's in AudioComponent.h.

public class AudioPlayer implements LineListener {
    private static AudioPlayer ourInstance = new AudioPlayer();

    public static AudioPlayer getInstance() {
        return ourInstance;
    }

    private AudioPlayer() {
    }


    boolean playCompleted;
    public void playSound(String audioFilePath){

        File audioFile = new File(audioFilePath);

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            AudioFormat format = audioStream.getFormat();

            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.addLineListener(this);

            audioClip.open(audioStream);

            audioClip.start();

            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();
            playCompleted = false;

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }


    public void punchSound(){
        playSound("/Users/wescratty/GitHub/javaRepos/javaRepo/New_Robot_Boxer/punch.wav");
    }

    public void blockSound(){
        playSound("/Users/wescratty/GitHub/javaRepos/javaRepo/New_Robot_Boxer/block.wav");
    }

    public void bellSound(){
        playSound("/Users/wescratty/GitHub/javaRepos/javaRepo/New_Robot_Boxer/endRound.wav");
    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");

        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }

    }
}
