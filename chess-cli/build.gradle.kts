plugins { application }
java { toolchain { languageVersion = JavaLanguageVersion.of(25) } }
dependencies { implementation(project(":chess-core")) }
application { mainClass = "com.javachess.cli.Main" }
