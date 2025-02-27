package com.chef.foody.presentation.chef

import com.chef.foody.data.remote.CartInfo

data class QuotationList(
    val data: List<CartInfo> = emptyList()
)