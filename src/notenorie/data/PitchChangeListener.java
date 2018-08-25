package notenorie.data;


/**
 * Interface for receiving updates from the Pitch Handler.
 *
 * Updates will be triggered when the state of one of the keys of a MIDI keyboard changes.
 *
 * */
public interface PitchChangeListener {
    // A Key was pressed down
    boolean PRESSED = true;

    // A key was released
    boolean UNPRESSED = false;


    void pitchChanged (int pitch, boolean status);
}
