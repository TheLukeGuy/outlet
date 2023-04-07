plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    fun pluginDep(id: String, version: String) = "$id:$id.gradle.plugin:$version"

    implementation(pluginDep("xyz.jpenilla.run-paper", "2.0.1"))
}
