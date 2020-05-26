package utils;

import item.MeleeWeapon;
import item.Weapon;

import java.util.Scanner;

public class ValidateInput {

    private Scanner sc;

    public ValidateInput(Scanner sc) {
        this.sc = sc;
    }

    public int validatePositiveInt() {
        int number;
        do {
            number = validateInt();
        } while (number < 0);
        return number;
    }

    public int validateInt() {
        while (!sc.hasNextInt()) {
            printNotValid(sc);
        }
        return sc.nextInt();
    }

    public float validateFloat() {
        while (!sc.hasNextFloat()) {
            System.out.println("Input float number: ");
            printNotValid(sc);
        }
        return sc.nextFloat();
    }

    public long validateLong() {
        while (!sc.hasNextLong()) {
            printNotValid(sc);
        }
        return sc.nextLong();
    }

    public boolean validateBool() {
        boolean result = false;
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.equals("true") || s.equals("false")) {
                result = Boolean.parseBoolean(s);
                break;
            }
            System.out.printf("\"%s\" is not a valid input.\n", s);
        }
        return result;
    }

    public Weapon validateWeapon() {
        boolean check = false;
        Weapon weapon = null;
        do {
            System.out.println("Input weapon type (PLASMA_GUN, COMBI_PLASMA_GUN, FLAMER, INFERNO_PISTOL, HEAVY_FLAMER): ");
            String name = sc.next();
            for (Weapon value : Weapon.values()) {
                if (value.toString().equals(name.toUpperCase())) {
                    check = true;
                    weapon = Weapon.valueOf(name.toUpperCase());
                    return weapon;
                }
            }
            System.out.println("There is no such weapon");
        } while (true);
    }

    public MeleeWeapon validateMeleeWeapon() {
        boolean check = false;
        MeleeWeapon weapon = null;
        do {
            String name = sc.next();
            for (MeleeWeapon value : MeleeWeapon.values()) {
                if (value.toString().equals(name.toUpperCase())) {
                    check = true;
                    weapon = MeleeWeapon.valueOf(name.toUpperCase());
                    return weapon;
                }
            }
            System.out.println("There is no such weapon");
        } while (true);
    }

    private static void printNotValid(Scanner sc) {
        String input = sc.next();
        System.out.printf("\"%s\" is not a valid input.\n", input);
    }
}
