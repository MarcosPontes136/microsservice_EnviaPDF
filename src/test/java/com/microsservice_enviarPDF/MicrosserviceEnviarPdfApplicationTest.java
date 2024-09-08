package com.microsservice_enviarPDF;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MicrosserviceEnviarPdfApplicationTest {
	
    @Mock
    private ConfigurableApplicationContext mockCtx;

    @Mock
    private Environment mockEnv;

    @Mock
    private Logger mockLogger;
    
    @Test
    public void contextLoads() {
        assertThat(true).isTrue();
    }

    @Test
    public void testMain() {
        assertThatCode(() -> {
            MicrosserviceEnviarPdfApplication.main(new String[]{});
        }).doesNotThrowAnyException();
    }
}
