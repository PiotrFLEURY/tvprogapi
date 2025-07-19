package fr.piotrfleury.tvprogapi.domain.entities

enum class ChannelPackageEntity {
    TNT, FR, ALL;

    companion object {
        fun fromString(type: String): ChannelPackageEntity {
            return when (type.uppercase()) {
                "TNT" -> TNT
                "FR" -> FR
                else -> ALL
            }
        }
    }
}