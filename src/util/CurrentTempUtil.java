package util;

import java.io.File;
import java.nio.file.Paths;

import model.GameHistoryEntry;
import model.User;

public class CurrentTempUtil {
    public static User currentUser;
    public static GameHistoryEntry currentProgress ;


    public static String getResourcePath(String relativeResourcePath) {
        if (relativeResourcePath.startsWith("file:/")) {
            return relativeResourcePath; // already a URI
        }

        String path = Paths.get(System.getProperty("user.dir"), relativeResourcePath).toString();
        File file = new File(path);
        if (file.exists()) {
            return file.toURI().toString();
        }

        throw new IllegalArgumentException("Resource not found in classpath or file system: " + relativeResourcePath);
    
    }
    public static void reset() {
        currentUser = null;
        currentProgress = null;
    }
    public static void printCurrentProgress() {
        System.out.println("Current GameType: " + CurrentTempUtil.currentProgress.getGameType());
        System.out.println("OpponentName: " + CurrentTempUtil.currentProgress.getOpponentName());
    }


    
}
