package StrUtil;

/**
 * Created by rbtlong on 5/9/15.
 */
public class TokenUtil {
    public static boolean isAcceptableChar(char c) {
        return c >= 48 && c <= 57 // numbers
                || c >= 65 && c <= 90 // capitals
                || c >= 97 && c <= 122; // lower case
    }
}
