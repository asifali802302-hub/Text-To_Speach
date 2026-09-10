package com.example.tts;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TtsController {

    private final TtsService ttsService;

    public TtsController(TtsService ttsService) {
        this.ttsService = ttsService;
    }

    @GetMapping("/api/health")
    public String healthCheck() {
        return "Text-to-Speech Backend is running!";
    }
    @GetMapping("/api/voices")
public String getVoices() {
    return """
            {
              "languages": [
                {
                  "language": "en-US",
                  "name": "English",
                  "voices": ["female", "male"]
                },
                {
                  "language": "hi-IN",
                  "name": "Hindi",
                  "voices": ["female", "male"]
                },
                {
                  "language": "gu-IN",
                  "name": "Gujarati",
                  "voices": ["female", "male"]
                },
                {
                  "language": "mr-IN",
                  "name": "Marathi",
                  "voices": ["female", "male"]
                },
                {
                  "language": "es-ES",
                  "name": "Spanish",
                  "voices": ["female", "male"]
                },
                {
                  "language": "fr-FR",
                  "name": "French",
                  "voices": ["female", "male"]
                },
                {
                  "language": "de-DE",
                  "name": "German",
                  "voices": ["female", "male"]
                }
              ]
            }
            """;
}

  @PostMapping("/api/tts")
public String generateSpeech(@jakarta.validation.Valid @RequestBody TtsRequest request) {

    String language = request.getLanguage();
    String voice = request.getVoice();

    String[] allowedLanguages = {
            "en-US",
            "hi-IN",
            "gu-IN",
            "mr-IN",
            "es-ES",
            "fr-FR",
            "de-DE"
    };

   if (!java.util.Arrays.asList(allowedLanguages).contains(language)) {
    return new com.fasterxml.jackson.databind.ObjectMapper()
            .createObjectNode()
            .put("error", "Invalid language.")
            .toString();
}

if (!voice.equalsIgnoreCase("female") &&
    !voice.equalsIgnoreCase("male")) {
    return new com.fasterxml.jackson.databind.ObjectMapper()
            .createObjectNode()
            .put("error", "Invalid voice.")
            .toString();
}

    String filePath = ttsService.generateSpeech(request);

if (filePath.startsWith("Error")) {
    return filePath;
}

String fileName = java.nio.file.Paths
        .get(filePath)
        .getFileName()
        .toString();

return """
        {
          "audioUrl": "/api/audio/%s"
        }
        """.formatted(fileName);}

    @GetMapping("/api/audio/{fileName}")
    public ResponseEntity<Resource> getAudio(@PathVariable String fileName) {

        try {
            Path audioFile = Paths.get("generated-audio")
                    .resolve(fileName)
                    .normalize();

            Resource resource = new UrlResource(audioFile.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + fileName + "\""
                    )
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}