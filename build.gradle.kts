plugins {
    kotlin("jvm") version "1.3.61"
    idea
}

group = "world.gregs.hestia.social"
version = "0.3.2"

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/hestia-rsps/Hestia")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //Main
    implementation("world.gregs.hestia:hestia-server-core:0.4.4")
    implementation("io.netty:netty-all:4.1.44.Final")
    implementation("org.yaml:snakeyaml:1.25")

    //Utilities
    implementation("commons-io:commons-io:2.6")
    implementation("com.google.guava:guava:28.2-jre")

    //Logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    //Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.assertj:assertj-core:3.14.0")
    testImplementation("org.mockito:mockito-core:3.2.4")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}