package fluentchat.util;

import static org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString;
import static org.apache.commons.codec.digest.DigestUtils.sha1;

public class PasswordUtils {

    private static final String OBSCURED_CODE = "FluentChat";

    public static String encode(String password) {
        return encodeBase64URLSafeString(sha1(password + OBSCURED_CODE));
    }
}
