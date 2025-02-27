package com.chef.foody.domain.repository

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
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.domain.model.User
import com.chef.foody.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun onUserLogin(email: String, password: String): Resource<User>
    suspend fun onChefRegister(email: String, authToken: String): Resource<Boolean>

    suspend fun onUserRegistration(firstName: String, lastName: String,email: String, password: String): Resource<User>
    suspend fun getUserInfo(): Flow<User>
    suspend fun storeUserInfo(user: User)
    suspend fun getUserLoginStatus(): Flow<LoginStatus>

    fun chefSetActiveOrder(orderStatus: OrderStatus)
    fun chefResetActiveOrder()
    fun chefGetActiveOrder(): OrderStatus?

    //copy
//    fun rentChefSetActiveOrder(orderStatus: ChefOrderStatus)
//    fun rentChefResetActiveOrder()
//    fun rentChefGetActiveOrder(): ChefOrderStatus?


    suspend fun storeUserLoginStatus(loginStatus: LoginStatus)


    suspend fun onChefFindCustomer(chefFindCustomerParam: ChefFindCustomerParam): Resource<List<CustomerInfo>>

    suspend fun onChefSendQuotation(quotation: ChefSendQuotationParam): Resource<Boolean>

    suspend fun onChefFindCustomerBeta(chefFindCustomerBetaParam: ChefFindCustomerBetaParam): Resource<List<CustomerInfo>>

    suspend fun onChefSendQuotationBeta(quotation: ChefSendQuotationBetaParam): Resource<Boolean>

    suspend fun onCheckChefStatus(checkChefStatusParam: CheckChefStatusParam): Resource<ChefStatus>

    suspend fun onCheckQuotationStatus(checkQuotationStatusParam: CheckQuotationStatusParam): Resource<OrderStatus>

    suspend fun onChefUpdateOrderStatus(chefUpdateOrderStatusParam: ChefUpdateOrderStatusParam): Resource<Boolean>
//copy
   suspend fun onRentChefUpdateOrderStatus(rentChefUpdateOrderStatusParam: RentChefUpdateOrderStatusParam): Resource<Boolean>
    suspend fun onRentChefStatusCheck(getClientRentalOrderStatusByChefParam: GetClientRentalOrderStatusByChefParam): Resource<ChefOrderStatus>
    suspend fun onOrderFood(orderFoodParam: OrderFoodParam): Resource<Boolean>

    suspend fun onLookingForQuotation(lookingForQuotationParam: LookingForQuotationParam): Resource<Pair<List<CartInfo>, List<QuotationInfo>>>

    suspend fun onLookingForChefRentQuotation(email: String , authToken: String): Resource<List<ChefQuotationDetails>>

    suspend fun onUpdateUserAddress(updateUserAddressParam: UpdateUserAddressParam): Resource<Boolean>
    suspend fun onAcceptOfferQuotation(acceptOfferQuotationParam: AcceptOfferQuotationParam): Resource<Boolean>
    suspend fun onCustomerCheckOrderStatus(customerCheckOrderStatusParam: CustomerCheckOrderStatusParam): Resource<CustomerCheckOrderStatus>
    suspend fun onRentAChef(email: String, note: String, authToken: String): Resource<Boolean>

    suspend fun onAcceptToRentalQuotation(acceptToRentalQuotationParam: AcceptToRentalQuotationParam): Resource<Boolean>

    suspend fun onCurrentRentalOrderStatus(currentRentalOrderStatusParam: CurrentRentalOrderStatusParam): Resource<CurrentRentalOrderStatus>
    suspend fun storeChefStatus(chefStatus: ChefStatus)

    suspend fun getChefStatus(): Flow<ChefStatus>

    suspend fun getUserAddress(email: String, authToken: String): Resource<String>


}