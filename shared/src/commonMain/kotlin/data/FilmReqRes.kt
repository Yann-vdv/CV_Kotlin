package data

@kotlinx.serialization.Serializable
data class FilmReqRes(
    val count:Int,
    var results: List<Film?>
)
