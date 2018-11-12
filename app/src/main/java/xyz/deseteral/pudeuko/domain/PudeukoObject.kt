package xyz.deseteral.pudeuko.domain

data class PudeukoObject(
    val text: String,
    val link: Link? = null,
    val image: Image? = null,
    val createdAt: String
)
