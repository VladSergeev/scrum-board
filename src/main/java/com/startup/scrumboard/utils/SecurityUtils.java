package com.startup.scrumboard.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
/**
 * Created by vsergeev on 26.12.2016.
 */
public final class SecurityUtils {
    private static final String SALT = "YAR_YAR_CAPTAIN";

    private SecurityUtils() {
    }

    /**
     * @param password password
     * @return sha encode
     */
    public static String getPwd(final String password) {
        return new ShaPasswordEncoder(256).encodePassword(password, SALT);
    }

}
