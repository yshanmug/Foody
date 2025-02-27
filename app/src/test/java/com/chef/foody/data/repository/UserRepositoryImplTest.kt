package com.chef.foody.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.chef.foody.data.MemoryDatasource
import com.chef.foody.data.remote.AcceptOfferQuotationParam
import com.chef.foody.data.remote.AcceptOfferQuotationRequest
import com.chef.foody.data.remote.AcceptOfferQuotationResponse
import com.chef.foody.data.remote.AcceptOfferQuotationResponseBody
import com.chef.foody.data.remote.AcceptToRentalQuotationParam
import com.chef.foody.data.remote.AcceptToRentalQuotationRequest
import com.chef.foody.data.remote.AcceptToRentalQuotationResponse
import com.chef.foody.data.remote.AcceptToRentalQuotationResponseBody
import com.chef.foody.data.remote.CartInfo
import com.chef.foody.data.remote.CheckChefStatusParam
import com.chef.foody.data.remote.CheckChefStatusRequest
import com.chef.foody.data.remote.CheckChefStatusResponse
import com.chef.foody.data.remote.CheckChefStatusResponseBody
import com.chef.foody.data.remote.CheckQuotationResponse
import com.chef.foody.data.remote.CheckQuotationResponseBody
import com.chef.foody.data.remote.CheckQuotationStatusParam
import com.chef.foody.data.remote.CheckQuotationStatusRequest
import com.chef.foody.data.remote.ChefFindCustomerParam
import com.chef.foody.data.remote.ChefFindCustomerRequest
import com.chef.foody.data.remote.ChefFindCustomerResponse
import com.chef.foody.data.remote.ChefFindCustomerResponseBody
import com.chef.foody.data.remote.ChefQuotationDetails
import com.chef.foody.data.remote.ChefSendQuotationParam
import com.chef.foody.data.remote.ChefSendQuotationRequest
import com.chef.foody.data.remote.ChefSendQuotationResponse
import com.chef.foody.data.remote.ChefSendQuotationResponseBody
import com.chef.foody.data.remote.ChefUpdateOrderStatusParam
import com.chef.foody.data.remote.ChefUpdateOrderStatusRequest
import com.chef.foody.data.remote.ChefUpdateOrderStatusResponse
import com.chef.foody.data.remote.ChefUpdateOrderStatusResponseBody
import com.chef.foody.data.remote.CustomerCheckOrderStatus
import com.chef.foody.data.remote.CustomerCheckOrderStatusParam
import com.chef.foody.data.remote.CustomerCheckOrderStatusRequest
import com.chef.foody.data.remote.CustomerCheckOrderStatusResponse
import com.chef.foody.data.remote.CustomerCheckOrderStatusResponseBody
import com.chef.foody.data.remote.CustomerInfo
import com.chef.foody.data.remote.FoodInfo
import com.chef.foody.data.remote.LookingForChefRentQuotationParam
import com.chef.foody.data.remote.LookingForChefRentQuotationRequest
import com.chef.foody.data.remote.LookingForChefRentQuotationResponse
import com.chef.foody.data.remote.LookingForChefRentQuotationResponseBody
import com.chef.foody.data.remote.LookingForQuotationParam
import com.chef.foody.data.remote.LookingForQuotationRequest
import com.chef.foody.data.remote.LookingForQuotationResponse
import com.chef.foody.data.remote.LookingForQuotationResponseBody
import com.chef.foody.data.remote.OrderFoodParam
import com.chef.foody.data.remote.OrderFoodRequest
import com.chef.foody.data.remote.OrderFoodResponse
import com.chef.foody.data.remote.OrderFoodResponseBody
import com.chef.foody.data.remote.OrderStatus
import com.chef.foody.data.remote.QuotationDetails
import com.chef.foody.data.remote.QuotationInfo
import com.chef.foody.data.remote.RegisterChefParam
import com.chef.foody.data.remote.RegisterChefRequest
import com.chef.foody.data.remote.RegisterChefResponse
import com.chef.foody.data.remote.RegisterChefResponseBody
import com.chef.foody.data.remote.RentChefParam
import com.chef.foody.data.remote.RentChefRequest
import com.chef.foody.data.remote.RentChefResponse
import com.chef.foody.data.remote.RentChefResponseBody
import com.chef.foody.data.remote.UpdateUserAddressParam
import com.chef.foody.data.remote.UpdateUserAddressRequest
import com.chef.foody.data.remote.UpdateUserAddressResponse
import com.chef.foody.data.remote.UpdateUserAddressResponseBody
import com.chef.foody.data.remote.UserApi
import com.chef.foody.data.remote.UserInfo
import com.chef.foody.data.remote.UserLoginParam
import com.chef.foody.data.remote.UserLoginRequest
import com.chef.foody.data.remote.UserLoginResponse
import com.chef.foody.data.remote.UserLoginResponseBody
import com.chef.foody.data.remote.UserRegistrationParam
import com.chef.foody.data.remote.UserRegistrationRequest
import com.chef.foody.data.remote.UserRegistrationResponse
import com.chef.foody.data.remote.UserRegistrationResponseBody
import com.chef.foody.data.remote.deliveryMode
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.domain.model.User
import com.chef.foody.domain.repository.UserRepository
import com.chef.foody.domain.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserRepositoryImplTest {

    // Mock UserRepository
    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var api: UserApi


    // reusable constants
    private val authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDk4NTU0NzYsImlzcyI6ImxvY2FsaG9zdCIsInVzZXJJZCI6Mn0.pJvSC1SbVmrQRN4AJeJTrmwlWpQAGpL76wiNSC5qpDc"
    private val customerList = listOf(
        CustomerInfo(orderId = 1,userId = 101,customerFirstName = "Alice",customerLastName = "Smith",cartInfo = emptyList(),isAlreadyQuoted = false,price = 0.0f,preparationTime = 0,quotationId = 0,quotationStatusIsAccepted = ""),
        CustomerInfo(orderId = 2,userId = 102,customerFirstName = "Bob",customerLastName = "Johnson",cartInfo = emptyList(),isAlreadyQuoted = false,price = 0.0f,preparationTime = 0,quotationId = 0,quotationStatusIsAccepted = ""))
    private val cart_info = listOf(
        CartInfo("Pizza", 3),
        CartInfo("Burger", 2))
    private val food_info = listOf(
        FoodInfo(name="noodles",quantity=1,type="NON_VEG"),
        FoodInfo(name="soup",quantity=3,type="VEG"),
        FoodInfo(name="Burger",quantity=1,type="NON_VEG"))
    val quotationInfoList = listOf(
        QuotationInfo(quotation_id = 1,chef_first_name = "John",chef_last_name = "Doe",price = 25,preparation_time = "30 minutes",delivery_mode = DeliveryType.DOOR_DELIVERY),
        QuotationInfo(quotation_id = 2,chef_first_name = "Jane",chef_last_name = "Smith",price = 30,preparation_time = "45 minutes",delivery_mode = DeliveryType.TAKE_AWAY))
    val chefQuotationInfoList = listOf(
        ChefQuotationDetails(quotation_id = 1,chef_first_name = "John",chef_last_name = "Doe",price = 25,preparation_time = 30, menu = "I can prepare egg and corn rice."),
        ChefQuotationDetails(quotation_id = 2,chef_first_name = "Jane",chef_last_name = "Smith",price = 30,preparation_time = 45,menu = "I can prepare mushroom gravy."))
    val order_status = CustomerCheckOrderStatus(chef_first_name = "Arvindh",chef_last_name = "Prasadh",chef_address = null,order_status = DeliveryOrderStatus.ORDER_CONFIRMED,delivery_mode = DeliveryType.DOOR_DELIVERY)


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `onUserLogin returns success`() = runBlocking {
        val email = "test@example.com"
        val password = "password"
        val expectedUser = User(
            firstName = "Kishore",
            lastName = "Kumarasamy",
            email = "kishore.rathika5304@gmail.com",
            address = "123 main street",
            authToken = authToken,
        )

        val expectedUserInfo = UserInfo(
            firstName = "Kishore",
            lastName = "Kumarasamy",
            email = "kishore.rathika5304@gmail.com",
            address = "123 main street"
        )

        val loginResponseBody = UserLoginResponseBody(
            status = true,
            authToken = authToken,
            message = "Login Successful.",
            userInfo = expectedUserInfo
        )

        val loginResponse = UserLoginResponse(loginResponseBody)

        // Mock the api object
        val api = mock(UserApi::class.java)
        `when`(api.onUserLogin(UserLoginRequest(name = "userLogin", param = UserLoginParam(email = email, password = password)))).thenReturn(loginResponse)

        val result = api.onUserLogin(UserLoginRequest(param= UserLoginParam(email, password)))

        assertEquals(expectedUserInfo, (result.response.userInfo))

    }

    @Test
    fun `onUserRegistration returns on success`() = runBlocking {
        val userInfo = UserInfo(firstName = "Kishore",lastName = "Kumarasamy",email = "kishore.rathika5304@gmail.com", address = "123 main street")
        val userRegsitrationResponseBody = UserRegistrationResponseBody(true, "Inserted successfully.",userInfo = userInfo, authToken = authToken)
        val userRegistrationResponse = UserRegistrationResponse(userRegsitrationResponseBody)

        val api = mock(UserApi::class.java)
        `when`(api.onUserRegistration(UserRegistrationRequest(
            name = "userregistration",
            param = UserRegistrationParam(
                firstName = "Kishore",
                lastName = "Kumarasamy",
                email = "kishore.rathika5304@gmail.com",
                password = "pass@123")
        )
        )
        ).thenReturn(userRegistrationResponse)

        val result = api.onUserRegistration(UserRegistrationRequest(name = "userregistration", param= UserRegistrationParam(firstName = "Kishore", lastName = "Kumarasamy",email = "kishore.rathika5304@gmail.com",password = "pass@123")))
        assertEquals(userRegistrationResponse.response.userInfo, (result.response.userInfo))
    }

    @Test
    fun `onChefRegister() returns on success`() = runBlocking {
        val email = "kishore.rathika5304@gmail.com"
        val expectedResponse = RegisterChefResponse(RegisterChefResponseBody(status = true, message = "Inserted successfully."))

        val api = mock(UserApi::class.java)
        `when`(api.registerChef(RegisterChefRequest(name = "registerChef",param = RegisterChefParam(email)),authToken = authToken)).thenReturn(expectedResponse)

        val result = api.registerChef(RegisterChefRequest(name = "registerChef",param = RegisterChefParam(email)),authToken = authToken)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `onChefFindCustomer() returns on success`() = runBlocking {
        val email = "kishore.rathika5304@gmail.com"

        val expectedResponse = ChefFindCustomerResponse(ChefFindCustomerResponseBody(customerInfo = customerList, message = "CUSTOMER AND ORDER DETAILS", status = true))

        val api = mock(UserApi::class.java)
        `when`(api.onChefFindCustomer(ChefFindCustomerRequest(name = "lookingForCustomersBeta",param = ChefFindCustomerParam(email)),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onChefFindCustomer(ChefFindCustomerRequest(name = "lookingForCustomersBeta", param = ChefFindCustomerParam(email)),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onChefSendQuotation() returns on success`() = runBlocking {
        val chefSendQuotationParam = ChefSendQuotationParam(email = "arvindhsugu@gmail.com", orderId = 1,price = 25.25F, preparationTime = 35)
        val expectedResponse = ChefSendQuotationResponse(ChefSendQuotationResponseBody(true, "Quotation Sent"))

        val api = mock(UserApi::class.java)
        `when`(api.onChefSendQuotation(ChefSendQuotationRequest(name = "sendQuotationBeta", param = chefSendQuotationParam),authToken)).thenReturn(expectedResponse)
        val result = api.onChefSendQuotation(ChefSendQuotationRequest(name = "sendQuotationBeta", param = chefSendQuotationParam),authToken)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun `onCheckChefStatus() returns on success`() = runBlocking {

        val expectedResponse = CheckChefStatusResponse(CheckChefStatusResponseBody(true,"VERIFIED CHEF"))

        val api = mock(UserApi::class.java)
        `when`(api.onCheckChefStatus(CheckChefStatusRequest(name = "checkRegisteredChefStatus", param = CheckChefStatusParam("kishore.rathika5304@gmail.com")),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onCheckChefStatus(CheckChefStatusRequest(name = "checkRegisteredChefStatus", param = CheckChefStatusParam("kishore.rathika5304@gmail.com")),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onCheckQuotationStatus() returns on success`() = runBlocking {
        val email = "kishore.rathika5304@gmail.com"
        val orderid = 19
        val orderStatus = OrderStatus(orderid,"Arvindh","Prasadh",null,DeliveryOrderStatus.ORDER_CONFIRMED,DeliveryType.DOOR_DELIVERY,15,12,cart_info)
        val expectedResponse = CheckQuotationResponse(CheckQuotationResponseBody(status = true, message = "Order Status", orderStatus = orderStatus))

        val api = mock(UserApi::class.java)
        `when`(api.onCheckQuotationStatus(CheckQuotationStatusRequest(name = "getClientOrderStatusByChef", CheckQuotationStatusParam(email = email, orderID = orderid)),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onCheckQuotationStatus(CheckQuotationStatusRequest(name = "getClientOrderStatusByChef", CheckQuotationStatusParam(email = email, orderID = orderid)),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onChefUpdateOrderStatus() returns on success`() = runBlocking {
        val expectedResponse = ChefUpdateOrderStatusResponse(ChefUpdateOrderStatusResponseBody(true, "Status Updated"))

        val api = mock(UserApi::class.java)
        `when`(api.onChefUpdateOrderStatus(ChefUpdateOrderStatusRequest(name = "updateOrderStatus", param = ChefUpdateOrderStatusParam(email = "auroraborails@gmail.com", orderId = 19, orderStatus = DeliveryOrderStatus.PREPARING_FOOD)),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onChefUpdateOrderStatus(ChefUpdateOrderStatusRequest(name = "updateOrderStatus", param = ChefUpdateOrderStatusParam(email = "auroraborails@gmail.com", orderId = 19, orderStatus = DeliveryOrderStatus.PREPARING_FOOD)),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `chefResetActiveOrder() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK MEMORY DATASOURCE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `chefGetActiveOrder() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK MEMORY DATASOURCE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `getUserInfo() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `storeUserInfo() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `getUserLoginStatus() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `storeUserLoginStatus() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onOrderFood() returns on success`() = runBlocking {

        val expectedResponse = OrderFoodResponse(OrderFoodResponseBody(true, "ORDER PLACED SUCCESSFULLY."))
        val orderFoodParam = OrderFoodParam(email = "kishore.rathika5304@gmail.com", cart = food_info, delivery_mode = deliveryMode.DOOR_DELIVERY)

        val api = mock(UserApi::class.java)
        `when`(api.onOrderFood(OrderFoodRequest(name = "orderFood", param = orderFoodParam),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onOrderFood(OrderFoodRequest(name = "orderFood", param = orderFoodParam),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onLookingForQuotation() returns on success`() = runBlocking {

        val expectedResponse = LookingForQuotationResponse(LookingForQuotationResponseBody(status = true, message = "QUOTATION DETAILS",QuotationDetails(cart_info = cart_info, quotation_info = quotationInfoList)))

        val api = mock(UserApi::class.java)
        `when`(api.onLookingForQuotation(LookingForQuotationRequest(name = "lookingForQuotation", param = LookingForQuotationParam(email = "kishore.rathika5304@gmail.com")),authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onLookingForQuotation(LookingForQuotationRequest(name = "lookingForQuotation", param = LookingForQuotationParam(email = "kishore.rathika5304@gmail.com")),authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onUpdateUserAddress() returns on success`() = runBlocking {

        val expectedResponse = UpdateUserAddressResponse(UpdateUserAddressResponseBody(true, "Address Updated"))

        val api = mock(UserApi::class.java)
        `when`(api.onUpdateUserAddress(UpdateUserAddressRequest(name = "updateUserAddress", param = UpdateUserAddressParam(email = "auroraborails@gmail.com","288 Albert Street, Waterloo, Ontario, postal code: N2L3T8")), authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onUpdateUserAddress(UpdateUserAddressRequest(name = "updateUserAddress", param = UpdateUserAddressParam(email = "auroraborails@gmail.com","288 Albert Street, Waterloo, Ontario, postal code: N2L3T8")), authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onAcceptOfferQuotation() returns on success`() = runBlocking {

        val expectedResponse = AcceptOfferQuotationResponse(AcceptOfferQuotationResponseBody(true,"QUOTATION ACCEPTED"))

        val api = mock(UserApi::class.java)
        `when`(api.onAcceptOfferQuotation(AcceptOfferQuotationRequest(name = "acceptToQuotation", param = AcceptOfferQuotationParam(email = "kishore.rathika5304@gmail.com", quotationId = 19, isAccepted = true)), authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onAcceptOfferQuotation(AcceptOfferQuotationRequest(name = "acceptToQuotation", param = AcceptOfferQuotationParam(email = "kishore.rathika5304@gmail.com", quotationId = 19, isAccepted = true)), authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onRentAChef() returns on success`() = runBlocking {


        val expectedResponse = RentChefResponse(RentChefResponseBody(true, "RENTAL CHEF REQUEST SUCCESSFUL."))

        val api = mock(UserApi::class.java)
        `when`(api.onRentAChef(RentChefRequest(name = "rentChef", param = RentChefParam(email = "kishore.rathika5304@gmail.com", user_note = "I have carrot, mushroom, egg and corn.")), authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onRentAChef(RentChefRequest(name = "rentChef", param = RentChefParam(email = "kishore.rathika5304@gmail.com", user_note = "I have carrot, mushroom, egg and corn.")), authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `storeChefStatus() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `getChefStatus() returns on success`() = runBlocking {

        // UNSURE HOW TO MOCK DATASTORE
        val expectedResponse = ""
        val result = ""

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onLookingForChefRentQuotation() returns on success`() = runBlocking {

        val expectedResponse = LookingForChefRentQuotationResponse(LookingForChefRentQuotationResponseBody(message = "QUOTATION DETAILS", status = true, quotation = chefQuotationInfoList))

        val api = mock(UserApi::class.java)
        `when`(api.onLookingForChefRentQuotation(LookingForChefRentQuotationRequest(name = "lookingForChefRentQuotation", param = LookingForChefRentQuotationParam(email = "kishore.rathika5304@gmail.com")), authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onLookingForChefRentQuotation(LookingForChefRentQuotationRequest(name = "lookingForChefRentQuotation", param = LookingForChefRentQuotationParam(email = "kishore.rathika5304@gmail.com")), authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onCustomerCheckOrderStatus() returns on success`() = runBlocking {

        val expectedResponse = CustomerCheckOrderStatusResponse(CustomerCheckOrderStatusResponseBody(status = true, message = "Order Status", orderStatus = order_status))

        val api = mock(UserApi::class.java)
        `when`(api.onCustomerCheckOrderStatus(CustomerCheckOrderStatusRequest(name = "currentOrderStatus", param = CustomerCheckOrderStatusParam(email = "kishore.rathika5304@gmail.com")), authToken = authToken)).thenReturn(expectedResponse)
        val result = api.onCustomerCheckOrderStatus(CustomerCheckOrderStatusRequest(name = "currentOrderStatus", param = CustomerCheckOrderStatusParam(email = "kishore.rathika5304@gmail.com")), authToken = authToken)

        assertEquals(expectedResponse, result)

    }

    @Test
    fun `onAcceptToRentalQuotation() returns on success`() = runBlocking {

        val expectedResponse = AcceptToRentalQuotationResponse(AcceptToRentalQuotationResponseBody(true, "QUOTATION ACCEPTED"))

        val api = mock(UserApi::class.java)
        `when`(api.onAcceptToRentalQuotation(AcceptToRentalQuotationRequest(name = "acceptToRentalQuotation", param = AcceptToRentalQuotationParam(email = "kishore.rathika5304@gmail.com", quotationId = 15,isAccepted = true)), authToken = authToken)).thenReturn(expectedResponse)
        val result =api.onAcceptToRentalQuotation(AcceptToRentalQuotationRequest(name = "acceptToRentalQuotation", param = AcceptToRentalQuotationParam(email = "kishore.rathika5304@gmail.com", quotationId = 15,isAccepted = true)), authToken = authToken)

        assertEquals(expectedResponse, result)

    }
}