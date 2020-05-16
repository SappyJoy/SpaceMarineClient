package item;

import java.io.Serializable;

/**
 * SpaceMarine.Chapter is a simple class which represents chapter
 */
public class Chapter implements Comparable<Chapter>, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле не может быть null

    public Chapter(String name, int marinesCount, String world) {
        this.name = name;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name +
                "\", \"marinesCount\":" + marinesCount +
                ", \"world\":\"" + world +
                "\"}";
    }

    @Override
    public int compareTo(Chapter chapter) {
        if (world.equals(chapter.world)) {
            return name.compareTo(chapter.name);
        } else {
            return world.compareTo(chapter.world);
        }
    }
}
