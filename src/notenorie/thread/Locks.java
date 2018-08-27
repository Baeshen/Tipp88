package notenorie.thread;

public final class Locks {
    public static final Object CREATE_NOTES_LOCK = new Object();
    public static final Object PLAY_LOCK = new Object();

    private static boolean sWaitCreateNotes = false;
    private static boolean sWaitPlay = false;

    public static void setWakeupCreateNotes(boolean wait) {
        sWaitCreateNotes = wait;
    }

    public static void setWakeupPlay(boolean wait) {
        sWaitPlay = wait;
    }

    public static boolean isWakeupCreateNotes () {
        return sWaitCreateNotes;
    }

    public static boolean isWakeupPlay () {
        return sWaitPlay;
    }

}
