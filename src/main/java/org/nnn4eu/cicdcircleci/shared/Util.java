package org.nnn4eu.cicdcircleci.shared;

import javax.validation.ConstraintViolation;
import java.security.SecureRandom;
import java.util.function.Predicate;

public class Util {
    // "0123456789" + "ABCDE...Z"
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

//    @SuppressWarnings("unchecked")
//    public static <T extends Serializable> T clone(T object) {
//        return (T) SerializationUtils.deserialize(SerializationUtils.serialize(object));
//    }


    public static Predicate<ConstraintViolation<?>> havingMessage(String message) {
        return l -> message.equals(l.getMessage());
    }

    public static Predicate<ConstraintViolation<?>> havingPropertyPath(String propertyPath) {
        return l -> propertyPath.equals(l.getPropertyPath().toString());
    }
}
