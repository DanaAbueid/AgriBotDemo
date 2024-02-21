package com.icare.icare.models

data class ReportRequestBody(
    val userId: Long?,
    val title: String,
    val reason: String,
    val reported: String,
    val repText: String
)
