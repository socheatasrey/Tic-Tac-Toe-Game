package util;

import javafx.scene.media.AudioClip;
import java.util.HashMap;

public class SoundUtil {

    private static double volume = 0.5;

    private static boolean isMusicMuted = false;
    private static boolean isSoundMuted = false;

    private static final HashMap<String, AudioClip> loops = new HashMap<>();

    public static void clik(){
        play("click_sound_effect.wav");
    }

    public static void play(String filename) {
        try {
            if (isSoundMuted) return;
            String url = CurrentTempUtil.getResourcePath("src/resources/audio/") + filename;
            AudioClip clip = new AudioClip(url);
            clip.setVolume(volume);
            clip.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound: " + filename);
            e.printStackTrace();
        }
    }

    public static AudioClip loopMusic(String filename) {
        try {
            if (isMusicMuted) return null;
            String url = CurrentTempUtil.getResourcePath("src/resources/audio/") + filename;
            AudioClip clip = new AudioClip(url);
            clip.setCycleCount(AudioClip.INDEFINITE);
            clip.setVolume(volume);
            clip.play();
            loops.put(filename, clip);
            return clip;
        } catch (Exception e) {
            System.err.println("Failed to loop sound: " + filename);
            e.printStackTrace();
            return null;
        }
    }

    public static void stopMusic(String filename) {
        AudioClip clip = loops.get(filename);
        if (clip != null) {
            clip.stop();
        }
    }

    public static void setVolume(double vol) {
        volume = vol;
        for (AudioClip clip : loops.values()) {
            clip.setVolume(vol);
        }
    }

    public static double getVolume() {
        return volume;
    }

    // MUSIC MUTE
    public static void setMusicMuted(boolean muted) {
        isMusicMuted = muted;
        if (muted) {
            for (AudioClip clip : loops.values()) {
                clip.stop();
            }
        }
    }

    public static boolean isMusicMuted() {
        return isMusicMuted;
    }

    // SOUND MUTE
    public static void setSoundMuted(boolean muted) {
        isSoundMuted = muted;
    }

    public static boolean isSoundMuted() {
        return isSoundMuted;
    }
}
