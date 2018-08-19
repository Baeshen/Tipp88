package notenorie.data;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import java.util.ArrayList;
import java.util.HashMap;

public final class PitchHandler {

    private final static PitchHandler instance = new PitchHandler();

    private HashMap<Integer, Boolean> mPitchStatusContainer;
    private PianoReceiver mReceiver;

    private ArrayList<PitchChangeListener> mPitchChangeListeners;

    public PitchHandler() {
        mReceiver = new PianoReceiver();
        mPitchChangeListeners = new ArrayList<>();
        mPitchStatusContainer = new HashMap<>();
        init();
    }

    private void init () {
        for (int i = 36; i <= 83; i++) {
            mPitchStatusContainer.put(i, false);
        }
    }

    private void setKeyState (int key) {
        if (mPitchStatusContainer.containsKey(key)) {
            mPitchStatusContainer.put(key, !mPitchStatusContainer.get(key));
            notifyPichChageListeners(key, mPitchStatusContainer.get(key));
        }

    }

    public boolean getKeyState (int key) {
        return mPitchStatusContainer.getOrDefault(key, null);
    }

    public void registerPitchChangeListener (PitchChangeListener listener) {
        mPitchChangeListeners.add(listener);
    }

    private void notifyPichChageListeners (int pitch, boolean status) {
        for (PitchChangeListener listener : mPitchChangeListeners) {
            listener.pitchChanged(pitch, status);
        }
    }


    public static PitchHandler getInstance () {
        return instance;
    }

    public class PianoReceiver implements Receiver {


        public PianoReceiver () {
            try {
                MidiSystem.getTransmitter().setReceiver(this);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void send(MidiMessage message, long timeStamp) {
            int status = message.getStatus();
            byte pitch = message.getMessage()[1];
            byte speed = message.getMessage()[2];

            if (status != (byte) 254) {
                setKeyState(pitch);
            }

            //defaultSystemReceiver.send(message, timeStamp);
        }

        @Override
        public void close() {
            this.close();
        }
    }
}
