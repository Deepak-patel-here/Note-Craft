package com.deepakjetpackcompose.ainotes.model.client

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null
)

@Serializable
data class Candidate(
    val content: ContentResult? = null
)

@Serializable
data class ContentResult(
    val parts: List<PartResult>? = null
)

@Serializable
data class PartResult(
    val text: String? = null
)
