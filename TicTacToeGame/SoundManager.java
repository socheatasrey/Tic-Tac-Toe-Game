import javax.sound.sampled.Clip;

public class SoundManager {
    public static boolean isSoundEnabled = true;
    public static Object backgroundMusic;
    public static boolean isMusicEnabled;

    public static void playClickSound(Clip clip) {
        if (isSoundEnabled && clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
