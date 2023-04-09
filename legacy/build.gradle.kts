plugins {
    id("sh.lpx.outlet.plugin-conventions")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    // This is the earliest version that can run on Java 17 :/
    // I don't care enough to deal with targeting a separate Java version
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
}

tasks {
    runServer {
        minecraftVersion("1.17.1")
    }
}
