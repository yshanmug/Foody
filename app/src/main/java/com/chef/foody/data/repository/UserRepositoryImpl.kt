package com.chef.foody.data.repository

//import com.chef.foody.util.AppConstants.CHEF_ACTIVE_ORDER
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.chef.foody.data.MemoryDatasource
import com.chef.foody.data.mappers.toUser
import com.chef.foody.data.remote.AcceptOfferQuotationParam
import com.chef.foody.data.remote.AcceptOfferQuotationRequest
import com.chef.foody.data.remote.AcceptToRentalQuotationParam
import com.chef.foody.data.remote.AcceptToRentalQuotationRequest
import com.chef.foody.data.remote.CartInfo
import com.chef.foody.data.remote.CheckChefStatusParam
import com.chef.foody.data.remote.CheckChefStatusRequest
import com.chef.foody.data.remote.CheckQuotationStatusParam
import com.chef.foody.data.remote.CheckQuotationStatusRequest
import com.chef.foody.data.remote.ChefFindCustomerBetaParam
import com.chef.foody.data.remote.ChefFindCustomerBetaRequest
import com.chef.foody.data.remote.ChefFindCustomerParam
import com.chef.foody.data.remote.ChefFindCustomerRequest
import com.chef.foody.data.remote.ChefOrderStatus
import com.chef.foody.data.remote.ChefQuotationDetails
import com.chef.foody.data.remote.ChefSendQuotationBetaParam
import com.chef.foody.data.remote.ChefSendQuotationBetaRequest
import com.chef.foody.data.remote.ChefSendQuotationParam
import com.chef.foody.data.remote.ChefSendQuotationRequest
import com.chef.foody.data.remote.ChefUpdateOrderStatusParam
import com.chef.foody.data.remote.ChefUpdateOrderStatusRequest
import com.chef.foody.data.remote.CurrentRentalOrderStatus
import com.chef.foody.data.remote.CurrentRentalOrderStatusParam
import com.chef.foody.data.remote.CurrentRentalOrderStatusRequest
import com.chef.foody.data.remote.CustomerCheckOrderStatus
import com.chef.foody.data.remote.CustomerCheckOrderStatusParam
import com.chef.foody.data.remote.CustomerCheckOrderStatusRequest
import com.chef.foody.data.remote.CustomerInfo
import com.chef.foody.data.remote.GetClientRentalOrderStatusByChefParam
import com.chef.foody.data.remote.GetClientRentalOrderStatusByChefRequest
import com.chef.foody.data.remote.GetUserAddressRequest
import com.chef.foody.data.remote.GetUserAddressRequestParam
import com.chef.foody.data.remote.LookingForChefRentQuotationParam
import com.chef.foody.data.remote.LookingForChefRentQuotationRequest
import com.chef.foody.data.remote.LookingForQuotationParam
import com.chef.foody.data.remote.LookingForQuotationRequest
import com.chef.foody.data.remote.OrderFoodParam
import com.chef.foody.data.remote.OrderFoodRequest
import com.chef.foody.data.remote.OrderStatus
import com.chef.foody.data.remote.QuotationInfo
import com.chef.foody.data.remote.RegisterChefParam
import com.chef.foody.data.remote.RegisterChefRequest
import com.chef.foody.data.remote.RentChefParam
import com.chef.foody.data.remote.RentChefRequest
import com.chef.foody.data.remote.RentChefUpdateOrderStatusParam
import com.chef.foody.data.remote.RentChefUpdateOrderStatusRequest
import com.chef.foody.data.remote.UpdateUserAddressParam
import com.chef.foody.data.remote.UpdateUserAddressRequest
import com.chef.foody.data.remote.UserApi
import com.chef.foody.data.remote.UserLoginParam
import com.chef.foody.data.remote.UserLoginRequest
import com.chef.foody.data.remote.UserRegistrationParam
import com.chef.foody.data.remote.UserRegistrationRequest
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.domain.model.User
import com.chef.foody.domain.repository.UserRepository
import com.chef.foody.domain.util.Resource
import com.chef.foody.util.AppConstants.USER_ADDRESS_KEY
import com.chef.foody.util.AppConstants.USER_AUTH_TOKEN_KEY
import com.chef.foody.util.AppConstants.USER_CHEF_STATUS
import com.chef.foody.util.AppConstants.USER_EMAIL_KEY
import com.chef.foody.util.AppConstants.USER_FIRST_NAME_KEY
import com.chef.foody.util.AppConstants.USER_LAST_NAME_KEY
import com.chef.foody.util.AppConstants.USER_LOGIN_STATUS_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(private val api: UserApi, private val dataStore: DataStore<Preferences>, private val memoryDatasource: MemoryDatasource): UserRepository {
    override suspend fun onUserLogin(email: String, password: String): Resource<User> {

        return try {

            val response = api.onUserLogin(
                UserLoginRequest(
                    param = UserLoginParam(
                        email = email,
                        password = password
                    )
                )
            )
            if (response.response.status) {
                Resource.Success(
                    data = response.toUser()
                )
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onUserRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val response = api.onUserRegistration(
                UserRegistrationRequest(
                    param = UserRegistrationParam(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password
                    )
                )
            )
            if (response.response.status) {

                Resource.Success(
                    data = response.toUser()
                )
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }


    override suspend fun onChefRegister(
        email: String,
        authToken: String
    ): Resource<Boolean> {
        return try {
            val response = api.registerChef(
                RegisterChefRequest(
                    param = RegisterChefParam(
                        email = email
                    )
                )
                ,
                "Bearer $authToken"
            )

            if (response.response.status) {
                Resource.Success(
                    data = true
                )
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }

    }

    override suspend fun onChefFindCustomer(chefFindCustomerParam: ChefFindCustomerParam): Resource<List<CustomerInfo>> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onChefFindCustomer(
                ChefFindCustomerRequest(
                    param = chefFindCustomerParam
                ),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.customerInfo)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefFindCustomer")
        }
    }


    override suspend fun onChefFindCustomerBeta(chefFindCustomerBetaParam: ChefFindCustomerBetaParam): Resource<List<CustomerInfo>> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onChefFindCustomerBeta(
                ChefFindCustomerBetaRequest(
                    param = chefFindCustomerBetaParam
                ),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.customerInfo)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefFindCustomer")
        }
    }


    override suspend fun onChefSendQuotation(quotation: ChefSendQuotationParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onChefSendQuotation(
                ChefSendQuotationRequest(param = quotation),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefSendQuotation.")
        }
    }



    override suspend fun onChefSendQuotationBeta(quotation: ChefSendQuotationBetaParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onChefSendQuotationBeta(
                ChefSendQuotationBetaRequest(param = quotation),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefSendQuotation.")
        }
    }


    override suspend fun onCheckChefStatus(checkChefStatusParam: CheckChefStatusParam): Resource<ChefStatus> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onCheckChefStatus(
                CheckChefStatusRequest(param = checkChefStatusParam),
                "Bearer $authToken"
            )
            if (response.response.status) Resource.Success(ChefStatus.VERIFIED)
            else Resource.Success(ChefStatus.NOT_VERIFIED) //
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onCheckChefStatus.")
        }
    }

    override suspend fun onCheckQuotationStatus(checkQuotationStatusParam: CheckQuotationStatusParam): Resource<OrderStatus> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onCheckQuotationStatus(
                CheckQuotationStatusRequest(param = checkQuotationStatusParam),
                "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.orderStatus)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onCheckQuotationStatus.")
        }
    }

    override suspend fun onChefUpdateOrderStatus(chefUpdateOrderStatusParam: ChefUpdateOrderStatusParam): Resource<Boolean> {
        var authToken = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onChefUpdateOrderStatus(
                ChefUpdateOrderStatusRequest(param = chefUpdateOrderStatusParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else Resource.Error(response.response.message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefUpdateOrderStatus")
        }
    }

    override suspend fun onRentChefUpdateOrderStatus(rentChefUpdateOrderStatusParam: RentChefUpdateOrderStatusParam): Resource<Boolean> {
        var authToken = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onRentChefUpdateOrderStatus(
                RentChefUpdateOrderStatusRequest(param = rentChefUpdateOrderStatusParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else Resource.Error(response.response.message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onChefUpdateOrderStatus")
        }
    }

    override fun chefSetActiveOrder(orderStatus: OrderStatus) {
        memoryDatasource.setActiveOrder(orderStatus)
    }
    override fun chefResetActiveOrder() {
        memoryDatasource.resetActiveOrder()
    }

    override fun chefGetActiveOrder(): OrderStatus? {
        return memoryDatasource.getActiveOrder()
    }
///copy
//suspend fun onRentChefUpdateOrderStatus(getClientRentalOrderStatusByChefParam: GetClientRentalOrderStatusByChefParam): Resource<ChefOrderStatus>
//    suspend fun onChefUpdateOrderStatus(chefUpdateOrderStatusParam: ChefUpdateOrderStatusParam): Resource<Boolean>

    override suspend fun onRentChefStatusCheck(getClientRentalOrderStatusByChefParam: GetClientRentalOrderStatusByChefParam): Resource<ChefOrderStatus> {
        var authToken = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onRentChefCheckOrderStatus(
                GetClientRentalOrderStatusByChefRequest(param = getClientRentalOrderStatusByChefParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.orderStatus)
            }
            else {
                Resource.Error(response.response.message)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "onRentalChefUpdateOrderStatus")
        }
    }



//    override suspend fun onCurrentRentalOrderStatus(currentRentalOrderStatusParam: CurrentRentalOrderStatusParam): Resource<CurrentRentalOrderStatus> {
//        var authToken: String = ""
//        getUserInfo().collectLatest { authToken = it.authToken }
//        return try {
//            val response = api.onCurrentRentalOrderStatus(
//                CurrentRentalOrderStatusRequest(
//                    param = currentRentalOrderStatusParam),
//                authToken = "Bearer $authToken"
//            )
//            if (response.response.status) {
//                Resource.Success(response.response.orderStatus)
//            } else {
//                Resource.Error(response.response.message)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Resource.Error(e.message ?: "An unknown error occurred.")
//        }
//    }






//    fun rentChefSetActiveOrder(orderStatus: ChefOrderStatus)
//    fun rentChefResetActiveOrder()
//    fun rentChefGetActiveOrder(): ChefOrderStatus?

//    override fun rentChefSetActiveOrder(orderStatus: ChefOrderStatus) {
//        memoryDatasource.setActiveOrder(orderStatus)
//    }
//    override fun rentChefResetActiveOrder() {
//        memoryDatasource.resetActiveOrder()
//    }
//
//    override fun rentChefGetActiveOrder(): ChefOrderStatus? {
//        return memoryDatasource.getActiveOrder()
//    }





    override suspend fun getUserInfo(): Flow<User> = flow {
        val preferences = dataStore.data.first()
        val firstName = preferences[USER_FIRST_NAME_KEY] ?: ""
        val lastName = preferences[USER_LAST_NAME_KEY] ?: ""
        val email = preferences[USER_EMAIL_KEY] ?: ""
        val authToken = preferences[USER_AUTH_TOKEN_KEY] ?: ""
        val address = preferences[USER_ADDRESS_KEY] ?: ""
        emit(User(firstName, lastName, email, address, authToken))
    }

    override suspend fun storeUserInfo(user: User) {
        dataStore.edit { preferences ->
            preferences[USER_FIRST_NAME_KEY] = user.firstName
            preferences[USER_LAST_NAME_KEY] = user.lastName
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_AUTH_TOKEN_KEY] = user.authToken
            preferences[USER_ADDRESS_KEY] = user.address
        }
    }

    override suspend fun getUserLoginStatus(): Flow<LoginStatus> = flow {
        val preferences = dataStore.data.first()
        when (preferences[USER_LOGIN_STATUS_KEY] ?: LoginStatus.Logout.name) {
            LoginStatus.LoginSuccessful.name -> {
                Log.d("LOGIN_STATUS_", "FROM DATA STORE LoginStatus.LoginSuccessful")
                emit(LoginStatus.LoginSuccessful)
            }

            LoginStatus.LoginFailed.name -> {
                Log.d("LOGIN_STATUS_", "FROM DATA STORE LoginStatus.LoginFailed")
                emit(LoginStatus.LoginFailed)
            }

            LoginStatus.Logout.name -> {
                Log.d("LOGIN_STATUS_", "FROM DATA STORE LoginStatus.Logout")
                emit(LoginStatus.Logout)
            }
        }
    }

    override suspend fun storeUserLoginStatus(loginStatus: LoginStatus) {
        dataStore.edit { preferences ->
            preferences[USER_LOGIN_STATUS_KEY] = loginStatus.name
        }
    }

    override suspend fun onOrderFood(orderFoodParam: OrderFoodParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response =
                api.onOrderFood(OrderFoodRequest(param = orderFoodParam), "Bearer $authToken")
            if (response.response.status) Resource.Success(response.response.status)
            else Resource.Error(response.response.message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onLookingForQuotation(lookingForQuotationParam: LookingForQuotationParam): Resource<Pair<List<CartInfo>, List<QuotationInfo>>> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onLookingForQuotation(
                LookingForQuotationRequest(
                    param = lookingForQuotationParam
                ), authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                val cartInfo = response.response.quotation.cart_info
                val quotationInfo = response.response.quotation.quotation_info
                val data = Pair(cartInfo, quotationInfo)
                Resource.Success(data)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onUpdateUserAddress(updateUserAddressParam: UpdateUserAddressParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onUpdateUserAddress(
                UpdateUserAddressRequest(param = updateUserAddressParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onAcceptOfferQuotation(acceptOfferQuotationParam: AcceptOfferQuotationParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onAcceptOfferQuotation(
                AcceptOfferQuotationRequest(param = acceptOfferQuotationParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }


    override suspend fun onRentAChef(
        email: String,
        note: String,
        authToken: String
    ): Resource<Boolean> {
        return try {
            val response = api.onRentAChef(
                RentChefRequest(param = RentChefParam(email, note)),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }

    }

    override suspend fun storeChefStatus(chefStatus: ChefStatus) {
        dataStore.edit { preferences ->
            preferences[USER_CHEF_STATUS] = chefStatus.name
        }
    }

    override suspend fun getChefStatus(): Flow<ChefStatus> = flow {
        val preferences = dataStore.data.first()
        when (preferences[USER_CHEF_STATUS] ?: ChefStatus.NOT_VERIFIED) {
            ChefStatus.VERIFIED.name -> {
                emit(ChefStatus.VERIFIED)
            }
            ChefStatus.NOT_VERIFIED.name -> {
                emit(ChefStatus.NOT_VERIFIED)
            }
            ChefStatus.VERIFICATION_IN_PROGRESS.name -> {
                emit(ChefStatus.VERIFICATION_IN_PROGRESS)
            }
        }
    }

    override suspend fun getUserAddress(email: String, authToken: String): Resource<String> {
        return try {
            val response = api.getUserAddress(
                GetUserAddressRequest(param = GetUserAddressRequestParam(email)),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                val data = response.response.address
                Resource.Success(data)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }


    override suspend fun onLookingForChefRentQuotation(
        email: String,
        authToken: String
    ): Resource<List<ChefQuotationDetails>> {
        return try {
            val response = api.onLookingForChefRentQuotation(
                LookingForChefRentQuotationRequest(param = LookingForChefRentQuotationParam((email))),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                val data = response.response.quotation
                Resource.Success(data)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onCustomerCheckOrderStatus(customerCheckOrderStatusParam: CustomerCheckOrderStatusParam): Resource<CustomerCheckOrderStatus> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onCustomerCheckOrderStatus(
                CustomerCheckOrderStatusRequest(
                    param = customerCheckOrderStatusParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.orderStatus)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onAcceptToRentalQuotation(acceptToRentalQuotationParam: AcceptToRentalQuotationParam): Resource<Boolean> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onAcceptToRentalQuotation(
                AcceptToRentalQuotationRequest(param = acceptToRentalQuotationParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(true)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun onCurrentRentalOrderStatus(currentRentalOrderStatusParam: CurrentRentalOrderStatusParam): Resource<CurrentRentalOrderStatus> {
        var authToken: String = ""
        getUserInfo().collectLatest { authToken = it.authToken }
        return try {
            val response = api.onCurrentRentalOrderStatus(
                CurrentRentalOrderStatusRequest(
                    param = currentRentalOrderStatusParam),
                authToken = "Bearer $authToken"
            )
            if (response.response.status) {
                Resource.Success(response.response.orderStatus)
            } else {
                Resource.Error(response.response.message)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }



}