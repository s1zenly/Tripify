tasks.register("buildAll") {
    group = "build"
    description = "Build all Tripify services"
    dependsOn(gradle.includedBuilds.map { it.task(":build") })
}
