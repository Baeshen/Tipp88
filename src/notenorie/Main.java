package notenorie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import notenorie.data.PitchChangeListener;
import notenorie.data.PitchHandler;
import notenorie.layout.ScorePane;

import javax.swing.border.Border;


public class Main extends Application
        implements PitchChangeListener {

    private Rectangle mRectangel;
    private Text mText;

    private ScorePane mScore;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        PitchHandler.getInstance().registerPitchChangeListener(this);

        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);

        ScrollPane scrollPane = new ScrollPane();

        initVariables();

        mScore = new ScorePane();

        mScore.setAlignment(Pos.CENTER);

        scrollPane.setContent(mScore);


        flowPane.getChildren().add(scrollPane);

        flowPane.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(flowPane, Pos.CENTER);

        root.setCenter(flowPane);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    private void initVariables () {
        mRectangel = new Rectangle(50, 50, Color.RED);

        mRectangel.setX(100);
        mRectangel.setY(100);

        mText = new Text("Lorem ipsum");

        mText.setText("Test TEst test");

        mText.setX(50);
        mText.setY(50);
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void pitchChanged(int pitch, boolean status) {
        if (status) {
            Platform.runLater(() -> {
                mScore.addNote(pitch);
            });
        }

    }
}
