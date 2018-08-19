package notenorie;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    private Rectangle mRectangel;
    private Text mText;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        initVariables();

        PianoPane pane = new PianoPane();



        root.getChildren().add(mRectangel);
        root.getChildren().add(mText);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
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
}
