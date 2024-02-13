import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.9.21"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.5-SNAPSHOT" apply(false)
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

val archivesBaseName: String by rootProject
val modVersion: String by rootProject
val mavenGroup: String by rootProject

architectury {
    minecraft = "1.18.2"
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    withGroovyBuilder {
        "loom" {
            "silentMojangMappingsLicense"()
            setProperty("accessWidenerPath", file("src/main/resources/ht_materials.accesswidener"))
        }
    }

    dependencies {
        add("minecraft", "com.mojang:minecraft:1.18.2")
        add("mappings", "net.fabricmc:yarn:1.18.2+build.4:v2")
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    base.archivesName = archivesBaseName
    version = modVersion
    group = mavenGroup

    repositories {
        mavenCentral()
        maven(url = "https://api.modrinth.com/maven")
        maven(url = "https://cursemaven.com")
        maven(url = "https://dvs1.progwml6.com/files/maven")
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.architectury.dev")
        maven(url = "https://maven.blamejared.com")
        maven(url = "https://maven.blamejared.com") // JEI
        maven(url = "https://maven.jamieswhiteshirt.com/libs-release") // Reach Entity Attributes
        maven(url = "https://maven.ladysnake.org/releases")
        maven(url = "https://maven.shedaniel.me") // REI
        maven(url = "https://maven.terraformersmc.com/releases") //Mod Menu
        maven(url = "https://maven.tterrag.com") // Flywheel
        maven(url = "https://mvn.devos.one/snapshots") // Create
        maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") // DevAuth
        maven(url = "https://raw.githubusercontent.com/SolidBlock-cn/mvn-repo/main")
        maven(url = "https://thedarkcolour.github.io/KotlinForForge") // KFF
        maven(url = "https://ueaj.dev/maven")
    }

    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
    }

    kotlin {
        jvmToolchain(17)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    ktlint {
        reporters {
            reporter(ReporterType.HTML)
            reporter(ReporterType.SARIF)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        jar {
            from("LICENSE") {
                rename { "${it}_${archivesBaseName}" }
            }
        }
    }
}