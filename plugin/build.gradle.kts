plugins {
    id("sh.lpx.outlet.plugin-conventions")
    id("sh.lpx.outlet.shadow-conventions")
}

val pluginName: String by project
val minecraftVersion: String by project

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")

    runtimeOnly(project(":legacy"))
}

tasks {
    runServer {
        minecraftVersion(minecraftVersion)
    }

    jar {
        archiveClassifier.set("original")
    }

    shadowJar {
        archiveBaseName.set(pluginName)
        archiveClassifier.set("")
    }
}
