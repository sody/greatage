log.name("release_1_0")

log.begin("1").author("sody").context("dev", "test")
log.insert("test")
    .into("name", "description")
    .values("Mutabra", "Cool Game")
log.insert("test")
    .set("name", "Mutabra")
    .set("description", "Cool Game")
log.end()

