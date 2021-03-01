package com.crypto.domain.model

data class CurrencyProfile(
    val tagline: String,
    val projectDetails: String,
    val links: List<Link>
)

data class Link(
    val name: String,
    val link: String
)