package notenorie.data;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Note extends Circle {

    private String mName;
    private int mPitch;

    public Note (String name, int pitch) {
        setName(name);
        setPitch(pitch);
    }

    public Note (String name, int pitch, double size, Paint fill) {
        this(name, pitch);
        this.setRadius(size);
        this.setFill(fill);
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

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof  Note && obj != null) {
            if (((Note) obj).getName().equals(getName()) && ((Note) obj).getPitch() == getPitch()) {
                return true;
            } else {
                return false;
            }
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        String string = getName() + getPitch();
        return string.hashCode();
    }
}
