# Java Chess

Un moteur d'échecs en Java, indépendant des interfaces (CLI, JavaFX à venir).

## Prérequis

- JDK 25
- Gradle 9.8.1 **uniquement pour générer le wrapper la première fois**

## Première installation

Depuis la racine du dépôt :

```bash
gradle wrapper --gradle-version 9.8.1
./gradlew test
./gradlew :chess-cli:run
```

Committez les fichiers `gradlew`, `gradlew.bat` et `gradle/wrapper/` générés.
À partir de là, plus besoin de Gradle installé localement.

## Modules

- `chess-core` : règles et objets métier (aucune dépendance UI)
- `chess-cli` : point d'entrée en terminal
- `chess-ui` : futur module JavaFX

## Prochaine étape

Ajouter `Move`, `Board` et le placement initial, avec tests.
