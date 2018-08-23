package notenorie.layout;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ScorePane extends GridPane {

    private Rectangle mPlaceholder;

    private ArrayList<Integer> mNoteContainer;

    private double mScale;

    private double mUpperLimit;
    private double mLowerLimit;


    private int mNoteCounter;

    private static int sNoteFrontOffset = 5;
    private static int sNoteOffset = 3;

    private static double GRID_BOX_WIDTH = 3;
    private static double GRID_BOX_HEIGHT = 3;

    public ScorePane () {

        mUpperLimit = 17;
        mLowerLimit = 27;

        mScale = 5;

        //setGridLinesVisible(true);

        init();
    }

    public void init () {
        mNoteContainer = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 32; j++) {

                if ( j <= getLowerLimit() && j >= getUpperLimit())
                    add(new Rectangle(getGridBoxWidth(),getGridBoxHeight(), Color.WHITE), i, j);

                if (j >= 18 && j <= 26 && j % 2 == 0) {
                    add(new Rectangle( getGridBoxWidth(), 2, Color.BLACK), i, j);
                }
            }
        }

    }


    private void displayNotes () {
        for (int i : mNoteContainer) {

            int columnIndex = sNoteFrontOffset + sNoteOffset * mNoteCounter ;
            int rowIndex    = 0;

            add(new Circle(getGridBoxHeight()/2, Color.BLACK),columnIndex, rowIndex );

            mNoteCounter++;
        }
    }



    public void addNote (int pitch) {
        mNoteContainer.add(pitch);
    }



    public double getGridBoxWidth() {
        return GRID_BOX_WIDTH * mScale;
    }

    public double getGridBoxHeight() {
        return GRID_BOX_HEIGHT * mScale;
    }

    public void setUpperLimit(double upperLimit) {
        if (upperLimit < 18) {
            this.mUpperLimit = mUpperLimit;
        }
        else{
            this.mUpperLimit = 17;
        }
    }

    public void setLowerLimit(double lowerLimit) {
        if (lowerLimit < 39 && lowerLimit > 27) {
            this.mLowerLimit = mLowerLimit;
        } else {
            this.mLowerLimit = 27;
        }
    }

    public double getUpperLimit() {
        return mUpperLimit;
    }

    public double getLowerLimit() {
        return mLowerLimit;
    }
}
