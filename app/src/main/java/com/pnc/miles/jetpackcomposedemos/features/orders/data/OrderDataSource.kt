package com.pnc.miles.jetpackcomposedemos.features.orders.data

interface OrderDataSource {
    suspend fun getOrders(): List<OrderLineItemDto>
}