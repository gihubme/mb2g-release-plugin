package org.nnn4eu.cicdcircleci.shared;

public class AppConstant {
    //"^(.+)@(\\S+)$"
    public final static String emailRegex =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public final static String countryCode = "(\\+\\d{1,3}( )?)";
    public final static String nationalNumber = "(\\d{4,13})$";
//    public final static String nationalNumber = "(\\d{1,4}[ .]?)?|(\\d{3,4}[ .]?)?|\\d{4}$";

    // optional group of 1 to 4 digits followed by space or no space
    // optional group of 3 to 4 digits followed by space or no space
    // mandatory group of 4 digits

    // ---- ---- 1111   4
    // ---1 ---- 1111   5
    // --11 ---- 1111   6
    // -111 ---- 1111   7
    // 1111 ---- 1111   8

    // ---- -111 1111   7
    // ---1 -111 1111   8
    // --11 -111 1111   9
    // -111 -111 1111   10
    // 1111 -111 1111   11

    // ---- 1111 1111   8
    // ---1 1111 1111   9
    // --11 1111 1111   10
    // -111 1111 1111   11
    // 1111 1111 1111   12

}
