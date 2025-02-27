package com.chef.foody.presentation.chef

import com.chef.foody.data.remote.QuotationInfo

data class QuotationInfoList(
    val data: List<QuotationInfo> = emptyList()
)