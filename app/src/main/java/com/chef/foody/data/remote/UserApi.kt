package com.chef.foody.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onUserLogin(@Body userLoginRequest: UserLoginRequest): UserLoginResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun registerChef(@Body registerChefRequest: RegisterChefRequest, @Header("Authorization") authToken: String): RegisterChefResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onUserRegistration(@Body userRegistrationRequest: UserRegistrationRequest): UserRegistrationResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onChefFindCustomer(@Body chefFindCustomerRequest: ChefFindCustomerRequest, @Header("Authorization") authToken: String): ChefFindCustomerResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onChefSendQuotation(@Body chefSendQuotationRequest: ChefSendQuotationRequest, @Header("Authorization") authToken: String): ChefSendQuotationResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onChefFindCustomerBeta(@Body chefFindCustomerBetaRequest: ChefFindCustomerBetaRequest, @Header("Authorization") authToken: String): ChefFindCustomerBetaResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onChefSendQuotationBeta(@Body chefSendQuotationBetaRequest: ChefSendQuotationBetaRequest, @Header("Authorization") authToken: String): ChefSendQuotationBetaResponse


    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onCheckChefStatus(@Body checkChefStatusRequest: CheckChefStatusRequest, @Header("Authorization") authToken: String): CheckChefStatusResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onChefUpdateOrderStatus(@Body chefUpdateOrderStatusRequest: ChefUpdateOrderStatusRequest, @Header("Authorization") authToken: String):ChefUpdateOrderStatusResponse

    //copy
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onRentChefUpdateOrderStatus(@Body rentChefOrderUpdateOrderStatusRequest: RentChefUpdateOrderStatusRequest, @Header("Authorization") authToken: String): ChefUpdateOrderStatusResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onRentChefCheckOrderStatus(@Body getClientRentalOrderStatusByChefRequest: GetClientRentalOrderStatusByChefRequest, @Header("Authorization") authToken: String): GetClientRentalOrderStatusByChefResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onCheckQuotationStatus(@Body checkQuotationStatusRequest: CheckQuotationStatusRequest, @Header("Authorization") authToken: String): CheckQuotationResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onOrderFood(@Body orderFoodRequest: OrderFoodRequest, @Header("Authorization") authToken: String): OrderFoodResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onLookingForQuotation(@Body lookingForQuotationRequest: LookingForQuotationRequest, @Header("Authorization") authToken: String): LookingForQuotationResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onUpdateUserAddress(@Body updateUserAddressRequest: UpdateUserAddressRequest, @Header("Authorization") authToken: String): UpdateUserAddressResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onRentAChef(@Body rentChef: RentChefRequest, @Header("Authorization") authToken: String): RentChefResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onLookingForChefRentQuotation(@Body lookingForChefRentQuotation: LookingForChefRentQuotationRequest, @Header("Authorization") authToken: String): LookingForChefRentQuotationResponse


    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onAcceptOfferQuotation(@Body acceptOfferQuotationRequest: AcceptOfferQuotationRequest, @Header("Authorization") authToken: String): AcceptOfferQuotationResponse

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onCustomerCheckOrderStatus(@Body customerCheckOrderStatusRequest: CustomerCheckOrderStatusRequest, @Header("Authorization") authToken: String): CustomerCheckOrderStatusResponse
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onAcceptToRentalQuotation(@Body acceptToRentalQuotationRequest: AcceptToRentalQuotationRequest, @Header("Authorization") authToken: String): AcceptToRentalQuotationResponse
    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun onCurrentRentalOrderStatus(@Body currentRentalOrderStatusRequest: CurrentRentalOrderStatusRequest, @Header("Authorization") authToken: String): CurrentRentalOrderStatusResponse


    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun getUserAddress(@Body getUserAddressRequest: GetUserAddressRequest, @Header("Authorization") authToken: String): GetUserAddressResponse


}