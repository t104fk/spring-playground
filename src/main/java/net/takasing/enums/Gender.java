package net.takasing.enums;

/**
 * @author takasing
 */
public enum Gender {
    MALE(1),
    FEMALE(2),
    OTHER(0),
    ;
    private int value;
    Gender(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public static Gender getGender(int value) {
        for (Gender gender : values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        return Gender.OTHER;
    }
}
