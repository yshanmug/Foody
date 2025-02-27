package com.chef.foody.presentation.customer

import com.chef.foody.data.remote.CustomerInfo

data class CustomerInfoList(
    val data: List<CustomerInfo> = emptyList()
)