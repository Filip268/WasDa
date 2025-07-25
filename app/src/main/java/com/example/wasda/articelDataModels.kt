import kotlinx.serialization.Serializable

@Serializable
data class Kategorie(val id: Int, val kategorie: String)

@Serializable
data class Ort(val id: Int, val name: String)

@Serializable
data class Fach(val id: Int, val fach_bezeichnung: String)

@Serializable
data class Artikel(val name: String, val ablaufdatum: String, val kategorie_id: Int)

@Serializable
data class ArtikelInsert(
    val name: String,
    val ablaufdatum: String,
    val kategorie_id: Int
)