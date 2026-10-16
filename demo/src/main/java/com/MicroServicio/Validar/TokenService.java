package com.MicroServicio.Validar;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private static final long DURACION_SEGUNDOS = 8 * 60 * 60;

    @Value("${app.security.token-secret}")
    private String secret;

    public String emitir(Long idMedico) {
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(
                (idMedico + ":" + (Instant.now().getEpochSecond() + DURACION_SEGUNDOS))
                        .getBytes(StandardCharsets.UTF_8));
        return payload + "." + firmar(payload);
    }

    public Long validar(String token) {
        try {
            String[] partes = token.split("\\.", -1);
            if (partes.length != 2 || !MessageDigest.isEqual(
                    partes[1].getBytes(StandardCharsets.US_ASCII),
                    firmar(partes[0]).getBytes(StandardCharsets.US_ASCII))) {
                return null;
            }
            String[] datos = new String(Base64.getUrlDecoder().decode(partes[0]), StandardCharsets.UTF_8)
                    .split(":", -1);
            if (datos.length != 2 || Long.parseLong(datos[1]) < Instant.now().getEpochSecond()) {
                return null;
            }
            return Long.parseLong(datos[0]);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private String firmar(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(
                    mac.doFinal(payload.getBytes(StandardCharsets.US_ASCII)));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo firmar el token del médico", e);
        }
    }
}
