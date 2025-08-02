package model

import kotlinx.serialization.Serializable

@Serializable
data class AllData(
    val date: String,
    val time: String,
    val pro: String,
    val sec: String,
    val num: String,
    val year: String,
    val department: String,
    val cap_title: String,
    val cap_name: String,
    val d_date: String,
    val d_time: String,
    val car_type: String,
    val plate: String,
    val unit: String,
    val owner: String,
    val name: String,
    val address: String,
    val age: String,
    val job: String,
    val id: String,
    val questions: List<QandAData>,
    val decisions: List<String>,
    val instructions: List<String>,
    val action: Int,
)