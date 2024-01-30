package ec.edu.unemi.unimart.configurations.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public record RsaKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
