package com.example.tts;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class TtsService {

    public String generateSpeech(TtsRequest request) {

        try {
            // Create a folder for generated audio files
            Path outputDirectory = Paths.get("generated-audio");

            if (!Files.exists(outputDirectory)) {
                Files.createDirectories(outputDirectory);
            }

            // Create a unique MP3 filename
            String fileName = UUID.randomUUID() + ".mp3";

            Path outputFile = outputDirectory.resolve(fileName);

            // Select Edge TTS voice
            String voice = getVoice(request.getLanguage(), request.getVoice());

            // Location of Python script
            Path pythonScript = Paths.get("tts.py").toAbsolutePath();

            // Run Python script
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python3",
                    pythonScript.toString(),
                    request.getText(),
                    voice,
                    outputFile.toString()
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Read Python output
            StringBuilder output = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException(
                        "TTS generation failed: " + output
                );
            }

            // Check whether MP3 was actually created
            if (!Files.exists(outputFile)) {
                throw new RuntimeException(
                        "Audio file was not created."
                );
            }

            return outputFile.toAbsolutePath().toString();

        } catch (Exception e) {

            e.printStackTrace();

            return "Error generating speech: " + e.getMessage();
        }
    }

    private String getVoice(String language, String gender) {

        if (language == null) {
            language = "en-US";
        }

        if (gender == null) {
            gender = "female";
        }

        switch (language) {

            case "hi-IN":
                return gender.equalsIgnoreCase("male")
                        ? "hi-IN-MadhurNeural"
                        : "hi-IN-SwaraNeural";

            case "gu-IN":
                return gender.equalsIgnoreCase("male")
                        ? "gu-IN-NiranjanNeural"
                        : "gu-IN-DhwaniNeural";

            case "mr-IN":
                return gender.equalsIgnoreCase("male")
                        ? "mr-IN-ManoharNeural"
                        : "mr-IN-AarohiNeural";

            case "es-ES":
                return gender.equalsIgnoreCase("male")
                        ? "es-ES-AlvaroNeural"
                        : "es-ES-ElviraNeural";

            case "fr-FR":
                return gender.equalsIgnoreCase("male")
                        ? "fr-FR-HenriNeural"
                        : "fr-FR-DeniseNeural";

            case "de-DE":
                return gender.equalsIgnoreCase("male")
                        ? "de-DE-ConradNeural"
                        : "de-DE-KatjaNeural";

            case "en-US":
            default:
                return gender.equalsIgnoreCase("male")
                        ? "en-US-GuyNeural"
                        : "en-US-AriaNeural";
        }
    }
}