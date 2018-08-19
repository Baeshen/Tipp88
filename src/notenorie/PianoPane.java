package notenorie;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import notenorie.data.PitchChangeListener;
import notenorie.data.PitchHandler;

public class PianoPane extends Pane
        implements PitchChangeListener {

    // Test variable
    private Group keys;



    public PianoPane () {

        keys = new Group();

        PitchHandler.getInstance().registerPitchChangeListener(this);

        for (int i = 36; i <= 48; i++) {
            HBox key = new HBox();
            key.setAlignment(Pos.BOTTOM_CENTER);

            key.setPrefSize(30, 80);

            key.getChildren().add(new Text(Integer.toString(i)));
            keys.getChildren().add(key);
        }

        getChildren().add(keys);
   }


    @Override
    public void pitchChanged(int pitch, boolean status) {
        System.out.println("Pitch: " + pitch + " changed to " + ((status) ? "Pressed" : "Unpressed"));
    }
}
