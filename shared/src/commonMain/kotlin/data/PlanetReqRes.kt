package data

@kotlinx.serialization.Serializable
data class PlanetReqRes(
    val count:Int,
    var results: List<Planet?>
)
