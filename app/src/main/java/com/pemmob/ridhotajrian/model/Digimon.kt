package com.pemmob.ridhotajrian.model

data class DigimonResponse(
    val content: List<DigimonItem>
)


data class DigimonItem(
    val id: Int,
    val name: String,
    val image: String?
)

data class DigimonDetail(
    val id: Int,
    val name: String,
    val images: List<DigimonImage>?,
    val levels: List<DigimonLevel>?,
    val attributes: List<DigimonAttribute>?,
    val types: List<DigimonType>?
)

data class DigimonImage(val href: String)
data class DigimonLevel(val level: String)
data class DigimonAttribute(val attribute: String)
data class DigimonType(val type: String)