plugins {
    id("java")
}

group = "dev.diego"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // https://mvnrepository.com/artifact/com.zaxxer/HikariCP
    implementation("com.zaxxer:HikariCP:2.3.2")

    //h2
    implementation("com.h2database:h2:2.2.224")

    //ibaitis
    implementation("org.mybatis:mybatis:3.5.13")

    // SLF4J API
    // https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
    implementation("ch.qos.logback:logback-classic:1.4.11")


}

tasks.test {
    useJUnitPlatform()
}