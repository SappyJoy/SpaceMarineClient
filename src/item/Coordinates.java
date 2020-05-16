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

    public Coordinates clone() throws CloneNotSupportedException {
        Coordinates cloned = (Coordinates) super.clone();
        return cloned;
    }

    @Override
    public String toString() {
        return "{" +
                "\"x\":" + x +
                ", \"y\":" + y +
                '}';
    }
}