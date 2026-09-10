import { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [text, setText] = useState("");
  const [language, setLanguage] = useState("en-US");
  const [voice, setVoice] = useState("female");
  const [error, setError] = useState("");
  const [languages, setLanguages] = useState([]);
  const [loading, setLoading] = useState(false);
  const [audioUrl, setAudioUrl] = useState("");

  useEffect(() => {
    const fetchVoices = async () => {
      try {
        const response = await fetch(
          "http://localhost:8080/api/voices"
        );

        const data = await response.json();

        setLanguages(data.languages);
      } catch (error) {
        console.error("Could not load voices:", error);
      }
    };

    fetchVoices();
  }, []);

  const checkBackend = async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/api/health"
      );

      const result = await response.text();

      alert(result);
    } catch (error) {
      alert("Backend connection failed!");
    }
  };

  const generateSpeech = async () => {
    if (text.trim() === "") {
      setError("Please enter some text.");
      return;
    }

    if (text.length > 5000) {
      setError("Text cannot exceed 5000 characters.");
      return;
    }

    setError("");
    setLoading(true);
    setAudioUrl("");

    try {
      const response = await fetch(
        "http://localhost:8080/api/tts",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            text: text,
            language: language,
            voice: voice
          })
        }
      );

      const result = await response.text();

      if (!response.ok) {
        throw new Error(result);
      }

      if (result.startsWith("Error")) {
        throw new Error(result);
      }

      const fileName = result.split("\\").pop();

      const audioFileUrl =
        `http://localhost:8080/api/audio/${fileName}`;

      setAudioUrl(audioFileUrl);

    } catch (error) {
      console.error(error);

      setError(
        "Could not generate speech. Please check the backend."
      );

    } finally {
      setLoading(false);
    }
  };

  const clearText = () => {
    setText("");
    setError("");
    setAudioUrl("");
  };

  const wordCount =
    text.trim() === ""
      ? 0
      : text.trim().split(/\s+/).length;

  return (
    <div className="app">

      <div className="container">

        <h1 className="title">
          Text-to-Speech Application
        </h1>

        <p className="subtitle">
          Convert your text into natural-sounding speech
        </p>

        <textarea
          placeholder="Enter or paste your text here..."
          value={text}
          onChange={(e) => setText(e.target.value)}
          maxLength={5000}
        />

        <div className="stats">

          <span>
            Characters: {text.length} / 5000
          </span>

          <span>
            Words: {wordCount}
          </span>

        </div>

        <div className="options">

          {/* LANGUAGE */}

          <div className="option">

            <label>
              Language
            </label>

            <select
              value={language}
              onChange={(e) => {
                setLanguage(e.target.value);
                setVoice("female");
              }}
            >

              {languages.map((item) => (
                <option
                  key={item.language}
                  value={item.language}
                >
                  {item.name}
                </option>
              ))}

            </select>

          </div>


          {/* VOICE */}

          <div className="option">

            <label>
              Voice
            </label>

            <select
              value={voice}
              onChange={(e) =>
                setVoice(e.target.value)
              }
            >

              {languages
                .find(
                  (item) =>
                    item.language === language
                )
                ?.voices.map((voiceOption) => (

                  <option
                    key={voiceOption}
                    value={voiceOption}
                  >

                    {voiceOption === "female"
                      ? "Female Voice"
                      : "Male Voice"}

                  </option>

                ))}

            </select>

          </div>

        </div>


        {/* BUTTONS */}

        <div className="buttons">

          <button
            className="generate-btn"
            onClick={generateSpeech}
            disabled={loading}
          >

            {loading
              ? "Generating..."
              : "Generate Speech"}

          </button>


          <button
            className="clear-btn"
            onClick={clearText}
          >
            Clear
          </button>


          <button
            className="backend-btn"
            onClick={checkBackend}
          >
            Check Backend
          </button>

        </div>


        {/* ERROR */}

        {error && (
          <div className="error">
            {error}
          </div>
        )}


        {/* AUDIO */}

        {audioUrl && (

          <div className="audio-section">

            <h3>
              Generated Speech
            </h3>

            <audio
              controls
              src={audioUrl}
            >
              Your browser does not support
              the audio element.
            </audio>

            <a
              className="download-btn"
              href={audioUrl}
              download="speech.mp3"
            >
              Download Audio
            </a>

          </div>

        )}

      </div>

    </div>
  );
}

export default App;