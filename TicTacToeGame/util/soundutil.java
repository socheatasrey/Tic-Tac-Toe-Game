package util;

import javafx.scene.media.AudioClip;
import java.util.HashMap;

public class soundutil {
    private static double volume = 0.5; // Default volume
    private static boolean isMuted = false;

    private static final HashMap<String, AudioClip> loops = new HashMap<>();

    public static void play(String filename) {
        try {
            if (isMuted) return;
            AudioClip clip = new AudioClip(filename);
            clip.setVolume(volume);
            clip.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound: " + filename);
            e.printStackTrace();
        }
    }

    public static AudioClip loopMusic(String filename) {
        try {
            AudioClip clip = new AudioClip(filename);
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

    public static void setMuted(boolean muted) {
        isMuted = muted;
        if (muted) {
            for (AudioClip clip : loops.values()) {
                clip.stop();
            }
        } else {
            // Optional: Restart music if needed
        }
    }

    public static boolean isMuted() {
        return isMuted;
    }
}
