package com.yohann.ocihelper.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StartupSecurityGuard {
    @Value("${web.account}") private String account;
    @Value("${web.password}") private String password;
    @Value("${web.jwt-secret}") private String jwtSecret;

    @PostConstruct
    public void validate() {
        if (account == null || account.isBlank() || "yohann1".equals(account)
                || password == null || password.length() < 16 || "yohann1".equals(password)
                || jwtSecret == null || jwtSecret.length() < 32 || jwtSecret.equals(password)) {
            throw new IllegalStateException("Configura un usuario propio, contraseña de al menos 16 caracteres y clave JWT independiente de al menos 32 caracteres");
        }
    }
}
