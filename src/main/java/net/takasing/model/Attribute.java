package net.takasing.model;

import lombok.Data;
import net.takasing.enums.Blood;
import net.takasing.enums.Gender;

/**
 * @author toyofuku_takashi
 */
@Data
public class Attribute {
    private String memo;
    private Gender gender;
    private Blood blood;
}
