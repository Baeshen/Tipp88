package notenorie;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import notenorie.layout.ScorePane;

import javax.swing.border.Border;


public class Main extends Application {

    private Rectangle mRectangel;
    private Text mText;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();

        FlowPane flowPane = new FlowPane(Orientation.VERTICAL);

        initVariables();

        ScorePane score = new ScorePane();
        ScorePane score2 = new ScorePane();

        score.setAlignment(Pos.CENTER);
        score2.setAlignment(Pos.CENTER);

        flowPane.getChildren().add(score);

        flowPane.setAlignment(Pos.CENTER);

        BorderPane.setAlignment(flowPane, Pos.CENTER);

        root.setCenter(flowPane);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
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
