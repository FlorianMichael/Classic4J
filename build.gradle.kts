import de.florianmichael.baseproject.*

plugins {
    `java-library`
    id("de.florianmichael.baseproject.BaseProject")
}

setupProject()
setupPublishing(listOf(DeveloperInfo("FlorianMichael", "EnZaXD", "florian.michael07@gmail.com")))

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.13.1")
}
