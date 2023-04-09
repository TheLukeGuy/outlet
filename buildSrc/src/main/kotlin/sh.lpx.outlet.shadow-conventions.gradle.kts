plugins {
    id("sh.lpx.outlet.base-conventions")
    id("com.github.johnrengelman.shadow")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}
