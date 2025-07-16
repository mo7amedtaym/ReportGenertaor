package model

data class PoliceReport(
    val reportNumber: String,
    val officerName: String,
    val officerRank: PoliceRank,
    val department: PoliceDepartment,
    val reportDate: String,
    val reportTime: String
)
