import kotlinx.serialization.Serializable

@Serializable
data class Kategorie(val id: Int, val kategorie: String)

@Serializable
data class Ort(val id: Int? = null, val name: String)

@Serializable
data class Fach(val id: Int, val fach_bezeichnung: String)

@Serializable
data class Artikel(val name: String, val ablaufdatum: String, val kategorie_id: Int)

@Serializable
data class ArtikelInsert(
    val name: String,
    val ablaufdatum: String,
    val kategorie_id: Int,
    val ort_fach: Int
)

@Serializable
data class FachOrtRelation(
    val id: Int,
    val ort_id: Int,
    val fach_id: Int
)


@Serializable
data class ArtikelDetails(
    val artikel_id: Int,
    val artikel_name: String,
    val ablaufdatum: String,
    val kategorie_name: String,
    val ort_name: String,
    val fach_bezeichnung: String
)