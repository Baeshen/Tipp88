package notenorie.helper;

import notenorie.layout.ScorePane;

import java.util.HashMap;


/**
 * Helper class for ScorePane
 */
public class ScoreHelper {

    // Container for the position of notes in clefG
    private HashMap<Integer, Integer> mClefG;
    // Container for the position of notes in clefF
    private HashMap<Integer, Integer> mClefF;

    /**
     * Default Constructor
     */
    public ScoreHelper () {
        mClefG = new HashMap<>();
        mClefF = new HashMap<>();

        int[] clefFsteps = {2,1,2,2,1,2,2};
        // Setup of the positions of the f Clef
        for (int i = 39, counter = 21; i >= 0; ) {
            for (int step : clefFsteps) {
                if (i >= 0)
                    mClefF.put(counter, i--);
                counter += step;
            }
        }


        int[] clefGsteps = {2,2,2,1,2,2,1};

        // Setup of the positions of the G Clef
        for (int i = 39, counter = 41; i >= 0; ) {
            for (int step : clefGsteps) {
                if (i >= 0)
                    mClefG.put(counter, i--);
                counter += step;
            }
        }
    }

    /**
     *
     * Returns the position of a note based on a Score based on its clef
     *
     * @param clef  which clef should be used
     * @param note  note which position should be returned
     * @return  return the position of the note
     * @throws NullPointerException
     */
    public int getNoteScorePosition(int clef, int note) throws NullPointerException{
        switch (clef) {

            case ScorePane.CLEF_G:
                System.out.println("Clef G");
                return mClefG.get(note);

            case ScorePane.CLEF_F:
                System.out.println("Clef F");
                return mClefF.get(note);

            default:
                return 0;
        }
    }
}
