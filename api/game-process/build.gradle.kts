plugins {
    id("base-conventions")
    id("integration-test-suite")
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(libs.bundles.logging)
    implementation(libs.fastutil)
    implementation(libs.guice)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.rsprot.api)
    implementation(projects.api.config)
    implementation(projects.api.controller)
    implementation(projects.api.dbGateway)
    implementation(projects.api.invWeight)
    implementation(projects.api.npc)
    implementation(projects.api.player)
    implementation(projects.api.playerOutput)
    implementation(projects.api.random)
    implementation(projects.api.registry)
    implementation(projects.api.repo)
    implementation(projects.api.route)
    implementation(projects.api.stats.levelmod)
    implementation(projects.api.type.typeReferences)
    implementation(projects.api.utils.utilsLogging)
    implementation(projects.api.utils.utilsMap)
    implementation(projects.api.utils.utilsZone)
    implementation(projects.engine.events)
    implementation(projects.engine.game)
    implementation(projects.engine.interact)
    implementation(projects.engine.map)
    implementation(projects.engine.routefinder)
    integrationImplementation(libs.fastutil)
    integrationImplementation(libs.rsprot.api)
    integrationImplementation(projects.api.combat.combatCommons)
    integrationImplementation(projects.api.deathPlugin)
    integrationImplementation(projects.api.hitPlugin)
    integrationImplementation(projects.api.player)
    integrationImplementation(projects.api.random)
    integrationImplementation(projects.api.registry)
    integrationImplementation(projects.api.utils.utilsZone)
    integrationImplementation(projects.engine.coroutine)
}
