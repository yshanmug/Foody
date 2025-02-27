package com.chef.foody.data.repository

import com.chef.foody.data.remote.AcceptOfferQuotationParam
import com.chef.foody.data.remote.AcceptToRentalQuotationParam
import com.chef.foody.data.remote.CartInfo
import com.chef.foody.data.remote.CheckChefStatusParam
import com.chef.foody.data.remote.CheckQuotationStatusParam
import com.chef.foody.data.remote.ChefFindCustomerBetaParam
import com.chef.foody.data.remote.ChefFindCustomerParam
import com.chef.foody.data.remote.ChefOrderStatus
import com.chef.foody.data.remote.ChefQuotationDetails
import com.chef.foody.data.remote.ChefSendQuotationBetaParam
import com.chef.foody.data.remote.ChefSendQuotationParam
import com.chef.foody.data.remote.ChefUpdateOrderStatusParam
import com.chef.foody.data.remote.CurrentRentalOrderStatus
import com.chef.foody.data.remote.CurrentRentalOrderStatusParam
import com.chef.foody.data.remote.CustomerCheckOrderStatus
import com.chef.foody.data.remote.CustomerCheckOrderStatusParam
import com.chef.foody.data.remote.CustomerInfo
import com.chef.foody.data.remote.GetClientRentalOrderStatusByChefParam
import com.chef.foody.data.remote.LookingForQuotationParam
import com.chef.foody.data.remote.OrderFoodParam
import com.chef.foody.data.remote.OrderStatus
import com.chef.foody.data.remote.QuotationInfo
import com.chef.foody.data.remote.RentChefUpdateOrderStatusParam
import com.chef.foody.data.remote.UpdateUserAddressParam
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.domain.model.User
import com.chef.foody.domain.repository.UserRepository
import com.chef.foody.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepositoryImpl: UserRepository {

    private var shouldReturnNetworkError = true
    private var user: User? = null
    private var activeOrder: OrderStatus? = null
    private var loginStatus: LoginStatus = LoginStatus.Logout
    private var chefStatus: ChefStatus? = null


    fun setShouldReturnNetworkError(value: Boolean)
    {
        shouldReturnNetworkError = value
    }

    override suspend fun onUserLogin(email: String, password: String): Resource<User> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(User("Kishore","Kumarasamy","kishore.rathika5304@gmail.com","233 Albert Street, Waterloo, Ontario", authToken = "Hzwodn23nhs77Ajql"))
        }
    }


    override suspend fun onChefRegister(email: String, firstName: String): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", false)
        }
        else{
            Resource.Error("User Registration Successful!", true)
        }
    }

    override suspend fun onUserRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<User> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(User("Kishore","Kumarasamy","kishore.rathika5304@gmail.com","233 Albert Street, Waterloo, Ontario", authToken = "Hzwodn23nhs77Ajql"))
        }
    }

    override suspend fun getUserInfo(): Flow<User> = flow {
        emit(User("Kishore","Kumarasamy","kishore.rathika5304@gmail.com","233 Albert Street, Waterloo, Ontario", authToken = "Hzwodn23nhs77Ajql"))
    }

    override suspend fun storeUserInfo(user: User) {
        this.user = user
    }

    override suspend fun getUserLoginStatus(): Flow<LoginStatus> = flow {
          emit(LoginStatus.Logout)
        }

    override fun chefSetActiveOrder(orderStatus: OrderStatus) {
    }

    override fun chefResetActiveOrder() {
        activeOrder = null
    }

    override fun chefGetActiveOrder(): OrderStatus {
        return OrderStatus(
            orderId = 123,
            customerFirstName = "John",
            customerLastName = "Doe",
            customerAddress = "123 Main St, City, Country",
            deliveryOrderStatus = DeliveryOrderStatus.PREPARING_FOOD,
            deliveryType = DeliveryType.DOOR_DELIVERY,
            preparationTime = 30,
            price = 500,
            cartInfo = listOf(
                CartInfo("Pizza", 3),
                CartInfo("Burger", 2)
            )
        )
    }

    override suspend fun storeUserLoginStatus(loginStatus: LoginStatus) {
        this.loginStatus = loginStatus
    }

    override suspend fun onChefFindCustomer(chefFindCustomerParam: ChefFindCustomerParam): Resource<List<CustomerInfo>> {
        val customerList = listOf(
            CustomerInfo(
                orderId = 1,
                userId = 101,
                customerFirstName = "Alice",
                customerLastName = "Smith",
                cartInfo = emptyList(),
                isAlreadyQuoted = false,
                price = 0.0f,
                preparationTime = 0,
                quotationId = 0,
                quotationStatusIsAccepted = ""
            ),
            CustomerInfo(
                orderId = 2,
                userId = 102,
                customerFirstName = "Bob",
                customerLastName = "Johnson",
                cartInfo = emptyList(),
                isAlreadyQuoted = false,
                price = 0.0f,
                preparationTime = 0,
                quotationId = 0,
                quotationStatusIsAccepted = ""
            )
        )
        return Resource.Success(customerList)
    }

    override suspend fun onChefSendQuotation(quotation: ChefSendQuotationParam): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
         Resource.Success(true)
        }
    }

    override suspend fun onChefFindCustomerBeta(chefFindCustomerBetaParam: ChefFindCustomerBetaParam): Resource<List<CustomerInfo>> {
        return Resource.Error("ERROR", null)
    }

    override suspend fun onChefSendQuotationBeta(quotation: ChefSendQuotationBetaParam): Resource<Boolean> {
       return Resource.Error("ERROR", null)
    }

    override suspend fun onCheckChefStatus(checkChefStatusParam: CheckChefStatusParam): Resource<ChefStatus> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{ Resource.Success(ChefStatus.VERIFIED)
        }
    }

    override suspend fun onCheckQuotationStatus(checkQuotationStatusParam: CheckQuotationStatusParam): Resource<OrderStatus> {
        val orderStatus = OrderStatus(123,"John","Doe","123 Main St, City, Country",DeliveryOrderStatus.PREPARING_FOOD,DeliveryType.DOOR_DELIVERY,30,500,listOf(
            CartInfo("Pizza", 3),
            CartInfo("Burger", 2)
        ))
        return Resource.Success(orderStatus)
    }

    override suspend fun onChefUpdateOrderStatus(chefUpdateOrderStatusParam: ChefUpdateOrderStatusParam): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(true)
        }
    }

    override suspend fun onRentChefUpdateOrderStatus(rentChefUpdateOrderStatusParam: RentChefUpdateOrderStatusParam): Resource<Boolean> {
        return Resource.Error("ERROR",null)
    }

    override suspend fun onRentChefStatusCheck(getClientRentalOrderStatusByChefParam: GetClientRentalOrderStatusByChefParam): Resource<ChefOrderStatus> {
        return Resource.Error("ERROR",null)
    }

    override suspend fun onOrderFood(orderFoodParam: OrderFoodParam): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(true)
        }
    }

    override suspend fun onLookingForQuotation(lookingForQuotationParam: LookingForQuotationParam): Resource<Pair<List<CartInfo>, List<QuotationInfo>>> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            val cartInfoList = listOf<CartInfo>(

            CartInfo("Pizza", 2),
            CartInfo("Burger", 1))
            val quotationInfoList = listOf<QuotationInfo>(
                QuotationInfo(
                    quotation_id = 1,
                    chef_first_name = "John",
                    chef_last_name = "Doe",
                    price = 25,
                    preparation_time = "30 minutes",
                    delivery_mode = DeliveryType.DOOR_DELIVERY
                ),
                QuotationInfo(
                    quotation_id = 2,
                    chef_first_name = "Jane",
                    chef_last_name = "Smith",
                    price = 30,
                    preparation_time = "45 minutes",
                    delivery_mode = DeliveryType.TAKE_AWAY
                )
            )
            return Resource.Success(cartInfoList to quotationInfoList)
        }
    }

    override suspend fun onLookingForChefRentQuotation(
        email: String,
        authToken: String
    ): Resource<List<ChefQuotationDetails>> {
        val quotationDetailsList = listOf<ChefQuotationDetails>(
            ChefQuotationDetails(
                quotation_id = 1,
                chef_first_name = "John",
                chef_last_name = "Doe",
                price = 25,
                menu = "Spaghetti Carbonara",
                preparation_time = 30
            ),
            ChefQuotationDetails(
                quotation_id = 2,
                chef_first_name = "Jane",
                chef_last_name = "Smith",
                price = 30,
                menu = "Chicken Alfredo",
                preparation_time = 45
            )
        )
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(quotationDetailsList)
        }
    }

    override suspend fun onUpdateUserAddress(updateUserAddressParam: UpdateUserAddressParam): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(true)
        }
    }

    override suspend fun onAcceptOfferQuotation(acceptOfferQuotationParam: AcceptOfferQuotationParam): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(true)
        }
    }

    override suspend fun onCustomerCheckOrderStatus(customerCheckOrderStatusParam: CustomerCheckOrderStatusParam): Resource<CustomerCheckOrderStatus> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(CustomerCheckOrderStatus("","","",DeliveryOrderStatus.ORDER_CONFIRMED,DeliveryType.DOOR_DELIVERY))
        }
    }

    override suspend fun onRentAChef(
        email: String,
        note: String,
        authToken: String
    ): Resource<Boolean> {
        return if(shouldReturnNetworkError)
        {
            Resource.Error("ERROR", null)
        }
        else{
            Resource.Success(true)
        }
    }

    override suspend fun onAcceptToRentalQuotation(acceptToRentalQuotationParam: AcceptToRentalQuotationParam): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun onCurrentRentalOrderStatus(currentRentalOrderStatusParam: CurrentRentalOrderStatusParam): Resource<CurrentRentalOrderStatus> {
        TODO("Not yet implemented")
    }

    override suspend fun storeChefStatus(chefStatus: ChefStatus) {
        this.chefStatus = chefStatus
    }

    override suspend fun getChefStatus(): Flow<ChefStatus> {
        return flow {
            emit(ChefStatus.VERIFIED)
        }
    }

    override suspend fun getUserAddress(email: String, authToken: String): Resource<String> {
        TODO("Not yet implemented")
    }


}