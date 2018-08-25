package notenorie.data;

public class Note {

    private String mName;
    private int mPitch;

    public Note (String name, int pitch) {

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getPitch() {
        return mPitch;
    }

    public void setPitch(int pitch) {
        this.mPitch = pitch;
    }
}
