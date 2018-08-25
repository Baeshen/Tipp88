package notenorie.layout;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import notenorie.helper.ScoreHelper;

import java.util.ArrayList;

public class ScorePane extends GridPane {

    // ScoreHelper for placement of Notes
    private ScoreHelper mScoreHelper;

    // Container for the played Notes that are visible
    private ArrayList<Integer> mPitchContainer;
    // Container for Shapes added to the score afterwards
    private ArrayList<Shape> mScoreShapeContainer;

    // Used Clef of the score
    private int mClef;

    // defines the size of the elements
    private double mScale;

    // Limit how high the notes can go (0-17)
    private double mUpperLimit;
    // Limit how low the notes can go (27 - 39)
    private double mLowerLimit;

    // How many Measure should there be (default 4)
    private int mMeasureCount;

    // How long is the score, based on ScoreOffset and MeasureCount
    private int mScoreLength;

    // Offset at the right and left
    private int mScoreOffset = 2;
    // Offset between the notes
    private int mNoteOffset = 2;

    // Size for a grid box,
    private final static double GRID_BOX_WIDTH = 3;
    private final static double GRID_BOX_HEIGHT = 3;

    public static final int CLEF_G = 1;
    public static final int CLEF_F = 2;

    // Default values
    private static int sDefaultUpperLimit = 16;
    private static int sDefaultLowerLimit = 29;

    private static int sDefaultScale = 5;

    private static int sDefaultMeasureCount = 4;

    private static int sDefaultClef = CLEF_G;




    /**
     * Lowest level Constructor
     *
     * @param upperLimit    Upper limit of the score
     * @param lowerLimit    lower limit of the score
     * @param clef          which clef should the score be set to
     */
    public ScorePane (int upperLimit, int lowerLimit, int clef) {
        setUpperLimit(upperLimit);
        setLowerLimit(lowerLimit);
        setClef(clef);
        setScale(sDefaultScale);
        setMeasureCount(sDefaultMeasureCount);
        setScoreLength(mScoreOffset);


        mPitchContainer = new ArrayList<>();
        mScoreShapeContainer = new ArrayList<>();
        mScoreHelper = new ScoreHelper();

        // maximal height of the score
        int scoreHeight = 40;


        /**
         * Sets up the base of the score
         *
         * @ToDo Add the score clef at the beginning
         */
        for (int column = 0; column < getScoreLength(); column++) {
            for (int row = 0; row < scoreHeight; row++) {
                if ( row <= getLowerLimit() && row >= getUpperLimit()) {
                    add(new Rectangle(getGridBoxWidth(), getGridBoxHeight(), Color.WHITE), column, row);
                }

                if (row >= 18 && row <= 26 && row % 2 == 0) {
                    add(new Rectangle( getGridBoxWidth(), 2, Color.BLACK), column, row);
                }
            }
        }
    }

    public ScorePane (int clef) {
        this(sDefaultUpperLimit, sDefaultLowerLimit, clef);
    }

    public ScorePane () {
        this(sDefaultUpperLimit, sDefaultLowerLimit, sDefaultClef);
    }

    /**
     *  Adds a note to the score base on its pitch
     *
     * @param pitch pitch of the note that should be added
     * @return  boolean that determines of the note was added
     */
    public boolean addNote (int pitch) {

        mPitchContainer.add(pitch);

        int columnIndex = mScoreOffset + mNoteOffset * mPitchContainer.size() + 1;
        int rowIndex;

        try {
            rowIndex = mScoreHelper.getNoteScorePosition(getClef(), pitch);
        } catch (NullPointerException e) {
            return false;
        }

        if (rowIndex < getUpperLimit() || rowIndex > getLowerLimit()) {
            return false;
        }

        Circle circle = new Circle(getGridBoxHeight()/2 - 3, Color.BLACK);
        mScoreShapeContainer.add(circle);
        GridPane.setHalignment(circle, HPos.CENTER);
        GridPane.setValignment(circle, VPos.CENTER);

        add(circle, columnIndex, rowIndex );
        if ((rowIndex < getLowerLimit() || rowIndex > getUpperLimit()) && (rowIndex % 2) == 0 ) {
            Rectangle rectangle = new Rectangle(getGridBoxWidth(), 2, Color.BLACK);

            mScoreShapeContainer.add(rectangle);

            add(rectangle, columnIndex, rowIndex);
        }
        return true;
    }

    /**
     * Clears the displayed notes and saved pitches
     */
    public void clearNotes () {
        mPitchContainer.clear();
        for (Shape circle : mScoreShapeContainer) {
            this.getChildren().remove(circle);
        }
        mScoreShapeContainer.clear();
    }

    /** Getters and Setters */

    /**
     * Returns the width of a grid box based on the scale variable
     * @return the width of a grid box based on scale
     */
    public double getGridBoxWidth() {
        return GRID_BOX_WIDTH * mScale;
    }

    /**
     * Returns the height of a grid box based on the scale variable
     * @return the height of a grid box based on scale
     */
    public double getGridBoxHeight() {
        return GRID_BOX_HEIGHT * mScale;
    }

    /**
     * Sets the scale for the score pane
     * @param scale scale it should be set to
     */
    public void setScale (double scale) {
        this.mScale = scale;
    }

    /**
     * Returns the scale of the score pane
     * @return  returns scale of the score pane
     */
    public double getScale () {
        return this.mScale;
    }

    /**
     * Sets the Upper Limit of the score counting from the top
     * @param upperLimit The upper limit it should be set to (0 - 18)
     */
    public void setUpperLimit(double upperLimit) {
        if (upperLimit < 18) {
            this.mUpperLimit = upperLimit;
        }
        else{
            this.mUpperLimit = sDefaultUpperLimit;
        }
    }

    /**
     * Sets the Lower Limit of the Score counting from the top
     * @param lowerLimit The lower limit it should be set to (27 - 39)
     */
    public void setLowerLimit(double lowerLimit) {
        if (lowerLimit < 39 && lowerLimit > 27) {
            this.mLowerLimit = lowerLimit;
        } else {
            this.mLowerLimit = sDefaultLowerLimit;
        }
    }

    /**
     * Returns the UpperLimit of the score
     * @return the UpperLimit of the score
     */
    public double getUpperLimit() {
        return mUpperLimit;
    }

    /**
     * Return the LowerLimit of the score
     * @return the LowerLimit of the score
     */
    public double getLowerLimit() {
        return mLowerLimit;
    }

    /**
     * Sets the clef
     * @param clef the targeted clef
     */
    public void setClef(int clef) {
        this.mClef = clef;
    }

    /**
     * Returns the clef
     * @return the used clef
     */
    public int getClef () {
        return this.mClef;
    }

    /**
     * Sets the ScoreLength based on the MeasureCount, noteOffset and scoreOffset at the start and end
     * @param scoreOffset The offset of the score
     */
    private void setScoreLength(int scoreOffset) {
        setScoreOffset(scoreOffset);

        int notesPerMeasure = 4;

        this.mScoreLength = getMeasureCount() * notesPerMeasure * mNoteOffset + scoreOffset * 2;
    }

    /**
     * Returns the score length
     * @return the scoreLength
     */
    public int getScoreLength() {
        return mScoreLength;
    }

    /**
     * Returns the MeasureCount
     * @return the MeasureCount
     */
    public int getMeasureCount() {
        return mMeasureCount;
    }

    /**
     * Sets the measure Count
     * @param measureCount the new measure count
     */
    public void setMeasureCount(int measureCount) {
        this.mMeasureCount = measureCount;
    }

    /**
     * Gets the ScoreOffset
     * @return  the ScoreOffset
     */
    public int getScoreOffset() {
        return mScoreOffset;
    }

    /**
     * Sets the ScoreOffset
     * @param scoreOffset   New ScoreOffset
     */
    public void setScoreOffset(int scoreOffset) {
        this.mScoreOffset = scoreOffset;
    }

    /**
     * Gets NoteOffset
     * @return the NoteOffset
     */
    public int getNoteOffset() {
        return mNoteOffset;
    }

    /**
     * Sets the NoteOffset
     * @param noteOffset new NoteOffset
     */
    public void setNoteOffset(int noteOffset) {
        this.mNoteOffset = noteOffset;
    }
}
