package notenorie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import notenorie.data.Note;
import notenorie.data.PitchChangeListener;
import notenorie.data.PitchHandler;
import notenorie.layout.ScorePane;
import notenorie.layout.SettingsPane;

import java.util.HashMap;
import java.util.Set;


public class Main extends Application
        implements PitchChangeListener {

    private Scene mMainScene;


    private BorderPane mRoot;

    private FlowPane mCenterPane;
    private ScorePane mGScore;
    private ScrollPane mScrollPane;
    private SettingsPane mSettingsPane;

    private MenuBar mMenuBar;

    private Menu mViewMenu;
    private MenuItem mViewMain;
    private MenuItem mViewSettings;

    private HashMap<Note, Boolean> mPossibleNotes;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mGScore = new ScorePane();
        mGScore.setAlignment(Pos.CENTER);

        mSettingsPane = new SettingsPane();
        mSettingsPane.setAlignment(Pos.CENTER);


        mScrollPane = new ScrollPane(mSettingsPane);
        mScrollPane.setFitToWidth(true);
        mScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mScrollPane.setPrefSize(800,500);

        mCenterPane = new FlowPane(Orientation.VERTICAL);
        mCenterPane.getChildren().addAll(mGScore);
        mCenterPane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(mCenterPane, Pos.CENTER);


        mRoot = new BorderPane();

        mMainScene = new Scene(mRoot, 800, 500);

        mMenuBar = new MenuBar();

        mViewMenu = new Menu("View");

        mViewMain = new MenuItem("Score");
        mViewSettings = new MenuItem("Settings");


        mViewSettings.setOnAction((e) -> {
            if (mCenterPane.getChildren().contains(mGScore)) {
                mCenterPane.getChildren().remove(mGScore);
            }

            if (!mCenterPane.getChildren().contains(mScrollPane)) {
                mCenterPane.getChildren().addAll(mScrollPane);
            }
        });

        mViewMain.setOnAction((e) -> {
            if (mCenterPane.getChildren().contains(mScrollPane)) {
                mCenterPane.getChildren().remove(mScrollPane);
            }
            if (!mCenterPane.getChildren().contains(mGScore)) {
                mCenterPane.getChildren().addAll(mGScore);
            }
        });



        mViewMenu.getItems().addAll(mViewMain,mViewSettings);
        mMenuBar.getMenus().addAll(mViewMenu);

        mRoot.setCenter(mCenterPane);
        mRoot.setTop(mMenuBar);

        primaryStage.setScene(mMainScene);
        primaryStage.setTitle("Notenorie");
        primaryStage.show();
        PitchHandler.getInstance().registerPitchChangeListener(this);
    }

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void pitchChanged(int pitch, boolean status) {
        if (status) {
            Platform.runLater(() -> {
                mGScore.addNote(pitch);
            });
        }

    }
}
