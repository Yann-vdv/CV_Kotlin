package data

@kotlinx.serialization.Serializable
data class FilmSearchRes(
    val count:Int,
    var results: List<Film?>
)
