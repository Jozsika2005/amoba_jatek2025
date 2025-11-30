# Amőba Játék - amoba2025_vedes

Egyszerű konzolos amőba játék Java + Maven projektként.  
A játék két játékosnak (X és O) szól, tartalmaz high score táblát, VO (Value Object) osztályokat, és teszteket JUnit5-tel.

---

## Projekt célja

Ez a projekt az amőba játék logikájának gyakorlati megvalósítása:

- Konzolos játék két játékosnak (X és O)
- Játék logika (nyerés, döntetlen)
- High score tábla mentéssel és kiírással
- VO osztály a ranglista bejegyzésekhez
- Egyszerű OOP szerkezet (Main, Service, VO, Exception)
- Maven projekt struktúra, futtatható JAR készítése

---

## Használt technológiák

- Java 17
- Maven
- JUnit5 (tesztek)
- Mockito (indokolt esetekben)

---

## Projekt struktúra
amoba2025_vedes/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── hu/szakacsjozsef/amoba/
│   │   │       ├── AmoebaJatek.java
│   │   │       ├── service/HighScoreService.java
│   │   │       └── vo/ScoreEntry.java
│   │   │
│   │   └── resources/
│   │       └── highscore.txt
│   │
│   └── test/
│       └── java/
│           └── hu/szakacsjozsef/amoba/
│               ├── AmoebaJatekTest.java
│               └── HighScoreServiceTest.java
