package com.microsservice_enviarPDF.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.sound.sampled.Line;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/arquivos")
public class PdfController {
	
		private static final Logger logger = LoggerFactory.getLogger(PdfController.class);
	    private final ResourceLoader resourceLoader;
	
	    public PdfController(ResourceLoader resourceLoader) {
	        this.resourceLoader = resourceLoader;
	    }
	    
		@GetMapping(value = "/obterPDF", produces = MediaType.APPLICATION_PDF_VALUE)
		public ResponseEntity<byte[]> getPdfFromTxt() {
	        try {
	            
	            Resource resource = resourceLoader.getResource("classpath:arquivoPDF/MarcosPAlbuquerque.txt");

	            // Obtém o InputStream do recurso
	            try (InputStream inputStream = resource.getInputStream();
	                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

	                // Lê as linhas do arquivo e concatena o conteúdo
	                StringBuilder contentBuilder = new StringBuilder();
	                
	                String line;
	                while ((line = bufferedReader.readLine()) != null) {
	                    contentBuilder.append(line);
	                }

	                String base64Data = contentBuilder.toString();

	                // Decodifica o Base64 para obter os bytes do PDF
	                byte[] pdfBytes = Base64.getDecoder().decode(base64Data);

	                logger.info("PDF gerado com sucesso!!");
	                
	                return ResponseEntity.ok().body(pdfBytes);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("Erro ao processar a requisição para obter o PDF.", e);
	            return ResponseEntity.badRequest().body(null);
	        }
	    }
    
//    @GetMapping("/obterPDF")
//    public ResponseEntity<Resource> obterPDF(Authentication authentication) throws IOException {
//        if (authentication != null && authentication.isAuthenticated()) {
//            try {
//                Resource resource = resourceLoader.getResource("classpath:arquivoPDF/MarcosPAlbuquerque.pdf");
//
//                // Obtém o caminho do arquivo
//                Path path = resource.getFile().toPath();
//
//                // Lê o conteúdo do arquivo em um array de bytes
//                byte[] content = Files.readAllBytes(path);
//
//                // Configura o cabeçalho da resposta
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_PDF);
//                headers.setContentDispositionFormData("attachment", "MarcosPAlbuquerque.pdf");
//
//                // Retorna a resposta com o arquivo
//                return ResponseEntity.ok()
//                        .headers(headers)
//                        .body(resource);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return ResponseEntity.status(500).body(null);
//            }
//        } else {
//            // Usuário não autenticado, retornar 401 Unauthorized
//            return ResponseEntity.status(401).body(null);
//        }
//    }
}