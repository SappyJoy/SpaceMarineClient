package gui;

import item.MeleeWeapon;
import item.SpaceMarine;
import item.Weapon;
import utils.I18N;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public class SpaceMarineAdapter {
    private SpaceMarine spaceMarine;

    public SpaceMarineAdapter(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
        id = spaceMarine.getId();
        ownerId = spaceMarine.getOwnerId();
        name = spaceMarine.getName();
        x = spaceMarine.getCoordinates().getX();
        y = spaceMarine.getCoordinates().getY();
        health = spaceMarine.getHealth();
        loyal = spaceMarine.getLoyal();
        weapon = spaceMarine.getWeaponType().toString();
        meleeWeapon = spaceMarine.getMeleeWeapon().toString();
        chapter = spaceMarine.getChapter().getName();
        count = spaceMarine.getChapter().getMarinesCount();
        world = spaceMarine.getChapter().getWorld();
        creationDate = spaceMarine.getCreationDate();
        Locale locale = I18N.getLocale();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        creationDateString = creationDate.format(dtf);
        NumberFormat nf = NumberFormat.getNumberInstance();
        healthString = nf.format(health);
    }

    public String getHealthString() {
        return healthString;
    }

    public void setHealthString(String healthString) {
        this.healthString = healthString;
    }

    private String healthString;

    private LocalDateTime creationDate;

    public String getCreationDateString() {
        return creationDateString;
    }

    public void setCreationDateString(String creationDateString) {
        this.creationDateString = creationDateString;
    }

    private String creationDateString;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    private int id = 0;

    private long ownerId = 0;

    private String name = "default";

    private long x = 0;

    private long y = 0;

    private float health = 0;

    private boolean loyal = true;

    private String weapon = Weapon.COMBI_PLASMA_GUN.toString();

    private String meleeWeapon = MeleeWeapon.CHAIN_AXE.toString();

    private String chapter = "default";

    private int count = 0;

    private String world = "default";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isLoyal() {
        return loyal;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(String meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    @Override
    public String toString() {
        return "SpaceMarineAdapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", health=" + health +
                ", loyal=" + loyal +
                ", weapon='" + weapon + '\'' +
                ", meleeWeapon='" + meleeWeapon + '\'' +
                ", chapter='" + chapter + '\'' +
                ", count=" + count +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarineAdapter that = (SpaceMarineAdapter) o;
        return id == that.id &&
                ownerId == that.ownerId &&
                x == that.x &&
                y == that.y &&
                Float.compare(that.health, health) == 0 &&
                loyal == that.loyal &&
                count == that.count &&
                name.equals(that.name) &&
                Objects.equals(weapon, that.weapon) &&
                Objects.equals(meleeWeapon, that.meleeWeapon) &&
                chapter.equals(that.chapter) &&
                world.equals(that.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, name, x, y, health, loyal, weapon, meleeWeapon, chapter, count, world);
    }
}
