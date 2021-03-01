package com.crypto.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyProfileResponse(
    val data: Data
) {

    @JsonClass(generateAdapter = true)
    data class Data(
        val profile: Profile
    )

    @JsonClass(generateAdapter = true)
    data class Profile(
        val general: General
    )

    @JsonClass(generateAdapter = true)
    data class General(
        val overview: Overview
    )

    @JsonClass(generateAdapter = true)
    data class Overview(
        val tagline: String,
        @Json(name = "project_details") val projectDetails: String,
        @Json(name = "official_links") val officialLinks: List<Link>
    )

    @JsonClass(generateAdapter = true)
    data class Link(
        val name: String,
        val link: String
    )

}