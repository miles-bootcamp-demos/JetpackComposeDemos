package com.pnc.miles.jetpackcomposedemos.testutil.builders

import com.pnc.miles.jetpackcomposedemos.features.orders.domain.LineItem

fun aLineItem(
    id: Int = 1,
    productName: String = "Widget",
    orderQty: Int = 1,
    unitPrice: Double = 10.0,
    lineTotal: Double = orderQty * unitPrice    // derived from earlier parameters
) = LineItem(id, productName, orderQty, unitPrice, lineTotal)
