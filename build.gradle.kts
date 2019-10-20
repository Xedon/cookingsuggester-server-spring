import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("com.palantir.docker") version "0.22.1"
    id("org.springframework.boot") version "2.1.9.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    id("idea")
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    kotlin("plugin.jpa") version "1.2.71"
}

group = "de.ev"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

val snippetsDir = file("build/generated-snippets")
docker {
    name = "hub.docker.com/coockingsugester:${version}"
    files(tasks.bootJar.get().outputs)

}

task<Copy>("unpack"
) {
    dependsOn(this.project.tasks.bootJar)
    from(zipTree(tasks.bootJar.get().outputs.files.singleFile))
    into("build/dependency")
}

tasks.withType<BootRun> {
    environment("spring_profiles_active", "dev")
}

docker {
    name = "${project.group}/${project.name}"
    copySpec.from(tasks.getByName("unpack").outputs).into("dependency")
    buildArgs(mapOf("DEPENDENCY" to "dependency", "MAIN_CLASS" to "de.ev.coockingsuggester.Booststrap"))
}

dependencies {
    compile("joda-time:joda-time:2.10.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:2.10.0")
    implementation("io.springfox:springfox-data-rest:2.9.2")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.data:spring-data-rest-hal-browser")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}
