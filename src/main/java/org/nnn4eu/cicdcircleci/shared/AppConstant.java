package org.nnn4eu.cicdcircleci.shared;

public class AppConstant {
    public final static String emailRegex =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public final static String countryCode = "(\\+\\d{1,3}( )?)";
    public final static String nationalNumber = "(\\d{1,4}[ .]?)?|(\\d{3,4}[ .]?)?|\\d{4}$";

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
