# 🎙️ Text-to-Speech Web Application

A full-stack web-based **Text-to-Speech (TTS) application** that converts written text into natural-sounding speech. Users can enter text, select a language and voice, generate audio, play it directly in the browser, and download the generated speech.

The application is built using **React.js, Spring Boot, Java, Python, and Edge TTS** and is deployed as a complete full-stack application.

## 🚀 Live Demo

**Frontend:**
https://text-to-speach-1-c3tx.onrender.com

**Backend API:**
https://text-to-speach-y6om.onrender.com

---

## ✨ Features

* 📝 Convert written text into speech
* 🌍 Support for multiple languages
* 🎙️ Male and female voice selection
* 🔊 Generate and play speech directly in the browser
* ⬇️ Download generated audio
* 🔢 Real-time character counter
* 🚫 Maximum text limit of 5,000 characters
* ⚠️ Empty-text validation
* 🧹 Clear text and generated audio
* ❤️ Simple and responsive user interface
* 🔗 React frontend connected to Spring Boot REST APIs
* 🐍 Python-based speech generation using Edge TTS
* ☁️ Full-stack deployment using Render

---

## 🌍 Supported Languages

The application currently supports:

| Language | Code    |
| -------- | ------- |
| English  | `en-US` |
| Hindi    | `hi-IN` |
| Gujarati | `gu-IN` |
| Marathi  | `mr-IN` |
| Spanish  | `es-ES` |
| French   | `fr-FR` |
| German   | `de-DE` |

Each supported language provides **Female** and **Male** voice options.

---

## 🛠️ Technology Stack

### Frontend

* React.js
* JavaScript
* HTML5
* CSS3
* Vite

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Validation
* Maven

### Speech Generation

* Python
* Edge TTS

### Tools & Deployment

* Git
* GitHub
* Postman
* Docker
* Render

---

## 🏗️ Application Architecture

```text
┌─────────────────────────┐
│      React Frontend     │
│                         │
│  Text + Language +      │
│  Voice Selection        │
└────────────┬────────────┘
             │
             │ HTTP REST API
             ▼
┌─────────────────────────┐
│    Spring Boot Backend  │
│                         │
│  Validation + API       │
│  Request Processing     │
└────────────┬────────────┘
             │
             │ Executes
             ▼
┌─────────────────────────┐
│      Python / Edge TTS  │
│                         │
│    Speech Generation    │
└────────────┬────────────┘
             │
             │ MP3 Audio
             ▼
┌─────────────────────────┐
│     React Frontend      │
│                         │
│  ▶ Play  ⏸ Pause       │
│  🔊 Volume  ⬇ Download │
└─────────────────────────┘
```

---

## 📂 Project Structure

```text
text-to-speech/
│
├── frontend/
│   ├── src/
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── ...
│   ├── package.json
│   └── ...
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── example/
│   │       │           └── tts/
│   │       │               ├── TtsApplication.java
│   │       │               ├── TtsController.java
│   │       │               ├── TtsService.java
│   │       │               ├── TtsRequest.java
│   │       │               ├── ErrorResponse.java
│   │       │               └── GlobalExceptionHandler.java
│   │       │
│   │       └── resources/
│   │           └── application.properties
│   │
│   ├── tts.py
│   ├── Dockerfile
│   └── pom.xml
│
├── Text-to-Speech Application API.postman_collection.json
├── .gitignore
└── README.md
```

---

## 🔄 How the Application Works

1. The user enters text into the React application.
2. The user selects a language.
3. The user selects a male or female voice.
4. React sends the request to the Spring Boot backend.
5. Spring Boot validates the request.
6. The backend invokes the Python speech-generation script.
7. Python uses Edge TTS to generate an MP3 audio file.
8. The backend returns the generated audio URL.
9. React loads the audio.
10. The user can play or download the generated speech.

---

## 🔌 REST API Documentation

### 1. Health Check

**GET**

```text
/api/health
```

Example:

```text
GET https://text-to-speach-y6om.onrender.com/api/health
```

Response:

```text
Text-to-Speech Backend is running!
```

---

### 2. Get Supported Voices

**GET**

```text
/api/voices
```

Example:

```text
GET https://text-to-speach-y6om.onrender.com/api/voices
```

This endpoint returns the supported languages and available male/female voices.

---

### 3. Generate Speech

**POST**

```text
/api/tts
```

Request:

```json
{
  "text": "Hello, welcome to my Text-to-Speech application.",
  "language": "en-US",
  "voice": "female"
}
```

Example response:

```json
{
  "audioUrl": "/api/audio/generated-file.mp3"
}
```

---

### 4. Get Generated Audio

**GET**

```text
/api/audio/{fileName}
```

Example:

```text
GET /api/audio/generated-file.mp3
```

This endpoint returns the generated MP3 audio file.

---

## ✅ Validation & Error Handling

The application includes validation for:

* Empty text
* Text exceeding 5,000 characters
* Invalid text input
* Invalid language or voice selection
* Speech-generation failures
* Backend/API failures
* Network-related failures

Example validation message:

```text
Please enter some text.
```

The application prevents users from entering more than **5,000 characters**.

---

## 🧪 Testing

The application was tested using both the deployed web application and Postman.

### Functional Testing

The following functionality was verified:

* [x] Backend health check
* [x] Supported voice API
* [x] Speech generation
* [x] Audio playback
* [x] Audio download
* [x] Empty-text validation
* [x] 5,000-character limit
* [x] Clear functionality
* [x] All supported languages
* [x] Male voices
* [x] Female voices
* [x] Frontend-backend communication
* [x] Production deployment

### Postman Collection

A Postman collection is included in the repository:

```text
Text-to-Speech Application API.postman_collection.json
```

It contains requests for:

* Health Check
* Generate Speech
* Get Supported Voices
* Get Generated Audio

---



## ☁️ Deployment

The application is deployed as two services on **Render**.

### Frontend

```text
https://text-to-speach-1-c3tx.onrender.com
```

### Backend

```text
https://text-to-speach-y6om.onrender.com
```

The backend is containerized using Docker and includes the Java Spring Boot application and Python speech-generation environment.

---

## 🔐 Security Considerations

The project follows basic security practices:

* API processing is handled by the backend.
* Input validation is implemented.
* Environment-specific configuration can be managed using environment variables.
* `.env` files are excluded from Git using `.gitignore`.
* Generated audio files are excluded from Git.
* Frontend and backend are separated into independent services.

---

## 🔮 Future Enhancements

Possible future improvements include:

* User authentication
* Speech history
* Favorite voices
* Additional languages and voices
* PDF/DOCX text upload
* User usage limits
* Cloud audio storage
* Admin dashboard
* Usage analytics
* Improved rate limiting
* Additional audio formats

---

## 🎓 Project Purpose

This project was developed as a full-stack learning project to understand:

* React.js frontend development
* Java Spring Boot REST API development
* Backend validation
* Python integration with Java
* Text-to-Speech technology
* REST API testing with Postman
* Git and GitHub
* Docker
* Full-stack deployment
* Frontend-backend integration

---

## 👨‍💻 Author

**Md Asif Ali**

GitHub:
https://github.com/asifali802302-hub

---

## ⭐ Acknowledgements

* React.js
* Spring Boot
* Edge TTS
* Python
* Render
* Postman
* GitHub

---

## 📄 License

This project is intended for educational and portfolio purposes.
