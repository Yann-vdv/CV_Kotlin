data class Question(
    val id: Int,
    val label: String,
    val correctId: Int,
    val answers: List<Answer>
)
