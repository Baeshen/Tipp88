package notenorie;

import javax.sound.midi.*;

public class PianoReceiver implements Receiver{

    private Receiver mReceiver;



    public PianoReceiver () {
        try {
            mReceiver = MidiSystem.getReceiver();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(MidiMessage message, long timeStamp) {
        byte[] m = message.getMessage();

        if (m[0] != (byte) 254) {
            System.out.println("Received Byte: " +  m[2]);
        }
    }

    @Override
    public void close() {

    }
}
