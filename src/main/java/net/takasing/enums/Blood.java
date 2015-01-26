package net.takasing.enums;

/**
 * @author toyofuku_takashi
 */
public enum Blood {
    A(1),
    B(2),
    O(3),
    AB(4),
    OTHER(0),
    ;
    private int value;
    Blood(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public static Blood getBlood(int value) {
        for (Blood blood : values()) {
            if (blood.getValue() == value) {
                return blood;
            }
        }
        return Blood.OTHER;
    }
}
