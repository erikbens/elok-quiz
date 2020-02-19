# eLOK Quiz
 
Das Projekt enthält das aktuelle eLOK Quiz. Teste dein eLOK Wissen!

## Unterstützte Services und Vehalten  

### Backend
- REST Services für alle notwendigen Verwaltungen von Nutzern, Fragen, Antworten, Bereichen

### Frontend
- Verwaltung von Nutzern, inkl. Registrierung, Login sowie der Pflege durch Admins
- Pflege von Bereichen, Fragen und Antworten für Administratoren
- "Spielen" des Quiz mit verschiedenen Schwierigkeitsstufen und Bereichen
- Highscore- Übersicht

## Entwicklung

Details zur Einbindung / Deployment

### Einbindung

Beim Backend handelt sich, um ein Maven-Pojekt, welches in die Wunsch- IDE importiert werden kann.

- Checkout Git Repository

```
cd folder/to/clone-into/
git clone https://git-lps.loyaltypartner.com/elok/quiz.git
```

- Import Maven Projekt in IDE (eclipse, IntelliJ, ...)

Das Frontend wurde mit Angular entwickelt. Dazu wird npm (`https://www.npmjs.com/`), NodeJS (`https://nodejs.org/`) und Angular (`npm install -g @angular/cli`) benötigt.

### Packaging / Deployment

Kompiliert und gebaut wid das Projekt über Maven.
* `mvn clean compile`
* `mvn clean package` 

Als SpringBoot Applikation bestehen zwei Möglichkeiten für das Deployment:

#### Lokales Deployment

Backend:
In diesem Fall wird der Build-In Server von SpringBoot genutzt, es wird der Port (Aktuell 8888) aus der Datei `src/main/resources/application.properties` verwendet.

Gestartet wird der Server über den Maven- Befehl `mvn spring-boot:run` innerhalb des Projekt- Ordners.

Frontend:
Das Projekt kann mit AngularCLI über `ng -o serve` auf Port 4200 gestartet werden.

#### Deployment auf Server

In diesem Fall über Docker das Projekt bauen und im Docker Container starten. Dazu liegt eine `docker-compose.yml` im Projektverzeichnis. Diese (baut und) startet 
- Eine MySQL Instanz
- Das Backend basierend auf Spring
- Das Frontend basierend auf Angular

### Todos

 - [ ] Einstellen von Fragen / Antworten auch für Nutzer
 - [ ] Unique- Sicherstellung für Bilder