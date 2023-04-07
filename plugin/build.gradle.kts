plugins {
    id("sh.lpx.outlet.plugin-conventions")
}

val minecraftVersion: String by project

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")
}

tasks {
    runServer {
        minecraftVersion(minecraftVersion)
    }
}
