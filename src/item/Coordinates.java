package item;

import java.io.Serializable;

/**
 *Class which represents coordinates
 */
public class Coordinates implements Cloneable, Serializable {
    private long x; //Поле не может быть null
    private long y; //Максимальное значение поля: 254
    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public Coordinates() {
    }

    public Coordinates clone() throws CloneNotSupportedException {
        Coordinates cloned = (Coordinates) super.clone();
        return cloned;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\t\"x\":" + x +
                ", \n\t\t\"y\":" + y +
                "\n\t}";
    }
}