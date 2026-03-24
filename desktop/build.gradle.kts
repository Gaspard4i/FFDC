val gdxVersion: String by rootProject.extra
val lwjglVersion = "3.3.4"

sourceSets.main {
    resources.srcDirs("../assets")
}

dependencies {
    implementation(project(":core"))
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion") {
        // Exclude transitive LWJGL natives (pulled automatically by LibGDX)
        exclude(group = "org.lwjgl")
    }
    // Re-add LWJGL core + only 64-bit Windows natives
    implementation("org.lwjgl:lwjgl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl:$lwjglVersion:natives-windows")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-glfw:$lwjglVersion:natives-windows")
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-opengl:$lwjglVersion:natives-windows")
    implementation("org.lwjgl:lwjgl-openal:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-openal:$lwjglVersion:natives-windows")
    implementation("org.lwjgl:lwjgl-stb:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-stb:$lwjglVersion:natives-windows")
    implementation("org.lwjgl:lwjgl-jemalloc:$lwjglVersion")
    implementation("org.lwjgl:lwjgl-jemalloc:$lwjglVersion:natives-windows")

    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
}

tasks.register<JavaExec>("run") {
    mainClass.set("dev.gaspard.ffdc.desktop.DesktopLauncher")
    classpath = sourceSets.main.get().runtimeClasspath
    workingDir = file("../assets")
    isIgnoreExitValue = true

    if (System.getProperty("os.name").lowercase().contains("mac")) {
        jvmArgs("-XstartOnFirstThread")
    }
}

tasks.register<Jar>("dist") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "dev.gaspard.ffdc.desktop.DesktopLauncher"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
    archiveBaseName.set("ffdc-2026")
}
