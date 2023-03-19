plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

group = "sh.lpx"
version = "1.0.0-SNAPSHOT"
description = "A modern plugin manager for Paper."

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    processResources {
        val properties = mapOf("version" to project.version, "description" to project.description)
        inputs.properties(properties)
        filesMatching("paper-plugin.yml") {
            expand(properties)
        }
    }

    withType<JavaCompile>().configureEach {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    withType<Javadoc>().configureEach {
        options.encoding = Charsets.UTF_8.name()
    }

    @Suppress("UnstableApiUsage")
    withType<ProcessResources>().configureEach {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.19.4")
    }
}
