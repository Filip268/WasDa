import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kategorie(val id: Int, val kategorie: String)

@Serializable
data class Ort(val id: Int? = null, val name: String)

@Serializable
data class Fach(val id: Int, val fach_bezeichnung: String)

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
data class Artikel(
    @SerialName("artikel_id") val id: Int,
    @SerialName("artikel_name") val name: String,
    @SerialName("ablaufdatum") val ablaufdatum: String,
    @SerialName("kategorie_name") val kategorie: String,
    @SerialName("ort_name") val ort: String,
    @SerialName("fach_bezeichnung") val fach: String
)
