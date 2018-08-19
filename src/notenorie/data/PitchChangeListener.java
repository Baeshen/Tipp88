package notenorie.data;

public interface PitchChangeListener {
    boolean PRESSED = true;
    boolean UNPRESSED = false;

    void pitchChanged (int pitch, boolean status);
}
