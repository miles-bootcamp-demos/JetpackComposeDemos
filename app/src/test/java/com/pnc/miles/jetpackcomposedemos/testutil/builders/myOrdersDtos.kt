package com.pnc.miles.jetpackcomposedemos.testutil.builders

import com.pnc.miles.jetpackcomposedemos.features.orders.data.MyOrderDto
import com.pnc.miles.jetpackcomposedemos.features.orders.data.MyOrderResponseDto

fun aMyOrdersResponse(
    customerId: Int = 11000,
    vararg orders: MyOrderDto
) = MyOrderResponseDto(
    customerId = customerId,
    page = 1,
    pageSize = 100,
    totalOrders = orders.size,
    orders = orders.toList()
)