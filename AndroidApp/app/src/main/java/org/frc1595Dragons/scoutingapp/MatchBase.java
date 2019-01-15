package org.frc1595Dragons.scoutingapp;

/**
 * Created by Stephen Ogden on 12/29/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class MatchBase {

    public String name;
    public DataType datatype;
    public java.util.ArrayList<String> value;

    public static enum DataType {
        Text, Number, Boolean, BooleanGroup;
    }

}
