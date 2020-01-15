plugins {
    kotlin("jvm") version "1.3.61"
    idea
}

group = "world.gregs.hestia.social"
version = "0.3.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    //Main
    implementation("io.netty:netty-all:4.1.29.Final")
    implementation("org.yaml:snakeyaml:1.23")

    //Logging
    implementation("commons-io:commons-io:2.6")
    implementation("com.google.guava:guava:27.1-jre")

    //Utilities
    implementation("org.slf4j:slf4j-api:1.7.25")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    //Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.0.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.mockito:mockito-core:2.25.1")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}