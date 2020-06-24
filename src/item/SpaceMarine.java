package item;

import utils.ValidateInput;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class that represents space marine - element of collection
 */
public class SpaceMarine implements Cloneable, Comparable<SpaceMarine>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private long ownerId;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private static int curId = 0;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float health; //Значение поля должно быть больше 0
    private boolean loyal;
    private Weapon weaponType = null; //Поле может быть null
    private MeleeWeapon meleeWeapon = null; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public SpaceMarine(String name, Coordinates coordinates, float health, boolean loyal, Weapon weaponType,
                       MeleeWeapon meleeWeapon, Chapter chapter) {
        this();
        this.id = ++curId;
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.loyal = loyal;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, float health,
                       boolean loyal, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this(name, coordinates, health, loyal, weaponType, meleeWeapon, chapter);
        this.id = id;
        this.creationDate = creationDate;
    }

    public SpaceMarine(int id, long ownerId, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, float health,
                       boolean loyal, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this(id, name, coordinates, creationDate, health, loyal, weaponType, meleeWeapon, chapter);
        this.ownerId = ownerId;
    }

    public SpaceMarine() {
        creationDate = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public boolean getLoyal() {
        return loyal;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public static void setCurId(int curId) {
        SpaceMarine.curId = curId;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHealth() {
        return health;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return Float.compare(that.health, health) == 0 &&
                loyal == that.loyal &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                weaponType == that.weaponType &&
                meleeWeapon == that.meleeWeapon &&
                Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, creationDate, health, loyal, weaponType, meleeWeapon, chapter);
    }

    /**
     * Scans element from input stream with invitation to enter
     * @param sc
     */
    public void scan(Scanner sc) throws NoSuchElementException {
        ValidateInput in = new ValidateInput(sc);

        System.out.print("Input a name: ");
        name = sc.next();
        id = ++curId;
        System.out.print("Input coordinates(x, y):\n");
        System.out.print("x: ");
        long x = in.validateLong();
        System.out.print("y: ");
        long y = in.validateLong();
        coordinates = new Coordinates(x, y);
        System.out.print("Input health: ");
        health = in.validateFloat();
        System.out.print("Input loyal(true or false): ");
        loyal = in.validateBool();
        weaponType = in.validateWeapon();
        System.out.print("Input melee weapon(CHAIN_SWORD, CHAIN_AXE, POWER_BLADE): ");
        meleeWeapon = in.validateMeleeWeapon();
        System.out.print("Input chapter(name, count of marines and world):\n");
        System.out.print("name: ");
        String nameChapter = sc.next();
        System.out.print("count: ");
        int count = in.validatePositiveInt();
        System.out.print("world: ");
        String nameWorld = sc.next();
        chapter = new Chapter(nameChapter, count, nameWorld);
        System.out.println();
    }


    @Override
    public String toString() {
        return "\"SpaceMarine\":{" +
                "\n\t\"id\":" + id +
                ", \n\t\"name\":\"" + name + '\"' +
                ", \n\t\"coordinates\":" + coordinates +
                ", \n\t\"creationDate\":\"" + creationDate +
                "\", \n\t\"health\":" + health +
                ", \n\t\"loyal\":" + loyal +
                ", \n\t\"weaponType\":\"" + weaponType +
                "\", \n\t\"meleeWeapon\":\"" + meleeWeapon +
                "\", \n\t\"chapter\":" + chapter +
                '}';
    }

    @Override
    public int compareTo(SpaceMarine spaceMarine) {
        return name.compareTo(spaceMarine.name);
    }
}

