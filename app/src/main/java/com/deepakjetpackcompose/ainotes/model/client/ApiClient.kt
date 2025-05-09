package com.deepakjetpackcompose.ainotes.model.client


import com.deepakjetpackcompose.ainotes.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


object ApiClient{
    val apiKey =BuildConfig.API_KEY
    val client: HttpClient= HttpClient{
        install(ContentNegotiation){
            json(json = Json{ignoreUnknownKeys=true})
        }
    }

    suspend fun getSummary(content:String): GeminiResponse{
        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(
                            text = "Summarize the following make sure you only summaries it done use ** in the response please \n\n$content"
                        )
                    )
                )
            )
        )
        val response=client.post ("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey"){
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        println("Status: ${response.status}")
        println("Response Text: ${response.bodyAsText()}")
        return  response.body<GeminiResponse>()
    }


    suspend fun getTranslation(content:String,language:String):GeminiResponse{
        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(
                            text = "Translate the following in $language make sure just translate donot explain it and add multiple thing from your side just translate it \n\n $content"
                        )
                    )
                )
            )
        )
        return try {
            val response =
                client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            println("Status: ${response.status}")
            println("Response Text: ${response.bodyAsText()}")
             response.body<GeminiResponse>()
        }catch (e : Exception){
            GeminiResponse(null)
        }
    }

    suspend fun getMeaning(content:String):GeminiResponse{
        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(
                            text = "Explain the meaning of this word $content\n\n only explain the meaning in one line and 1 example."
                        )
                    )
                )
            )
        )
        return try {
            val response =
                client.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey") {
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            println("Status: ${response.status}")
            println("Response Text: ${response.bodyAsText()}")
            response.body<GeminiResponse>()
        }catch (e : Exception){
            GeminiResponse(null)
        }
    }


}

fun main() = runBlocking {
    val res= ApiClient.getSummary("Newton's law of inertia, also known as Newton's first law of motion, states that an object at rest remains at rest, and an object in motion remains in motion at constant speed and in a straight line unless acted on by an unbalanced force.\n" +
            " This principle, which is fundamental to classical mechanics, was first formulated by Galileo Galilei for horizontal motion on Earth and later generalized by René Descartes.\n" +
            " Newton's first law, or the law of inertia, is a cornerstone of physics, providing the basis for understanding how objects move or remain stationary when forces act upon them.\n" +
            " The law of inertia is less intuitively obvious than it might seem; in everyday experience, objects that are not being pushed tend to come to rest, but this is attributed to the presence of unbalanced forces such as friction and air resistance.\n" +
            " For Galileo, the principle of inertia was crucial for explaining how Earth could be spinning on its axis and orbiting the Sun without us sensing that motion, since we are in motion together with Earth and our natural tendency is to retain that motion.\n" +
            " In the Newtonian formulation, the common observation that bodies that are not pushed tend to come to rest is explained by the fact that they have unbalanced forces acting on them.\n" +
            " The law of inertia is not just a statement of the obvious; it was once a central issue of scientific contention and has since become a fundamental assumption of classical mechanics.\n" +
            " Newton's first law helps to provide the answer to how it is possible that if Earth is really spinning on its axis and orbiting the Sun, we do not sense that motion.\n" +
            " By the time Newton had sorted out all the details, it was possible to accurately account for the small deviations from this picture caused by the fact that the motion of Earth’s surface is not uniform motion in a straight line.")
        .candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?:"No summary found"

    println(res)

}