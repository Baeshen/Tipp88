package notenorie.layout;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import notenorie.data.PitchChangeListener;
import notenorie.helper.ScoreHelper;

import java.util.ArrayList;

public class ScorePane extends GridPane {

    private ScoreHelper mScoreHelper;

    private ArrayList<Integer> mNoteContainer;

    // Noten Schlüssel
    private int mClef;

    private double mScale;

    private double mUpperLimit;
    private double mLowerLimit;

    private int mScoreLength;

    private static int sNoteFrontOffset = 2;
    private static int sNoteOffset = 2;

    private static double GRID_BOX_WIDTH = 3;
    private static double GRID_BOX_HEIGHT = 3;

    private static int sDefaultUpperLimit = 16;
    private static int sDefaultLowerLimit = 29;

    private static int sDefaultScale = 5;

    private static int sDefaultScoreLength = 40;

    public static final int CLEF_G = 1;
    public static final int CLEF_F = 2;

    private static int sDefaultClef = CLEF_G;

    public ScorePane () {
        this(sDefaultUpperLimit, sDefaultLowerLimit, sDefaultClef);
    }

    public ScorePane (int clef) {
        this(sDefaultUpperLimit, sDefaultLowerLimit, clef);
    }

    public ScorePane (int upperLimit, int lowerLimit, int clef) {
        setUpperLimit(upperLimit);
        setLowerLimit(lowerLimit);
        setClef(clef);
        setScale(sDefaultScale);
        setScoreLength(sDefaultScoreLength);

        setGridLinesVisible(true);

        init();
    }

    private void init () {
        mNoteContainer = new ArrayList<>();

        mScoreHelper = new ScoreHelper();

        int scoreHeight = 40;

        for (int column = 0; column < getScoreLength(); column++) {
            for (int row = 0; row < scoreHeight; row++) {
                if ( row <= getLowerLimit() && row >= getUpperLimit())
                    add(new Rectangle(getGridBoxWidth(),getGridBoxHeight(), Color.WHITE), column, row);

                if (row >= 18 && row <= 26 && row % 2 == 0) {
                    add(new Rectangle( getGridBoxWidth(), 2, Color.BLACK), column, row);
                    System.out.println("Created Line Pice @ " + column  + " - " + row);
                }
            }
        }

    }

    public void addNote (int pitch) {

        mNoteContainer.add(pitch);

        int columnIndex = sNoteFrontOffset + sNoteOffset * mNoteContainer.size() + 1;
        int rowIndex;

        try {
            rowIndex = mScoreHelper.getNoteScorePosition(getClef(), pitch);
        } catch (NullPointerException e) {
            return;
        }

        if (rowIndex < getUpperLimit() || rowIndex > getLowerLimit()) {
            return;
        }

        //if (mNoteContainer.size() * 3 > )

        Platform.runLater(() -> {
            Circle circle = new Circle(getGridBoxHeight()/2 - 3, Color.BLACK);

            GridPane.setHalignment(circle, HPos.CENTER);
            GridPane.setValignment(circle, VPos.CENTER);

            add(circle, columnIndex, rowIndex );
            if ((rowIndex < getLowerLimit() || rowIndex > getUpperLimit()) && (rowIndex % 2) == 0 )
                add(new Rectangle( getGridBoxWidth(), 2, Color.BLACK), columnIndex, rowIndex);
        });

        System.out.println("Added Note @ " + columnIndex + " - " + rowIndex);
    }

    public void clearNotes () {
        mNoteContainer.clear();
    }

    public double getGridBoxWidth() {
        return GRID_BOX_WIDTH * mScale;
    }

    public double getGridBoxHeight() {
        return GRID_BOX_HEIGHT * mScale;
    }

    public void setScale (double scale) {
        this.mScale = scale;
    }

    public double getScale () {
        return this.mScale;
    }

    public void setUpperLimit(double upperLimit) {
        if (upperLimit < 18) {
            this.mUpperLimit = upperLimit;
        }
        else{
            this.mUpperLimit = sDefaultUpperLimit;
        }
    }

    public void setLowerLimit(double lowerLimit) {
        if (lowerLimit < 39 && lowerLimit > 27) {
            this.mLowerLimit = lowerLimit;
        } else {
            this.mLowerLimit = sDefaultLowerLimit;
        }
    }

    public double getUpperLimit() {
        return mUpperLimit;
    }

    public double getLowerLimit() {
        return mLowerLimit;
    }

    public void setClef(int clef) {
        this.mClef = clef;
    }

    public int getClef () {
        return this.mClef;
    }

    public int getScoreLength() {
        return mScoreLength;
    }

    public void setScoreLength(int scoreLength) {
        this.mScoreLength = scoreLength;
    }
}
