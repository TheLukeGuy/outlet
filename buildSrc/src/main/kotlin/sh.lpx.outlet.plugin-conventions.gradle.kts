plugins {
    id("sh.lpx.outlet.base-conventions")
    id("xyz.jpenilla.run-paper")
}

val pluginName: String by project
val pluginDescription: String by project

tasks {
    processResources {
        val properties = mapOf(
            "name" to pluginName,
            "version" to project.version,
            "description" to pluginDescription,
        )
        inputs.properties(properties)
        filesMatching(listOf("plugin.yml", "paper-plugin.yml")) {
            expand(properties)
        }
    }
}
