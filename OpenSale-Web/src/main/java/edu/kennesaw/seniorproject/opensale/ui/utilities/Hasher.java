package edu.kennesaw.seniorproject.opensale.ui.utilities;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import sun.misc.BASE64Encoder;

/**
 * Utility library for hashing things, mostly passwords.
 * @author spencer
 */
public class Hasher {
    
    public static String hashPassword(String password) {
        HashFunction hf = Hashing.sha256();
        HashCode hc = hf.hashString(password);
        BASE64Encoder base64 = new BASE64Encoder();
        String hashedPassword = base64.encode(hc.asBytes());
        return hashedPassword;
    }
}
