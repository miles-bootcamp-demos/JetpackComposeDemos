package com.pnc.miles.jetpackcomposedemos.features.orders.domain

interface OrderRepository {
    suspend fun getOrders(): List<Order>
}