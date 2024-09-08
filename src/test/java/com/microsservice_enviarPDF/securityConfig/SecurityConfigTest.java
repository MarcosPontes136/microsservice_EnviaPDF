package com.microsservice_enviarPDF.securityConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa o perfil de teste
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Configura o MockMvc antes de cada teste
    }

    @Test
    void shouldAllowAccessToProtectedEndpointWithBasicAuth() throws Exception {
        // Testa o acesso ao endpoint protegido com autenticação básica
        mockMvc.perform(get("/api/arquivos/obterPDF").with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectAccessWithInvalidCredentials() throws Exception {
        // Testa o acesso básico com credenciais incorretas
        mockMvc.perform(get("/api/arquivos/obterPDF").with(httpBasic("invalidUser", "wrongPassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void shouldAllowAccessToAuthenticatedUser() throws Exception {
        // Testa o acesso com um usuário autenticado simulado
        mockMvc.perform(get("/api/arquivos/obterPDF"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDenyAccessWithoutAuth() throws Exception {
        // Testa se o acesso sem autenticação é negado
        mockMvc.perform(get("/api/arquivos/obterPDF"))
                .andExpect(status().isUnauthorized());
    }
}

