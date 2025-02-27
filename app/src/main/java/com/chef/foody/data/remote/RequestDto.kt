package com.chef.foody.data.remote

import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.RentalChefOrderStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserLoginRequest(
    @Json(name = "name") val name: String = "userLogin",
    @Json(name =  "param") val param: UserLoginParam
)
@JsonClass(generateAdapter = true)
data class UserLoginParam(
    @Json(name =  "email") val email: String,
    @Json(name =  "password") val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterChefRequest(
    @Json(name = "name") val name: String = "registerChef",
    @Json(name =  "param") val param: RegisterChefParam
)
@JsonClass(generateAdapter = true)
data class RegisterChefParam(
    @Json(name = "email") val email: String
)

@JsonClass(generateAdapter = true)
data class UserRegistrationRequest(
    @Json(name = "name") val name: String = "userRegistration",
    @Json(name =  "param") val param: UserRegistrationParam
)
@JsonClass(generateAdapter = true)
data class UserRegistrationParam(
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
)

@JsonClass(generateAdapter = true)
data class ChefFindCustomerRequest(
    @Json(name = "name") val name: String = "lookingForCustomers",
    @Json(name =  "param") val param: ChefFindCustomerParam
)

@JsonClass(generateAdapter = true)
data class ChefFindCustomerParam(
    @Json(name = "email") val email: String
)

@JsonClass(generateAdapter = true)
data class ChefFindCustomerBetaRequest(
    @Json(name = "name") val name: String = "lookingForCustomersBeta",
    @Json(name =  "param") val param: ChefFindCustomerBetaParam
)
@JsonClass(generateAdapter = true)
data class ChefFindCustomerBetaParam(
    @Json(name = "email") val email: String
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationRequest(
    @Json(name = "name") val name: String = "sendQuotation",
    @Json(name =  "param") val param: ChefSendQuotationParam,
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "price") val price: Float,
    @Json(name = "preparation_time") val preparationTime: Int,
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationBetaRequest(
    @Json(name = "name") val name: String = "sendQuotationBeta",
    @Json(name =  "param") val param: ChefSendQuotationBetaParam,
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationBetaParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "price") val price: Float,
    @Json(name = "preparation_time") val preparationTime: Int,
    // beta API
    @Json(name = "type") val type: String = "",
    @Json(name = "menu") val menu: String = "",
)

@JsonClass(generateAdapter = true)
data class CheckChefStatusRequest(
    @Json(name = "name") val name: String = "checkRegisteredChefStatus",
    @Json(name =  "param") val param: CheckChefStatusParam,
)

@JsonClass(generateAdapter = true)
data class CheckChefStatusParam(
    @Json(name = "email") val email: String,
)

@JsonClass(generateAdapter = true)
data class CheckQuotationStatusRequest(
    @Json(name = "name") val name: String = "getClientOrderStatusByChef",
    @Json(name =  "param") val param: CheckQuotationStatusParam,
)

@JsonClass(generateAdapter = true)
data class CheckQuotationStatusParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderID: Int
)



@JsonClass(generateAdapter = true)
data class ChefUpdateOrderStatusRequest(
    @Json(name = "name") val name: String = "updateOrderStatus",
    @Json(name = "param") val param: ChefUpdateOrderStatusParam
)

@JsonClass(generateAdapter = true)
data class ChefUpdateOrderStatusParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "order_status") val orderStatus: DeliveryOrderStatus
)
@JsonClass(generateAdapter = true)
data class RentChefUpdateOrderStatusRequest(
    @Json(name = "name") val name: String = "updateRentalOrderStatus",
    @Json(name = "param") val param: RentChefUpdateOrderStatusParam
)
@JsonClass(generateAdapter = true)
data class RentChefUpdateOrderStatusParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "order_status") val orderStatus: RentalChefOrderStatus
)

@JsonClass(generateAdapter = true)
data class OrderFoodRequest(
    @Json(name = "name") val name: String = "orderFood",
    @Json(name = "param") val param: OrderFoodParam
)

@JsonClass(generateAdapter = true)
data class OrderFoodParam(
    @Json(name = "email") val email: String,
    @Json(name = "cart") val cart: List<FoodInfo>,
    @Json(name = "delivery_mode") val delivery_mode: deliveryMode
)

@JsonClass(generateAdapter = true)
data class FoodInfo(
    @Json(name = "name") val name: String,
    @Json(name = "quantity") val quantity: Int,
    @Json(name = "type") val type: String
)

enum class deliveryMode{
    TAKE_AWAY,
    DOOR_DELIVERY
}

@JsonClass(generateAdapter = true)
data class LookingForQuotationRequest(
    @Json(name = "name") val name: String = "lookingForQuotation",
    @Json(name = "param") val param: LookingForQuotationParam
)

@JsonClass(generateAdapter = true)
data class LookingForQuotationParam(
    @Json(name = "email") val email: String,
)

@JsonClass(generateAdapter = true)
data class UpdateUserAddressRequest(
    @Json(name = "name") val name:String = "updateUserAddress",
    @Json(name = "param") val param: UpdateUserAddressParam
)

@JsonClass(generateAdapter = true)
data class UpdateUserAddressParam(
    @Json(name = "email") val email: String,
    @Json(name = "address") val address: String
)

@JsonClass(generateAdapter = true)
data class RentChefRequest(
    @Json(name = "name") val name:String = "rentChef",
    @Json(name = "param") val param: RentChefParam
)

@JsonClass(generateAdapter = true)
data class RentChefParam(
    @Json(name = "email") val email: String,
    @Json(name = "user_note") val user_note: String
)


@JsonClass(generateAdapter = true)
data class LookingForChefRentQuotationRequest(
    @Json(name = "name") val name: String = "lookingForChefRentQuotation",
    @Json(name = "param") val param: LookingForChefRentQuotationParam
)
@JsonClass(generateAdapter = true)
data class LookingForChefRentQuotationParam(
    @Json(name = "email") val email: String,
)

data class AcceptOfferQuotationRequest(
    @Json(name = "name") val name: String = "acceptToQuotation",
    @Json(name =  "param") val param: AcceptOfferQuotationParam,
)

@JsonClass(generateAdapter = true)
data class AcceptOfferQuotationParam(
    @Json(name = "email") val email: String,
    @Json(name = "quotation_id") val quotationId: Int,
    @Json(name = "is_accepted") val isAccepted: Boolean,
)

@JsonClass(generateAdapter = true)
data class CustomerCheckOrderStatusRequest(
    @Json(name = "name") val name: String = "currentOrderStatus",
    @Json(name =  "param") val param: CustomerCheckOrderStatusParam
)

@JsonClass(generateAdapter = true)
data class CustomerCheckOrderStatusParam(
    @Json(name = "email") val email: String,
)
@JsonClass(generateAdapter = true)
data class AcceptToRentalQuotationRequest(
    @Json(name = "name") val name: String = "acceptToRentalQuotation",
    @Json(name =  "param") val param: AcceptToRentalQuotationParam,
)

@JsonClass(generateAdapter = true)
data class AcceptToRentalQuotationParam(
    @Json(name = "email") val email: String,
    @Json(name = "quotation_id") val quotationId: Int,
    @Json(name = "is_accepted") val isAccepted: Boolean,
)

@JsonClass(generateAdapter = true)
data class CurrentRentalOrderStatusRequest(
    @Json(name = "name") val name: String = "currentRentalOrderStatus",
    @Json(name =  "param") val param: CurrentRentalOrderStatusParam
)

@JsonClass(generateAdapter = true)
data class CurrentRentalOrderStatusParam(
    @Json(name = "email") val email: String,
)


@JsonClass(generateAdapter = true)
data class GetUserAddressRequest(
    @Json(name = "name") val name: String = "getUserAddress",
    @Json(name =  "param") val param: GetUserAddressRequestParam
)

@JsonClass(generateAdapter = true)
data class GetUserAddressRequestParam(
    @Json(name = "email") val email: String,
)
//{
//    "name":"currentRentalOrderStatus",
//    "param":{
//    "email": "kishore.rathika5304@gmail.com"
//}
//}

@JsonClass(generateAdapter = true)
data class GetClientRentalOrderStatusByChefRequest(
    @Json(name = "name") val name: String = "getClientRentalOrderStatusByChef",
    @Json(name =  "param") val param: GetClientRentalOrderStatusByChefParam,
)

@JsonClass(generateAdapter = true)
data class GetClientRentalOrderStatusByChefParam(
    @Json(name = "email") val email: String,
    @Json(name = "order_id") val orderID: Int
)





