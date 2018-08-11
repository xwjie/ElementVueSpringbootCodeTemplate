package cn.xiaowenjie.tool;

import org.apache.shiro.crypto.hash.SimpleHash;

public class PasswordUtil {

    public static final String ALGORITHM_NAME = "MD5";

    public static final int HASH_ITERATIONS = 1024;
    /**
     *  重新计算md5值
     * @param password
     * @param salt
     * @return
     */
    public static String renewPassword(String password, String salt){
        SimpleHash md5hash = new SimpleHash(
                ALGORITHM_NAME, password, salt, HASH_ITERATIONS);
        return md5hash.toHex();
    }
}
