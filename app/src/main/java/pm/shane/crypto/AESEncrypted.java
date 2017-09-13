package pm.shane.crypto;

/**
 * Created by Shane on 28/04/2017.
 */

public class AESEncrypted {

    private final String encryptedToken;
    private final byte[] unencryptedToken;
    private final String encryptedTokenKey;
    private final byte[] unencryptedTokenKey;
    private final String encryptedTokenIv;
    private final byte[] unencryptedTokenIv;

    public AESEncrypted(byte[] unencryptedToken, byte[] unencryptedTokenKey, byte[] unencryptedTokenIv, String encryptedToken, String encryptedKey, String encryptedIv) {
        this.unencryptedToken = unencryptedToken;
        this.unencryptedTokenKey = unencryptedTokenKey;
        this.unencryptedTokenIv = unencryptedTokenIv;
        this.encryptedToken = encryptedToken;
        this.encryptedTokenKey = encryptedKey;
        this.encryptedTokenIv = encryptedIv;
    }

    public String getEncryptedToken() {
        return encryptedToken;
    }

    public String getEncryptedTokenKey() {
        return encryptedTokenKey;
    }

    public String getTokenIv() {
        return encryptedTokenIv;
    }

    public String getEncryptedTokenIv() {
        return encryptedTokenIv;
    }

    public byte[] getUnencryptedToken() {
        return unencryptedToken;
    }

    public byte[] getUnencryptedTokenKey() {
        return unencryptedTokenKey;
    }

    public byte[] getUnencryptedTokenIv() {
        return unencryptedTokenIv;
    }
}
