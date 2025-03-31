package Utili;

public class OSUtils {

    public static String getOperatingSystem() {
        return System.getProperty("os.name").toLowerCase();
    }

    public static boolean isWindows() {
        return getOperatingSystem().contains("win");
    }

    public static boolean isMac() {
        return getOperatingSystem().contains("mac");
    }

    public static boolean isLinux() {
        return getOperatingSystem().contains("nix") || getOperatingSystem().contains("nux");
    }

    public static void main(String[] args) {
        System.out.println("Operating System: " + getOperatingSystem());
        System.out.println("Is Windows: " + isWindows());
        System.out.println("Is Mac: " + isMac());
        System.out.println("Is Linux: " + isLinux());
    }
}