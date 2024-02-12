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
        maven(url = "https://cursemaven.com") {
            content { includeGroup("curse.maven") }
        }
        maven(url = "https://api.modrinth.com/maven") {
            content { includeGroup("maven.modrinth") }
        }
        maven(url = "https://maven.architectury.dev/")
        maven(url = "https://maven.blamejared.com") {
            content { includeGroup("vazkii.patchouli") }
        }
        maven(url = "https://maven.shedaniel.me/") //REI
        maven(url = "https://maven.terraformersmc.com/releases/")
        maven(url = "https://thedarkcolour.github.io/KotlinForForge/") //KfF
        maven(url = "https://dvs1.progwml6.com/files/maven/") //JEI
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