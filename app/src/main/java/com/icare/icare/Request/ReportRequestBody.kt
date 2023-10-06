package com.icare.icare.Request

data class ReportRequestBody(
    val userId: Long?,
    val reportTitle: String,
    val reportReason: String,
    val adminName: String,
    val adminReplyText: String,
    val reportStatus: Boolean
)