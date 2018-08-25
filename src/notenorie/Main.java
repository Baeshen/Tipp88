package notenorie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import notenorie.data.Note;
import notenorie.data.PitchChangeListener;
import notenorie.data.PitchHandler;
import notenorie.layout.ScorePane;

import java.util.HashMap;


public class Main extends Application
        implements PitchChangeListener {

    private Scene mMainScene;


    private BorderPane mRoot;

    private FlowPane mCenterPane;
    private ScorePane mGScore;


    private MenuBar mMenuBar;

    private Menu mViewMenu;
    private MenuItem mViewMain;
    private MenuItem mViewSettings;

    private HashMap<Note, Boolean> mPossibleNotes;

    @Override
    public void start(Stage primaryStage) throws Exception{

        noteSetup();

        mGScore = new ScorePane();
        mGScore.setAlignment(Pos.CENTER);

        mCenterPane = new FlowPane(Orientation.VERTICAL);
        mCenterPane.getChildren().add(mGScore);
        mCenterPane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(mCenterPane, Pos.CENTER);

        mRoot = new BorderPane();

        mMainScene = new Scene(mRoot, 800, 500);

        mMenuBar = new MenuBar();

        mViewMenu = new Menu("View");

        mViewMain = new MenuItem("Score");
        mViewSettings = new MenuItem("Settings");


        mViewSettings.setOnAction((e) -> {
            //((FlowPane)mSettingsScene.getRoot()).setPrefHeight(mRoot.getPrefHeight());
            //((FlowPane)mSettingsScene.getRoot()).setPrefWidth(mRoot.getPrefWidth());
        });

        mViewMain.setOnAction((e) -> {
            primaryStage.setScene(mMainScene);
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


    private void noteSetup () {
        mPossibleNotes = new HashMap<>();
        String[] noteNames = {"C", "D", "E", "F", "G", "A", "H"};
        int[] midiSteps = {2,2,1,2,2,2,1};

        for (int midi = 24, i = 0,nameCounter = 1; midi <= 95;) {

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

    @Override
    public void pitchChanged(int pitch, boolean status) {
        if (status) {
            Platform.runLater(() -> {
                mGScore.addNote(pitch);
            });
        }

    }
}
