package notenorie;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import notenorie.data.Note;
import notenorie.data.PitchChangeListener;
import notenorie.data.PitchHandler;
import notenorie.layout.ScorePane;
import notenorie.layout.SettingsPane;
import notenorie.thread.Locks;

import java.util.ArrayList;
import java.util.Random;


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

    private ArrayList<Note> mNotesToPlay;

    private Button mPlayButton;

    private Boolean mNotePlayedFlag;

    private Boolean mCreatedNoteFlag;

    private int mPlayedNotePitch;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mNotesToPlay = new ArrayList<>();

        mCreatedNoteFlag = false;
        mNotePlayedFlag = false;
        mPlayedNotePitch = 0;

        mGScore = new ScorePane();
        mGScore.setAlignment(Pos.CENTER);

        mSettingsPane = new SettingsPane();
        mSettingsPane.setAlignment(Pos.CENTER);


        mScrollPane = new ScrollPane(mSettingsPane);
        mScrollPane.setFitToWidth(true);
        mScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mScrollPane.setPrefSize(800,500);

        mCenterPane = new FlowPane(Orientation.VERTICAL);
        mCenterPane.setAlignment(Pos.CENTER);

        mCenterPane.setVgap(20);

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

            if (mCenterPane.getChildren().contains(mPlayButton)) {
                mCenterPane.getChildren().remove(mPlayButton);
            }

            if (!mCenterPane.getChildren().contains(mScrollPane)) {
                mCenterPane.getChildren().addAll(mScrollPane);
            }
        });

        mViewMain.setOnAction((e) -> {
            if (mCenterPane.getChildren().contains(mScrollPane)) {
                mCenterPane.getChildren().remove(mScrollPane);
            }
            if (!mCenterPane.getChildren().contains(mPlayButton)) {
                mCenterPane.getChildren().add(mPlayButton);
            }
            if (!mCenterPane.getChildren().contains(mGScore)) {
                mCenterPane.getChildren().addAll(mGScore);
            }
        });

        mViewMenu.getItems().addAll(mViewMain,mViewSettings);
        mMenuBar.getMenus().addAll(mViewMenu);

        mPlayButton = new Button();

        mPlayButton.setText("Play");
        mPlayButton.setAlignment(Pos.CENTER);

        mPlayButton.setOnAction((e) ->{
            System.out.println("test");
            play();
        });



        mCenterPane.getChildren().addAll(mPlayButton, mGScore);

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

    private void setup () {
    }

    private synchronized void createNotes () {
        ArrayList<Note> notes = mSettingsPane.getActiveNotes();

        if (notes.size() == 0) {
            return;
        }
        Random random = new Random();

        while (notes.size() - 1 >= 0) {

            if (mNotesToPlay.size() >= 5) {
                return;
            }

            int index = 0;
            if (notes.size() - 1 > 0) {
                index = random.nextInt(notes.size() - 1);
            }
            mNotesToPlay.add(mGScore.addNote(notes.get(index).getPitch()));
            notes.remove(index);
        }

        synchronized (Locks.CREATE_NOTES_LOCK) {
            Locks.setWakeupCreateNotes(true);
            Locks.CREATE_NOTES_LOCK.notifyAll();
        }
    }

    private synchronized void play () {
        Runnable runnable = () -> {
            // @ToDo add Stop Button, maybe?
            while (true) {
                Platform.runLater(() -> {
                    mNotesToPlay.clear();
                    mGScore.clearNotes();
                    createNotes();
                });

                synchronized (Locks.CREATE_NOTES_LOCK) {
                    while (!Locks.isWakeupCreateNotes()) {
                        try {
                            Locks.CREATE_NOTES_LOCK.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
                Locks.setWakeupCreateNotes(false);

                for (Note note : mNotesToPlay) {


                    note.setFill(Color.BLUE);
                    note.setStrokeType(StrokeType.OUTSIDE);
                    note.setStrokeWidth(1);

                    boolean flag = true;

                    while (flag) {
                        synchronized (Locks.PLAY_LOCK) {
                            try {
                                while (!Locks.isWakeupPlay()) {
                                    Locks.PLAY_LOCK.wait();
                                }
                            } catch (InterruptedException e) {
                            }
                        }

                        if (mPlayedNotePitch == note.getPitch()) {
                            flag = false;
                            note.setFill(Color.GREEN);
                        } else {
                            note.setFill(Color.RED);
                        }


                        mNotePlayedFlag = false;
                        mPlayedNotePitch = 0;
                    }

                    note.setFill(Color.BLACK);
                }

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void pitchChanged(int pitch, boolean status) {
        if (status) {
            synchronized (Locks.PLAY_LOCK) {
                mPlayedNotePitch = pitch;
                Locks.setWakeupPlay(true);
                Locks.PLAY_LOCK.notifyAll();
            }
        }

    }
}
