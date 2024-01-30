package ec.edu.unemi.unimart.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa-keys")
public record RsaKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}
