import de.florianmichael.baseproject.*

plugins {
    `java-library`
    id("de.florianmichael.baseproject.BaseProject")
}

setupProject()
setupPublishing()

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.13.2")
}
