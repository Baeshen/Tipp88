package notenorie.layout;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import notenorie.data.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsPane extends FlowPane {

    private HashMap<Note,Boolean> mPossibleNotes;

    private HashMap<Integer, FlowPane> octaves;

    public SettingsPane () {
        octaves = new HashMap<>();
        noteSetup();
        setupOctaves();

        setPadding(new Insets(50,150,50,150));
        setVgap(30);
        setHgap(30);
    }

    private void setupOctaves () {

        String[] noteNames = {"C", "D", "E", "F", "G", "A", "H"};

        for (int octaveCounter = 1; octaveCounter <= 6; octaveCounter++) {
            FlowPane octave = new FlowPane();

            Label test = new Label();

            test.setFont(new Font(16));

            getChildren().add(test);

            test.setText("Octave " + octaveCounter);

            octave.setHgap(10);
            octave.setVgap(10);

            for (String noteName : noteNames){
                Note note = getNote(noteName + octaveCounter);
                octave.getChildren().add(createButton(note));
            }
            getChildren().add(octave);
        }
    }

    private Button createButton (Note note) {
        CornerRadii cornerRadii = new CornerRadii(10);

        Button button = new Button();
        button.setPrefSize(120, 50);
        button.setPadding(new Insets(10));

        button.setBackground( new Background(
                        new BackgroundFill(
                                Color.WHITE,
                                cornerRadii,
                                null
                        )));

        button.setBorder( new Border(
                        new BorderStroke(
                                Color.BLACK,
                                BorderStrokeStyle.SOLID,
                                cornerRadii,
                                new BorderWidths(2)
                        )));

        button.setText(note.getName());
        System.out.println(note.getName() + " - " + note.getPitch() + " - " + mPossibleNotes.get(note));

        button.setOnAction(
                (e) -> {
                    mPossibleNotes.put(note, !mPossibleNotes.get(note));
                    System.out.println(note.getName() + " value change to " + mPossibleNotes.get(note));

                    if (mPossibleNotes.get(note)) {
                        button.setBackground(new Background(
                                        new BackgroundFill(
                                                Color.color(0, 0.7, 0.3, 0.4),
                                                cornerRadii,
                                                null
                                        )));
                    } else {
                        button.setBackground(new Background(
                                        new BackgroundFill(
                                                Color.WHITE,
                                                cornerRadii,
                                                null
                                        )));
                    }
                });
        return button;
    }

    /**
     * Sets up the container for the settings
     */
    private void noteSetup () {
        mPossibleNotes = new HashMap<>();
        String[] noteNames = {"C", "D", "E", "F", "G", "A", "H"};
        int[] midiSteps = {2,2,1,2,2,2,1};

        for (int midi = 24, i = 0,nameCounter = 1; midi <= 95;) {

            mPossibleNotes.put(new Note(noteNames[i] + nameCounter, midi), false);


            mPossibleNotes.put(new Note(noteNames[i] + nameCounter, midi), false);

            System.out.println("Note: " + noteNames[i] + nameCounter + " - " +  midi + " -- " + midiSteps[i]);

            midi += midiSteps[i];

            if (noteNames[i].equals("H")) {
                nameCounter++;
                i = 0;
                continue;
            }
            i++;
        }
    }

    /**
     * Returns an ArrayList containing all Active Notes
     * @return ArrayList containing all selected notes
     */
    public ArrayList<Note> getActiveNotes () {
        ArrayList<Note> notes = new ArrayList<>();
        for (Map.Entry<Note, Boolean> entry : mPossibleNotes.entrySet()) {
            if (entry.getValue()) {
                notes.add(entry.getKey());
            }
        }
        return notes;
    }

    private Note getNote (String name) {
        for (Map.Entry<Note, Boolean> entry : mPossibleNotes.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
