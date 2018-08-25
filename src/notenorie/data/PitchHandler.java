package notenorie.data;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.HashMap;


/** The PitchHandler class keeps track of which key on a key board have been pressed.
 *
 * The PitchHandler class deploys a inner Receiver class to listen to MIDI messages. Based on the information in
 * the MIDI message the states of the keys, which are stored in a HashMap, are updated.
 *
 * */
public final class PitchHandler {

    // Instance from the PitchHandler class
    private final static PitchHandler instance = new PitchHandler();

    // Container for storing the states of the keys
    private HashMap<Integer, Boolean> mPitchStatusContainer;
    // Own Receiver for MIDI messages
    private PianoReceiver mReceiver;

    // List of PitchChangeListeners
    private ArrayList<PitchChangeListener> mPitchChangeListeners;

    /**
     * Default Constructor
     **/
    public PitchHandler() {
        mReceiver = new PianoReceiver();
        mPitchChangeListeners = new ArrayList<>();
        mPitchStatusContainer = new HashMap<>();

        for (int i = 36; i <= 83; i++) {
            mPitchStatusContainer.put(i, false);
        }
    }

    /** Sets the state of a specific Key
     *
     * @param key
     */
    private void setKeyState (int key) {
        if (mPitchStatusContainer.containsKey(key)) {
            mPitchStatusContainer.put(key, !mPitchStatusContainer.get(key));
            notifyPitchChangeListeners(key, mPitchStatusContainer.get(key));
        }

    }

    /** Return the State of a specific Key
     *
     * @param key The key which state should be returned
     * @return State of key
     */
    public boolean getKeyState (int key) {
        return mPitchStatusContainer.getOrDefault(key, null);
    }

    /** Adds a PitchChangeListener for listening for changes of the keys
     *
     * @param listener PitchChangeListener which will be added
     */
    public void registerPitchChangeListener (PitchChangeListener listener) {
        mPitchChangeListeners.add(listener);
    }

    /** Notifies the registered PitchChangeListeners that an change in state happened
     *
     * @param pitch     The pitch of the key
     * @param status    The status which the key changed to
     */
    private void notifyPitchChangeListeners(int pitch, boolean status) {
        for (PitchChangeListener listener : mPitchChangeListeners) {
            listener.pitchChanged(pitch, status);
        }
    }

    /** Returns the instance this PitchHandler class.
     *
     * @return The instance of the class
     */
    public static PitchHandler getInstance () {
        return instance;
    }

    /**
     * Receiver for Midi Messages
     */
    public class PianoReceiver implements Receiver {

        /**
         * default Constructor
         */
        public PianoReceiver () {
            try {
                MidiSystem.getTransmitter().setReceiver(this);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }

        /** Overridden send method from the super class
         *
         * @param message   MIDI message
         * @param timeStamp timeStamp when the message send
         */
        @Override
        public void send(MidiMessage message, long timeStamp) {
            // status of the key
            int status = message.getStatus();
            // pitch of the key which change status
            byte pitch = message.getMessage()[1];
            // speed at which the key was changed
            byte speed = message.getMessage()[2];

            // sets the key state
            if (status != (byte) 254) {
                setKeyState(pitch);
            }
        }

        /**
         * close the connection
         */
        @Override
        public void close() {
            this.close();
        }
    }
}
