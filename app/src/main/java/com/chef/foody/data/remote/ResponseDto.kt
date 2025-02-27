package com.chef.foody.data.remote
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.domain.model.RentalChefOrderStatus
import com.chef.foody.domain.model.RentalOrderStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserLoginResponse(
    @Json(name = "response") val response: UserLoginResponseBody
)
@JsonClass(generateAdapter = true)
data class UserLoginResponseBody(
    @Json(name  = "status") val status: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "userInfo") val userInfo: UserInfo?,
    @Json(name = "authToken") val authToken: String?
)


@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name  = "first_name") val firstName: String,
    @Json(name  = "last_name") val lastName: String,
    @Json(name  = "email") val email: String,
    @Json(name  = "address") val address: String?
)
data class RegisterChefResponse(
    @Json(name = "response") val response: RegisterChefResponseBody
)
@JsonClass(generateAdapter = true)
data class RegisterChefResponseBody(
    @Json(name  = "status") val status: Boolean,
    @Json(name = "message") val message: String
)
data class UserRegistrationResponse(
    @Json(name = "response") val response: UserRegistrationResponseBody
)
@JsonClass(generateAdapter = true)
data class UserRegistrationResponseBody(
    @Json(name  = "status") val status: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "userInfo") val userInfo: UserInfo?,
    @Json(name = "authToken") val authToken: String?
)

@JsonClass(generateAdapter = true)
data class CustomerInfo(
    @Json(name = "order_id") val orderId: Int,
    @Json(name = "user_id") val userId: Int,
    @Json(name = "customer_first_name") val customerFirstName: String,
    @Json(name = "customer_last_name") val customerLastName: String,
    // beta API
    @Json(name = "cart_info") val cartInfo: List<CartInfo>?,
    @Json(name = "is_already_quoted") val isAlreadyQuoted: Boolean,
    @Json(name = "price") val price: Float,
    @Json(name = "preparation_time") val preparationTime: Int,
    @Json(name = "quotation_id") val quotationId: Int,
    @Json(name = "quotation_status_is_accepted") val quotationStatusIsAccepted: String,
    // beta API
    @Json(name = "type") val type: String? = null, // has type -- prepare and cook by chef
    @Json(name = "menu") val menu: String? = null, // has menu & user_note -- chef rental
    @Json(name = "user_note") val userNote: String? = null,
)
@JsonClass(generateAdapter = true)
data class CartInfo(
    @Json(name = "name") val name: String,
    @Json(name = "quantity") val quantity: Int
)
@JsonClass(generateAdapter = true)
data class ChefFindCustomerResponse(
    @Json(name = "response") val response: ChefFindCustomerResponseBody
)
@JsonClass(generateAdapter = true)
data class ChefFindCustomerResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "customer_info") val customerInfo: List<CustomerInfo>,
)


@JsonClass(generateAdapter = true)
data class ChefFindCustomerBetaResponse(
    @Json(name = "response") val response: ChefFindCustomerBetaResponseBody
)
@JsonClass(generateAdapter = true)
data class ChefFindCustomerBetaResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "customer_info") val customerInfo: List<CustomerInfo>,
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationResponse(
    @Json(name = "response") val response: ChefSendQuotationResponseBody
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationBetaResponse(
    @Json(name = "response") val response: ChefSendQuotationBetaResponseBody
)

@JsonClass(generateAdapter = true)
data class ChefSendQuotationBetaResponseBody (
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)


@JsonClass(generateAdapter = true)
data class CheckChefStatusResponse(
    @Json(name = "response") val response: CheckChefStatusResponseBody
)

@JsonClass(generateAdapter = true)
data class CheckChefStatusResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)

@JsonClass(generateAdapter = true)
data class CheckQuotationResponse(
    @Json(name = "response") val response: CheckQuotationResponseBody
)

@JsonClass(generateAdapter = true)
data class CheckQuotationResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json (name = "order_status") val orderStatus: OrderStatus
)
@JsonClass(generateAdapter = true)
data class OrderStatus(
    @Json (name = "order_id") val orderId: Int,
    @Json (name = "customer_first_name") val customerFirstName: String,
    @Json (name = "customer_last_name") val customerLastName: String,
    @Json (name = "customer_address") val customerAddress: String?,
    @Json (name = "order_status") val deliveryOrderStatus: DeliveryOrderStatus,
    @Json (name = "delivery_mode") val deliveryType: DeliveryType,
    @Json (name = "preparation_time") val preparationTime: Int,
    @Json (name = "price") val price: Int,
    @Json (name = "cart_info") val cartInfo: List<CartInfo>
)

@JsonClass(generateAdapter = true)
data class ChefUpdateOrderStatusResponse(
    @Json(name = "response") val response: ChefUpdateOrderStatusResponseBody
)
@JsonClass(generateAdapter = true)
data class ChefUpdateOrderStatusResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
)

@JsonClass(generateAdapter = true)
data class OrderFoodResponse(
    @Json(name = "response") val response: OrderFoodResponseBody
)
@JsonClass(generateAdapter = true)
data class OrderFoodResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
)

@JsonClass(generateAdapter = true)
data class LookingForQuotationResponse(
    @Json(name = "response") val response: LookingForQuotationResponseBody
)
@JsonClass(generateAdapter = true)
data class LookingForQuotationResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json(name = "quotation") val quotation: QuotationDetails
)

@JsonClass(generateAdapter = true)
data class QuotationDetails(
    @Json(name = "cart_info") val cart_info: List<CartInfo>,
    @Json(name = "quotation_info") val quotation_info: List<QuotationInfo>
)
@JsonClass(generateAdapter = true)
data class QuotationInfo(
    @Json(name = "quotation_id") val quotation_id: Int,
    @Json(name = "chef_first_name") val chef_first_name: String,
    @Json(name = "chef_last_name") val chef_last_name: String,
    @Json(name = "price") val price: Int,
    @Json(name = "preparation_time") val preparation_time: String,
    @Json (name = "delivery_mode") val delivery_mode: DeliveryType
)



@JsonClass(generateAdapter = true)
data class UpdateUserAddressResponse(
    @Json(name = "response") val response: UpdateUserAddressResponseBody
)
@JsonClass(generateAdapter = true)
data class UpdateUserAddressResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)



@JsonClass(generateAdapter = true)
data class RentChefResponse(
    @Json(name = "response") val response: RentChefResponseBody
)
@JsonClass(generateAdapter = true)
data class RentChefResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)


@JsonClass(generateAdapter = true)
data class LookingForChefRentQuotationResponse(
    @Json(name = "response") val response: LookingForChefRentQuotationResponseBody
)
@JsonClass(generateAdapter = true)
data class LookingForChefRentQuotationResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json(name = "quotation") val quotation: List<ChefQuotationDetails>
)
@JsonClass(generateAdapter = true)
data class ChefQuotationDetails(
    @Json(name = "quotation_id") val quotation_id: Int,
    @Json(name = "chef_first_name") val chef_first_name: String,
    @Json(name = "chef_last_name") val chef_last_name: String,
    @Json(name = "price") val price: Int,
    @Json(name = "menu") val menu : String,
    @Json(name = "preparation_time") val preparation_time: Int
)
@JsonClass(generateAdapter = true)
data class AcceptOfferQuotationResponse(
    @Json(name = "response") val response: AcceptOfferQuotationResponseBody
)

@JsonClass(generateAdapter = true)
data class AcceptOfferQuotationResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)

@JsonClass(generateAdapter = true)
data class CustomerCheckOrderStatusResponse(
    @Json(name = "response") val response: CustomerCheckOrderStatusResponseBody
)

@JsonClass(generateAdapter = true)
data class CustomerCheckOrderStatusResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json (name = "order_status") val orderStatus: CustomerCheckOrderStatus
)
@JsonClass(generateAdapter = true)
data class CustomerCheckOrderStatus(
    @Json (name = "chef_first_name") val chef_first_name: String,
    @Json (name = "chef_last_name") val chef_last_name: String,
    @Json (name = "chef_address") val chef_address: String?,
    @Json (name = "order_status") val order_status: DeliveryOrderStatus,
    @Json (name = "delivery_mode") val delivery_mode: DeliveryType,
)

@JsonClass(generateAdapter = true)
data class AcceptToRentalQuotationResponse(
    @Json(name = "response") val response: AcceptToRentalQuotationResponseBody
)

@JsonClass(generateAdapter = true)
data class AcceptToRentalQuotationResponseBody(
    @Json(name = "status") val status: Boolean,
    @Json(name = "message") val message: String
)

@JsonClass(generateAdapter = true)
data class CurrentRentalOrderStatusResponse(
    @Json(name = "response") val response: CurrentRentalOrderStatusResponseBody
)

@JsonClass(generateAdapter = true)
data class CurrentRentalOrderStatusResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json (name = "order_status") val orderStatus: CurrentRentalOrderStatus
)

@JsonClass(generateAdapter = true)
data class GetUserAddressResponse(
    @Json(name = "response") val response: GetUserAddressResponseBody
)

@JsonClass(generateAdapter = true)
data class GetUserAddressResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json (name = "address") val address: String
)
@JsonClass(generateAdapter = true)
data class CurrentRentalOrderStatus(
    @Json (name = "chef_first_name") val chef_first_name: String,
    @Json (name = "chef_last_name") val chef_last_name: String,
    @Json (name = "chef_address") val chef_address: String?,
    @Json (name = "order_status") val order_status: RentalOrderStatus
)

@JsonClass(generateAdapter = true)
data class GetClientRentalOrderStatusByChefResponse(
    @Json(name = "response") val response: GetClientRentalOrderStatusByChefResponseBody
)
@JsonClass(generateAdapter = true)
data class GetClientRentalOrderStatusByChefResponseBody(
    @Json (name = "status") val status: Boolean,
    @Json (name = "message") val message: String,
    @Json (name = "order_status") val orderStatus: ChefOrderStatus
)

@JsonClass(generateAdapter = true)
data class ChefOrderStatus(
    @Json (name = "order_id") val orderId: Int,
    @Json (name = "customer_first_name") val customerFirstName: String,
    @Json (name = "customer_last_name") val customerLastName: String,
    @Json (name = "customer_address") val customerAddress: String?,
    @Json (name = "user_note") val userNote: String,
    @Json (name = "order_status") val chefOrderStatus: RentalChefOrderStatus,
    @Json (name = "preparation_time") val preparationTime: Int,
    @Json (name = "price") val price: Int,
    @Json (name = "menu") val menu : String

)


//@JsonClass(generateAdapter = true)
//data class ChefUpdateOrderStatusResponse(
//    @Json(name = "response") val response: ChefUpdateOrderStatusResponseBody
//)
//@JsonClass(generateAdapter = true)
//data class ChefUpdateOrderStatusResponseBody(
//    @Json (name = "status") val status: Boolean,
//    @Json (name = "message") val message: String,
//)
