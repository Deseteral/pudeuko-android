package xyz.deseteral.pudeuko

import java.util.Date

data class PudeukoObject(
    val text: String,
    val link: Link,
    val image: Image,
    val createdAt: Date
)
