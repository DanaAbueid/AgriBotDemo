package com.icare.icare.models

data class SubscriptionPlanDBO(
    val id: Long, // You might have other properties as well
    val name: String,
    val description: String,
    val price: Double
    // Add other properties as needed
)
