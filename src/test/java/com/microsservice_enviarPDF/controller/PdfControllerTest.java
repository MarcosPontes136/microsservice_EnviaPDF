package com.microsservice_enviarPDF.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;

public class PdfControllerTest {

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private Resource resource;

    @InjectMocks
    private PdfController pdfController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPdfFromTxt_Success() throws Exception {
        String base64Content = "JVBERi0xLjcNCiWhs8XXDQoxIDAgb2JqDQo8PC9QYWdlcyAyIDAgUiAvVHlwZS9DYXRhbG9nL1ZpZXdlclByZWZlcmVuY2VzPDwvRGlzcGxheURvY1RpdGxlIHRydWUvSGlkZU1lbnViYXIgdHJ1ZS9IaWRlVG9vbGJhciB0cnVlL0hpZGVXaW5kb3dVSSB0cnVlPj4+Pg0KZW5kb2JqDQoyIDAgb2JqDQo8PC9Db3VudCAxL0tpZHNbIDQgMCBSIF0vVHlwZS9QYWdlcz4+DQplbmRvYmoNCjMgMCBvYmoNCjw8L0NyZWF0aW9uRGF0ZShEOjIwMjQwOTA4MDgwNzM1KS9DcmVhdG9yKFBERml1bSkvUHJvZHVjZXIoUERGaXVtKT4+DQplbmRvYmoNCjQgMCBvYmoNCjw8L0NvbnRlbnRzIDUgMCBSIC9Hcm91cDw8L0NTL0RldmljZVJHQi9TL1RyYW5zcGFyZW5jeS9UeXBlL0dyb3VwPj4vTWVkaWFCb3hbIDAgMCA1OTUuNSA4NDIuMjVdL1BhcmVudCAyIDAgUiAvUmVzb3VyY2VzPDwvRXh0R1N0YXRlPDwvR1M3IDYgMCBSIC9HUzggNyAwIFIgPj4vRm9udDw8L0YxIDggMCBSID4+L1Byb2NTZXRbL1BERi9UZXh0L0ltYWdlQi9JbWFnZUMvSW1hZ2VJXT4+L1RhYnMvUy9UeXBlL1BhZ2U+Pg0KZW5kb2JqDQo1IDAgb2JqDQo8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvTGVuZ3RoIDE0NT4+c3RyZWFtDQp4nKWOMQvCMBCF94P7D29MBWMuNiZZBS24iQEHcWw7KdT6//EsFXT2hoN3fNz7BiZn3XtSijUcQg42INXe+oBHy3Re4M60LUyrvUA8SsckSjoIore6QrJZ7zdFmlNEP+pT9FNKc2qYLuZZiZi28tGM1VLWn3xFOTDttOHINPwnlKPdKPelNJnMAvjtegFm5TBqDQplbmRzdHJlYW0NCmVuZG9iag0KNiAwIG9iag0KPDwvQk0vTm9ybWFsL1R5cGUvRXh0R1N0YXRlL2NhIDE+Pg0KZW5kb2JqDQo3IDAgb2JqDQo8PC9CTS9Ob3JtYWwvQ0EgMS9UeXBlL0V4dEdTdGF0ZT4+DQplbmRvYmoNCjggMCBvYmoNCjw8L0Jhc2VG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2N+4Ak54GKzTuC5yGSKni/C2xNtk4quNAIiDaI8AsxIX+22BgGXEvmfswNISaBtOgQIoHwIdJfbd4ln6H4CP0JOAn6VLgF+gNwDfpB8D/gT6CkIZ5AhCpWAHrBbWA/qE/YAHhDCgJpwBvFfIAf6W8FnAp4VnoNtzwnNoXxReAPxj4Y8BXxL+HPAv2HevwteE/w34N8LXAb8rfBfwe8L3AL8v/APgjwVIF34ivAX4MyFPgiiJZYDlYiVgNfvbY2KtuBrQJd4C2CA2AbaK7YBbxVsBu8W9gAfEA4BD4jDgIfEwYEg8AjgiwkdiRDwBmBATgCkxBTgtngW8X7wf8EHxtwE/Iv4B4BPiZwCfFmcBPyd+DvB58XnAF0TYJf6J+BLgayLsEr8m/jXg18VvAH5L/Bbgd8TvAP6t+Drg90TYKP5A/EfAfxLfBPyxCEvFt8QrgNfEayRIMBWwTILPpWqpGtAhOQBrpVrAZdIywBXSCsA10hrAOqkRcIO0ATAgbQfslrpJkLfJiLXcI/cA3iHfAfiY/BjgU/IzJMnPyp9H+zn5m2h/W/422j+U/y/gGxYLz2WJf0dNyCHCqmd/b+0Z+VX5NflPkV8S5r1AJP8v+RWyyF8GjyqWg/IX5C/9O73nRbQNCmVuZHN0cmVhbQ0KZW5kb2JqDQoxMSAwIG9iag0KWyAyMDMgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDUyNyAwIDAgMCAwIDAgMCAwIDAgMCAwIDAgMCAwIDQ4NiAzMjNdDQplbmRvYmoNCnhyZWYNCjAgMTINCjAwMDAwMDAwMDAgNjU1MzUgZg0KMDAwMDAwMDAxNyAwMDAwMCBuDQowMDAwMDAwMTYxIDAwMDAwIG4NCjAwMDAwMDAyMTcgMDAwMDAgbg0KMDAwMDAwMDMwNCAwMDAwMCBuDQowMDAwMDAwNTYwIDAwMDAwIG4NCjAwMDAwMDA3NzcgMDAwMDAgbg0KMDAwMDAwMDgzMCAwMDAwMCBuDQowMDAwMDAwODgzIDAwMDAwIG4NCjAwMDAwMDEwNTEgMDAwMDAgbg0KMDAwMDAwMTI4OCAwMDAwMCBuDQowMDAwMDA4NjAzIDAwMDAwIG4NCnRyYWlsZXINCjw8DQovUm9vdCAxIDAgUg0KL0luZm8gMyAwIFINCi9TaXplIDEyL0lEWzw1Q0QzNTM3OUY2NEY0Qjg5QUNEMEM2REMwNUQ1MkNBNj48NUNEMzUzNzlGNjRGNEI4OUFDRDBDNkRDMDVENTJDQTY+XT4+DQpzdGFydHhyZWYNCjg4MDMNCiUlRU9GDQo="; // Coloque aqui um conteúdo base64 válido de um PDF

        InputStream inputStream = new ByteArrayInputStream(base64Content.getBytes(StandardCharsets.UTF_8));

        when(resourceLoader.getResource("classpath:arquivoPDF/MarcosPAlbuquerque.txt")).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);

        ResponseEntity<byte[]> response = pdfController.getPdfFromTxt();
        
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        byte[] expectedPdfBytes = Base64.getDecoder().decode(base64Content);
        assertEquals(expectedPdfBytes.length, response.getBody().length);
    }

    @Test
    public void testGetPdfFromTxt_FileNotFound() throws Exception {
        when(resourceLoader.getResource("classpath:arquivoPDF/MarcosPAlbuquerque.txt")).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new IOException("Arquivo não encontrado"));
        
        ResponseEntity<byte[]> response = pdfController.getPdfFromTxt();

        assertEquals(400, response.getStatusCodeValue());
    }
}
