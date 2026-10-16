plugins { `java-library` }
java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }
dependencies { implementation(project(":chess-core")) }
// JavaFX sera configuré lorsque nous développerons l'interface graphique.
