plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.leawa"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.3")
    runtimeOnly("com.amazonaws:aws-lambda-java-log4j2:1.5.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    named<Jar>("jar") {
        manifest {
            attributes["Main-Class"] = "com.laewa.Handler"
        }
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        manifest {
            attributes["Main-Class"] = "com.laewa.Handler"
        }

        from("src/main/java") {
            include("**/*.*")  // <-- include ALL file types (html, css, js, png, jpg, etc.)
        }
    }
}