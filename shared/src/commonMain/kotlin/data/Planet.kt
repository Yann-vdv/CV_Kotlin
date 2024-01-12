package data

@kotlinx.serialization.Serializable
data class Planet(
    val climate: String,
    val created: String,
    val diameter: Int,
    val edited: String,
    val films: List<String>,
    val gravity: String,
    val name: String,
    val orbital_period: Int,
    val population: Int,
    val residents: List<String>,
    val rotation_period: Int,
    val surface_water: Int,
    val terrain: String,
    val url: String
)
