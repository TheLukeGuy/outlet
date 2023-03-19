plugins {
    `java-library`
}

group = "sh.lpx"
version = "1.0.0-SNAPSHOT"
description = "A modern plugin manager for Paper."

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
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
}
