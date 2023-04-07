plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

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

    withType<ProcessResources>().configureEach {
        filteringCharset = Charsets.UTF_8.name()
    }
}
