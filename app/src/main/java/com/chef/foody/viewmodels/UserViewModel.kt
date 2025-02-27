package com.chef.foody.viewmodels

import UserType
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chef.foody.R
import com.chef.foody.data.remote.AcceptOfferQuotationParam
import com.chef.foody.data.remote.AcceptToRentalQuotationParam
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

import com.chef.foody.data.remote.LookingForChefRentQuotationParam
import com.chef.foody.data.remote.LookingForChefRentQuotationRequest
import com.chef.foody.data.remote.CustomerCheckOrderStatus
import com.chef.foody.data.remote.CurrentRentalOrderStatusParam
import com.chef.foody.data.remote.CustomerCheckOrderStatusParam
import com.chef.foody.data.remote.GetClientRentalOrderStatusByChefParam
import com.chef.foody.data.remote.LookingForQuotationParam
import com.chef.foody.data.remote.OrderFoodParam
import com.chef.foody.data.remote.OrderStatus
import com.chef.foody.data.remote.RentChefParam
import com.chef.foody.data.remote.RentChefUpdateOrderStatusParam
import com.chef.foody.data.repository.UserRepositoryImpl
import com.chef.foody.data.remote.UpdateUserAddressParam
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.domain.model.DeliveryOrderStatus
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.domain.model.LoginStatus
import com.chef.foody.domain.model.RentalChefOrderStatus
import com.chef.foody.domain.model.RentalOrderStatus
import com.chef.foody.domain.repository.UserRepository
import com.chef.foody.domain.util.Resource
import com.chef.foody.events.AcceptOfferQuotationEvent
import com.chef.foody.events.AcceptToRentalQuotationEvent
import com.chef.foody.events.AddressUpdateEvent
import com.chef.foody.events.CartEvent
import com.chef.foody.events.CartItem
import com.chef.foody.events.ChefQuotationEvent
import com.chef.foody.events.FoodOrderObj
import com.chef.foody.events.OrderFoodEvent
import com.chef.foody.events.UserLoginEvent
import com.chef.foody.events.UserSignUpEvent
import com.chef.foody.presentation.cart.CartState
import com.chef.foody.presentation.chef.QuotationDetail
import com.chef.foody.presentation.chef.QuotationList
import com.chef.foody.presentation.chef.QuotationInfoList
import com.chef.foody.presentation.customer.CurrentRentalOrderStatusList
import com.chef.foody.presentation.customer.CustomerInfoList
import com.chef.foody.presentation.customer.GetCustomerCheckOderStatus
import com.chef.foody.presentation.food.FoodState
import com.chef.foody.presentation.user.UserState
import com.chef.foody.util.OrderBetaType
import com.chef.foody.util.OrderTypeCheckBeta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
   private val userRepository: UserRepository
): ViewModel() {


    private val _userState = mutableStateOf(UserState()) //VIEW MODEL - EDITABLE
    val userState: State<UserState> = _userState // FOR VIEW - READ ONLY
    val currentUserType = mutableStateOf(UserType.COOK)

    private val _foodState = mutableStateOf(FoodState())
    val foodState: State<FoodState> = _foodState

    val chefNewOrderAccepted = MutableSharedFlow<OrderBetaType>()
    private val _chefActiveOrderStatus = mutableStateOf<OrderStatus?>(null)
    val chefActiveOrderStatus: State<OrderStatus?> = _chefActiveOrderStatus

    private val _rentalChefActiveOrderStatus = mutableStateOf<ChefOrderStatus?> (null)
    val rentalChefActiveOrderStatus: State<ChefOrderStatus?> = _rentalChefActiveOrderStatus

    private val _customerInfoList = mutableStateOf(CustomerInfoList())
    val customerInfoList: State<CustomerInfoList> = _customerInfoList

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _chefQuotationList = mutableStateOf(QuotationList())
    val chefQuotationList: State<QuotationList> = _chefQuotationList

    private val _chefQuotationInfoList = mutableStateOf(QuotationInfoList())
    val chefQuotationInfoList: State<QuotationInfoList> = _chefQuotationInfoList


    private val _chefRentQuotationList = mutableStateOf(QuotationDetail())
    val  chefRentQuotationList: State<QuotationDetail> = _chefRentQuotationList

    private val _isChefRentOrderPosted = MutableSharedFlow<Boolean>()
    val  isChefRentOrderPosted = _isChefRentOrderPosted.asSharedFlow()

    private val _isChefRentalAcceptedByUser = MutableSharedFlow<Int>()
    val isChefRentalAcceptedByUser = _isChefRentalAcceptedByUser.asSharedFlow()

    private val _isOrderAcceptedByUser = MutableSharedFlow<DeliveryType>()
    val  isOrderAcceptedByUser = _isOrderAcceptedByUser.asSharedFlow()


    private val _customerCheckOrderStatusList = mutableStateOf(GetCustomerCheckOderStatus(CustomerCheckOrderStatus("","","",DeliveryOrderStatus.ORDER_CONFIRMED,
        DeliveryType.DOOR_DELIVERY)))
    val customerCheckOrderStatusList: State<GetCustomerCheckOderStatus> = _customerCheckOrderStatusList



    private val _currentRentalOrderStatusList = mutableStateOf(CurrentRentalOrderStatusList(CurrentRentalOrderStatus("","","",RentalOrderStatus.ORDER_CONFIRMED)))
    val currentRentalOrderStatusList: State<CurrentRentalOrderStatusList> = _currentRentalOrderStatusList






    val authToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDgyMzM4OTUsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcwODIzNDc5NSwidXNlcklkIjoxOH0.uwwdp_741aVITDb-_v1gcAM66rA3uxsClWQJzjHj8JE"


    init {
        initFoodItems()
        restoreUserData()
        restoreMemoryData()
    }

    private fun initFoodItems() {
        val foods = listOf(
            FoodOrderObj(
                0,
                "Hamburger",
                "Indulge in our mouthwatering hamburger, featuring a succulent beef patty grilled to perfection.",
                R.drawable.pngegg12,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                1,
                "Ramen Noodles",
                "Savor the authentic flavors of Asia with our delectable Ramen Chinese Noodles.",
                R.drawable.pngegg1,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                2,
                "Pepperoni Pizza",
                "Treat yourself to the comforting aroma and taste of our Homemade pizza.",
                R.drawable.pngegg2,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                3,
                "Chicken Biryani",
                "Experience the art of authentic Indian cooking with our Homemade Chicken.",
                R.drawable.pngegg3,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                4,
                "Fried Chicken",
                "Indulge in the crispy and succulent delight of our Homemade Fried.",
                R.drawable.pngegg4,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                5,
                "Pork Chop",
                "Savor the juicy and succulent goodness of our Pork Chops, a classic favorite.",
                R.drawable.pork_item,
                "NON_VEG",
                0
            ),
            FoodOrderObj(
                6,
                "Veggie Salad",
                "Indulge in the crisp and refreshing flavors of our Veggie Salad, a vibrant.",
                R.drawable.pngegg6,
                "VEG",
                0
            ),
            FoodOrderObj(
                7,
                "Veggie Pizza",
                "Delight in the vibrant flavors and colors of our Veggie Pizza, a mouthwatering.",
                R.drawable.pngegg7,
                "VEG",
                0
            ),
            FoodOrderObj(
                8,
                "Veggie Burger",
                "Elevate your burger experience with our Homemade Veggie Burger, a delicious.",
                R.drawable.pngegg8,
                "VEG",
                0
            ),
            FoodOrderObj(
                9,
                "Veggie Pasta",
                "Treat yourself to a hearty and wholesome meal with our Veggie Pasta, a delightful.",
                R.drawable.pngegg9,
                "VEG",
                0
            ),
            FoodOrderObj(
                10,
                "French Fries",
                "Indulge in the crispy and golden goodness of our French Fries, a beloved classic that's.",
                R.drawable.pngegg10,
                "VEG",
                0
            ),
            FoodOrderObj(
                11,
                "Strawberry Milkshake",
                "Indulge in the sweet and creamy goodness of our Strawberry Milkshake.",
                R.drawable.pngegg11,
                "VEG",
                0
            )
        )
        _foodState.value = foodState.value.copy(foodList = foods)
    }

    private fun restoreUserData() {
        viewModelScope.launch {
            userRepository.getUserLoginStatus().collectLatest {
                _userState.value = userState.value.copy(
                    isUserLoggedIn = it
                )
            }
            userRepository.getUserInfo().collectLatest {
                _userState.value = userState.value.copy(
                    userInfo = it
                )
            }
        }
    }

     fun onUserLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onUserLogin(email, password)) {
                is Resource.Success -> {
                    Log.d("USER_INFO","AFTER LOGIN ${result.data?.firstName}")
                    userRepository.storeUserInfo(result.data ?: userState.value.userInfo)
                    userRepository.storeUserLoginStatus(LoginStatus.LoginSuccessful)
                    _userState.value = userState.value.copy(
                        isUserLoggedIn = LoginStatus.LoginSuccessful
                    )
                    _userState.value = userState.value.copy(
                        userInfo = result.data ?: userState.value.userInfo
                    )
                    delay(3000)
                    onCheckChefStatus()
                }

                is Resource.Error -> {
                    _userState.value = userState.value.copy(
                        isUserLoggedIn = LoginStatus.LoginFailed,
                        userInfo = userState.value.userInfo
                    )
                    userRepository.storeUserLoginStatus(LoginStatus.LoginFailed)
                }
            }
        }
    }

    private fun onUserSignup(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onUserRegistration(firstName, lastName, email, password)) {
                is Resource.Success -> {
                    Log.d("REGISTER_LOG", "SUCCESS " + result.message)
                    userRepository.storeUserInfo(result.data ?: userState.value.userInfo)
                    userRepository.storeUserLoginStatus(LoginStatus.LoginSuccessful)

                    _userState.value = userState.value.copy(
                        isUserLoggedIn = LoginStatus.LoginSuccessful
                    )
                    _userState.value = userState.value.copy(
                        userInfo = result.data ?: userState.value.userInfo
                    )
                }

                is Resource.Error -> {
                    Log.d("REGISTER_LOG", "ERROR " + result.message)
                    _userState.value = userState.value.copy(
                        isUserLoggedIn = LoginStatus.LoginFailed,
                        userInfo = userState.value.userInfo
                    )
                    userRepository.storeUserLoginStatus(LoginStatus.LoginFailed)
                }
            }
        }
    }

    fun getUserAddress()
    {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.getUserAddress(userState.value.userInfo.email,userState.value.userInfo.authToken)) {
                is Resource.Success -> {
                    userRepository.storeUserInfo(userState.value.userInfo.copy(address = result.data.toString()))
                    _userState.value = userState.value.copy(
                        userInfo = userState.value.userInfo.copy(address = result.data.toString())
                    )
                }

                is Resource.Error -> {
                    Log.d("GET_ADDRESS", "ERROR " + result.message)
                }
            }
        }
    }

    private fun resetUserInfo() {
        viewModelScope.launch {
            _userState.value = UserState()
            userRepository.storeUserInfo(userState.value.userInfo)
            userRepository.storeUserLoginStatus(LoginStatus.Logout)
        }
    }



    fun onLoginEvent(event: UserLoginEvent) {
        when (event) {
            is UserLoginEvent.ResetUserState -> {
                resetUserInfo()
            }

            is UserLoginEvent.Login -> {
                onUserLogin(event.email, event.password)
            }
        }
    }

    fun onSignupEvent(event: UserSignUpEvent) {
        when (event) {
            is UserSignUpEvent.Register -> {
                onUserSignup(event.firstName, event.lastName, event.email, event.confirmPassword)
            }
        }
    }

//
//
//    fun onChefFindCustomer() {
//        viewModelScope.launch(Dispatchers.IO) {
//            when (val result =
//                userRepository.onChefFindCustomer(ChefFindCustomerParam(userState.value.userInfo.email))) {
//                is Resource.Success -> {
//                    when (result.data) {
//                        null -> {
////                            Log.d("UserViewModel", "onChefFindCustomer result.data == null")
//                            _customerInfoList.value = CustomerInfoList()
//                        }
//
//                        else -> {
////                            Log.d("UserViewModel", "onChefFindCustomer else")
//                            _customerInfoList.value = CustomerInfoList(result.data)
//                            when (val acceptedOffer = result.data.find {
//                                it.quotationStatusIsAccepted == "ACCEPTED"
//                            }) {
//                                null -> {
////                                    Log.d("UserViewModel", "onChefFindCustomer no quotation is accepted")
//                                }
//
//                                else -> {
////                                    Log.d("UserViewModel", "onChefFindCustomer ORDER ID ${acceptedOffer.orderId}")
//                                    when (val orderStatus = userRepository.onCheckQuotationStatus(
//                                        CheckQuotationStatusParam(
//                                            userState.value.userInfo.email,
//                                            acceptedOffer.orderId
//                                        )
//                                    )) {
//                                        is Resource.Success -> {
//                                            if (orderStatus.data!=null) {
//                                                userRepository.chefSetActiveOrder(orderStatus.data)
//                                                _chefActiveOrderStatus.value =
//                                                    userRepository.chefGetActiveOrder()
//                                            } else userRepository.chefResetActiveOrder()
//                                        }
//
//                                        is Resource.Error -> {
//                                            Log.d(
//                                                "UserViewModel",
//                                                "CHEF CHECK QUOTATION STATUS ${result.message}"
//                                            );
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                is Resource.Error -> {
//                    Log.d("UserViewModel", "onChefFindCustomers ${result.message}")
//                }
//            }
//        }
//    }



//    fun onChefFindCustomer() {
//        viewModelScope.launch(Dispatchers.IO) {
//            when (val result =
//                userRepository.onChefFindCustomer(ChefFindCustomerParam(userState.value.userInfo.email))) {
//                is Resource.Success -> {
//                    when (result.data) {
//                        null -> {
////                            Log.d("UserViewModel", "onChefFindCustomer result.data == null")
//                            _customerInfoList.value = CustomerInfoList()
//                        }
//
//                        else -> {
////                            Log.d("UserViewModel", "onChefFindCustomer else")
//                            _customerInfoList.value = CustomerInfoList(result.data)
//                            when (val acceptedOffer = result.data.find {
//                                it.quotationStatusIsAccepted == "ACCEPTED"
//                            }) {
//                                null -> {
////                                    Log.d("UserViewModel", "onChefFindCustomer no quotation is accepted")
//                                }
//
//                                else -> {
////                                    Log.d("UserViewModel", "onChefFindCustomer ORDER ID ${acceptedOffer.orderId}")
//                                    when (val orderStatus = userRepository.onCheckQuotationStatus(
//                                        CheckQuotationStatusParam(
//                                            userState.value.userInfo.email,
//                                            acceptedOffer.orderId
//                                        )
//                                    )) {
//                                        is Resource.Success -> {
//                                            if (orderStatus.data!=null) {
//                                                userRepository.chefSetActiveOrder(orderStatus.data)
//                                                _chefActiveOrderStatus.value =
//                                                    userRepository.chefGetActiveOrder()
//                                                chefNewOrderAccepted.emit(OrderBetaType.ChefCook)
//                                            } else userRepository.chefResetActiveOrder()
//                                        }
//
//                                        is Resource.Error -> {
//                                            Log.d(
//                                                "UserViewModel",
//                                                "CHEF CHECK QUOTATION STATUS ${result.message}"
//                                            );
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                is Resource.Error -> {
//                    Log.d("UserViewModel", "onChefFindCustomers ${result.message}")
//                }
//            }
//        }
//    }

    fun onChefFindCustomerBeta() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                userRepository.onChefFindCustomerBeta(ChefFindCustomerBetaParam(userState.value.userInfo.email))) {
                is Resource.Success -> {
                    when (result.data) {
                        null -> {
//                            Log.d("CHEF FIND CUSTOMER", "NULL")
                            _customerInfoList.value = CustomerInfoList()
                        }

                        else -> {
//                            Log.d("CHEF FIND CUSTOMER", "NOT NULL RESULTDATA")
                            _customerInfoList.value = CustomerInfoList(result.data)
                            when (val acceptedOffer = result.data.find {
                                it.quotationStatusIsAccepted == "ACCEPTED"
                            }) {
                                null -> {
//                                    Log.d("CHEF FIND CUSTOMER", "NULL ORDER ID")
                                }

                                else -> {
//                                    Log.d("CHEF FIND CUSTOMER", "NOT NULL ORDER ID ${acceptedOffer.orderId}")
                                    // cook in chef's home, not rental
                                    if (OrderTypeCheckBeta.orderTypeCheckCustomerInfoBeta(acceptedOffer) is OrderBetaType.ChefCook) {
                                        when (val orderStatus = userRepository.onCheckQuotationStatus(
                                            CheckQuotationStatusParam(
                                                userState.value.userInfo.email,
                                                acceptedOffer.orderId
                                            )
                                        )) {
                                            is Resource.Success -> {
                                                val order = orderStatus.data
                                                if (order!=null) {
                                                    userRepository.chefSetActiveOrder(order)
                                                    _chefActiveOrderStatus.value =
                                                        userRepository.chefGetActiveOrder()
                                                    chefNewOrderAccepted.emit(OrderBetaType.ChefCook)
//                                                    if(order.deliveryOrderStatus == DeliveryOrderStatus.ORDER_CONFIRMED)
//                                                    {
//                                                        chefUpdateOrderStatus(DeliveryOrderStatus.PREPARING_FOOD)
//                                                    }
                                                } else userRepository.chefResetActiveOrder()
                                            }
                                            is Resource.Error -> {
                                                Log.d(
                                                    "UserViewModel",
                                                    "CHEF CHECK QUOTATION BETA STATUS ${result.message}"
                                                );
                                            }
                                        }
                                    } else {
                                        Log.d("CHEF_RENTAL_CHECK","FROM VM ${acceptedOffer.orderId}")
                                        _isChefRentalAcceptedByUser.emit(acceptedOffer.orderId)
                                    }
                                }
                            }
                        }
                    }
                }

                is Resource.Error -> {
                    Log.d("UserViewModel", "onChefFindCustomers ${result.message}")
                }
            }
        }
    }


    fun onSendQuotationEvent(event: ChefQuotationEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is ChefQuotationEvent.Quotation -> {
                    when (val result = userRepository.onChefSendQuotation(
                        ChefSendQuotationParam(
                            userState.value.userInfo.email,
                            event.orderId,
                            event.price,
                            event.time
                        )
                    )) {
                        is Resource.Success -> {
                            _customerInfoList.value =
                                CustomerInfoList(
                                    _customerInfoList.value.data.map {
                                        if (it.orderId != event.orderId) it else it.copy(
                                            isAlreadyQuoted = true
                                        )
                                    }
                                )
                        }

                        is Resource.Error -> {
                            Log.d("UserViewModel", "onSendQuotationEvent ERROR ${result.message}");
                        }
                    }
                }
                else -> {}
            }
        }
    }

    fun onSendQuotationEventBeta(event: ChefQuotationEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is ChefQuotationEvent.QuotationBeta -> {
                    _customerInfoList.value = CustomerInfoList(
                        _customerInfoList.value.data.map {
                            if (it.orderId != event.orderId) it else it.copy(
                                price = event.price,
                                preparationTime = event.time,
                                menu = event.menu.ifEmpty { null }
                            )
                        }
                    )
                    when (val result = userRepository.onChefSendQuotationBeta(
                        ChefSendQuotationBetaParam(
                            userState.value.userInfo.email,
                            event.orderId,
                            event.price,
                            event.time,
                            type = event.type?:"your home",
                            menu = event.menu?:"",
                        )
                    )) {
                        is Resource.Success -> {
                            _customerInfoList.value =
                                CustomerInfoList(
                                    _customerInfoList.value.data.map {
                                        if (it.orderId != event.orderId) it else it.copy(
                                            isAlreadyQuoted = true
                                        )
                                    }
                                )
                        }
                        is Resource.Error -> {
                            Log.d("UserViewModel", "onSendQuotationEventBeta ERROR ${result.message}");
                        }
                    }
                }
                else -> {}
            }
        }
    }

    fun onCheckChefStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                userRepository.onCheckChefStatus(CheckChefStatusParam(_userState.value.userInfo.email))
            when (result) {
                is Resource.Success -> {
                        _userState.value = _userState.value.copy(chefStatus = result.data)
                        result.data?.let { userRepository.storeChefStatus(it) }
                    Log.d("UserViewModel", "onCheckChefStatus result:${result.data?.name}")
                }

                else -> {
                    Log.d("UserViewModel", "onCheckChefStatus ERROR ${result.message}")
                }
            }
        }
    }

    fun restoreMemoryData() {
        _chefActiveOrderStatus.value = userRepository.chefGetActiveOrder()
    }


//    private val _rentalChefActiveOrderStatus = mutableStateOf<ChefOrderStatus?> (null)
//    val rentalChefActiveOrderStatus: State<ChefOrderStatus?> = _rentalChefActiveOrderStatus

    fun chefUpdateOrderStatus(deliveryOrderStatus: DeliveryOrderStatus) {
  viewModelScope.launch(Dispatchers.IO) {
            // REQUIRE ACTIVE ORDER TO CONTINUE
            val activeOrder = userRepository.chefGetActiveOrder()
            if (activeOrder != null) {
                val targetActiveOrder = activeOrder.copy(deliveryOrderStatus = deliveryOrderStatus)
                when (val result = userRepository.onChefUpdateOrderStatus(ChefUpdateOrderStatusParam(userState.value.userInfo.email, activeOrder.orderId, deliveryOrderStatus)
                )) {
                    is Resource.Success -> {
//                        _chefActiveOrderStatus.value = activeOrder.copy(order_status = deliveryOrderStatus)
                        userRepository.chefSetActiveOrder(targetActiveOrder)
                        _chefActiveOrderStatus.value = targetActiveOrder
                        if (targetActiveOrder.deliveryOrderStatus == DeliveryOrderStatus.DELIVERED ||
                            targetActiveOrder.deliveryOrderStatus == DeliveryOrderStatus.FOOD_IS_READY_FOR_PICKUP
                        )
                            userRepository.chefResetActiveOrder() // no DELIVERED status, skip
                    }
                    is Resource.Error -> {
                        Log.d("UserViewModel", "chefUpdateOrderStatus Error ${result.message}")
                    }
                }
            }
        }
    }
    fun rentChefUpdateOrderStatus(orderID: Int, rentalChefOrderStatus: RentalChefOrderStatus) {
        viewModelScope.launch(Dispatchers.IO) {
                when (val result = userRepository.onRentChefUpdateOrderStatus(
                    RentChefUpdateOrderStatusParam(userState.value.userInfo.email, orderID, rentalChefOrderStatus)
                )) {
                    is Resource.Success -> {
                        getRentChefOrderStatusFromChef(orderID)
                    }
                    is Resource.Error -> {
                        Log.d("UserViewModel", "chefUpdateOrderStatus Error ${result.message}")
                    }
                }

        }
    }
//    fun restoreMemoryData() {
//        _rentalChefActiveOrderStatus = userRepository.rentChefGetActiveOrder()
//    }
//




    fun onCartEvent(event: CartEvent) {
        viewModelScope.launch {
            when (event) {
                is CartEvent.AddToCart -> {
                    val existingItem = _cartState.value.cartList.find { it.objID == event.item.id }
                    if (existingItem != null) {
                        // If item already exists, update its quantity
                        val updatedList = _cartState.value.cartList.toMutableList().apply {
                            // Find the item and update its quantity
                            find { it.objID == event.item.id }?.let {
                                it.quantity += event.item.quantity
                            }
                        }
                        _cartState.value = _cartState.value.copy(cartList = updatedList)
                        val index = foodState.value.foodList.indexOf(event.item)
                        val foodList = foodState.value.foodList.toMutableList()
                        foodList[index] = foodList[index].copy(quantity = 0)
                        _foodState.value = foodState.value.copy(foodList = foodList)

                    } else {
                        // If item does not exist, add it to the cart
                        val newItem = CartItem(
                            foodItem = event.item,
                            quantity = event.item.quantity,
                            objID = event.item.id,
                            title = event.item.name
                        )
                        val updatedList = _cartState.value.cartList.toMutableList().apply {
                            add(newItem)
                        }
                        _cartState.value = _cartState.value.copy(cartList = updatedList)
                        val index = foodState.value.foodList.indexOf(event.item)
                        val foodList = foodState.value.foodList.toMutableList()
                        foodList[index] = foodList[index].copy(quantity = 0)
                        _foodState.value = foodState.value.copy(foodList = foodList)
                        //val updatedFoodList = foodState.value.foodList.map { it.copy(quantity = 0) }
                        //_foodState.value = foodState.value.copy(foodList = updatedFoodList)
                    }
                }

                is CartEvent.ChangeQuantity -> {
                    if (event.change == "+") {
                        val index = foodState.value.foodList.indexOf(event.item)
                        if (index >= 0) {
                            val foodList = foodState.value.foodList.toMutableList()
                            foodList[index] =
                                foodList[index].copy(quantity = event.item.quantity + 1)
                            _foodState.value = foodState.value.copy(foodList = foodList)
                        }

                    } else if (event.change == "-") {
                        if (event.item.quantity >= 1) {
                            val index = foodState.value.foodList.indexOf(event.item)
                            if (index >= 0) {
                                val foodList = foodState.value.foodList.toMutableList()
                                foodList[index] =
                                    foodList[index].copy(quantity = event.item.quantity - 1)
                                _foodState.value = foodState.value.copy(foodList = foodList)
                            }
                        }
                    }
                }

                is CartEvent.ChangeCartQuantity -> {
                    if (event.change == "+") {
                        val index = cartState.value.cartList.indexOf(event.item)
                        if (index >= 0) {
                            val cartList = cartState.value.cartList.toMutableList()
                            cartList[index] =
                                cartList[index].copy(quantity = event.item.quantity + 1)
                            _cartState.value = cartState.value.copy(cartList = cartList)
                        }

                    } else if (event.change == "-") {
                        if (event.item.quantity > 1) {
                            val index = cartState.value.cartList.indexOf(event.item)
                            if (index >= 0) {
                                val cartList = cartState.value.cartList.toMutableList()
                                cartList[index] =
                                    cartList[index].copy(quantity = event.item.quantity - 1)
                                _cartState.value = cartState.value.copy(cartList = cartList)
                            }
                        } else if (event.item.quantity == 1) {
                            val cartList = cartState.value.cartList.toMutableList()
                            cartList.remove(event.item)
                            _cartState.value = cartState.value.copy(cartList = cartList)
                        }
                    }
                }

                is CartEvent.DeleteCartItem -> {
                    val cartList = cartState.value.cartList.toMutableList()
//                    event.item.quantity = 0
                    cartList.remove(event.item)
                    _cartState.value = cartState.value.copy(cartList = cartList)
                }
            }
        }
    }

    fun onProceedToFindOrder(event: OrderFoodEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is OrderFoodEvent.OrderFood -> {
                    when (
                        val result = userRepository.onOrderFood(
                            OrderFoodParam(
                                userState.value.userInfo.email,
                                event.cartList,
                                event.deliveryMode
                            )
                        )
                    ) {
                        is Resource.Success -> {
                            Log.d("RESULT_DATA", "${result.message}")
                        }

                        is Resource.Error -> {
                            Log.d("RESULT_DATA", "ERROR PLACING ORDER: ${result.message}")
                        }
                    }
                }
            }
        }
    }



    fun resetQuotationList()
    {
        _chefQuotationList.value = QuotationList()
        _chefQuotationInfoList.value = QuotationInfoList()
    }
    fun onLookingForQuotation() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onLookingForQuotation(LookingForQuotationParam(userState.value.userInfo.email))) {
                is Resource.Success -> {
                    if (result.data != null) {
                        val (cartInfo, quotationInfo) = result.data
                        if (quotationInfo.isNotEmpty() && cartInfo.isNotEmpty()) {
                            _chefQuotationList.value = QuotationList(cartInfo)
                            _chefQuotationInfoList.value = QuotationInfoList(quotationInfo)
                        }
                    } else {
                        _chefQuotationList.value = QuotationList()
                        _chefQuotationInfoList.value = QuotationInfoList()
                    }
                }
                is Resource.Error -> {
                    Log.d("UserViewModel", "onLookingForQuotation ${result.message}")
                }
            }
        }
    }

    fun resetLookingForQuotationList()
    {
        _chefQuotationList.value = QuotationList()
        _chefQuotationInfoList.value = QuotationInfoList()
    }
    fun onUpdateUserAddress(event: AddressUpdateEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is AddressUpdateEvent.AddressUpdate -> {
                    when (
                        val result = userRepository.onUpdateUserAddress(
                            UpdateUserAddressParam(
                                userState.value.userInfo.email,
                                event.address,
                            )
                        )
                    ) {
                        is Resource.Success -> {
                            getUserAddress()
                            Log.d("RESULT_DATA", "${result.message}")
                        }
                        is Resource.Error -> {
                            Log.d("RESULT_DATA", "ERROR PLACING ORDER: ${result.message}")
                        }
                    }
                }
            }
        }
    }




    fun onRentAChef(note: String)
    {
      viewModelScope.launch(Dispatchers.IO) {
      when(val result = userRepository.onRentAChef(userState.value.userInfo.email,note, userState.value.userInfo.authToken))
      {
          is Resource.Success -> {
              if(result.data == true)
              {
                  _isChefRentOrderPosted.emit(true)
                  Log.d("RENT_CHEF_SUCCESS","TRUE")
              }
              else
              {
                  _isChefRentOrderPosted.emit(false)
                  Log.d("RENT_CHEF_SUCCESS","FALSE")
              }

          }
          is Resource.Error-> {
              _isChefRentOrderPosted.emit(false)
              Log.d("RENT_CHEF_SUCCESS","ERROR")
          }
      }
      }
    }



//    fun onLookingForChefRentQuotation(email: String,  authToken: String): Resource<List<ChefQuotationDetails>> {
//        viewModelScope.launch(Dispatchers.IO) {
//            when(val result = userRepository.onLookingForChefRentQuotation(userState.value.userInfo.email, userState.value.userInfo.authToken))
//            {
//
//            }
//
//        }
//    }

    fun onLookingForChefRentQuotation() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onLookingForChefRentQuotation(userState.value.userInfo.email, userState.value.userInfo.authToken)) {
                is Resource.Success -> {
                    if (result.data != null) {
                        val quotationInformation = result.data

                        if (quotationInformation.isNotEmpty()) {
                            _chefRentQuotationList.value = QuotationDetail(quotationInformation)
                        }
                    } else {
                        Log.d("_RENT_CHEF","Resource.Success   STATUS FALSE")
                        _chefRentQuotationList.value = QuotationDetail(emptyList())
                    }
                }
                is Resource.Error -> {
                    Log.d("_RENT_CHEF","Resource.Success   Resource.ErrorE")
                    Log.d("UserViewModel", "onLookingForChefRentQuotation ${result.message}")
                }
            }
        }
    }



    fun onAcceptOfferQuotation(event: AcceptOfferQuotationEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event){
                is AcceptOfferQuotationEvent.AcceptOfferQuotation -> {
                    when (
                val result =
                    userRepository.onAcceptOfferQuotation(
                        AcceptOfferQuotationParam(
                            userState.value.userInfo.email,
                            event.quotationId,
                            event.isAccepted,
                        )
                    )
                    ) {
                        is Resource.Success -> {
                            Log.d("RESULT_DATA_QUOTATION", "SUCCESS : ${result.message}")
                            if(event.isAccepted && event.deliveryType == DeliveryType.DOOR_DELIVERY)
                                _isOrderAcceptedByUser.emit(DeliveryType.DOOR_DELIVERY)
                            else if(event.isAccepted && event.deliveryType == DeliveryType.TAKE_AWAY)
                                _isOrderAcceptedByUser.emit(DeliveryType.TAKE_AWAY)
                            Log.d("RESULT_DATA", "${result.message}")
                        }

                        is Resource.Error -> {
                            Log.d("RESULT_DATA_QUOTATION", "ERROR : ${result.message}")
                        }
                    }
                }
            }
        }
    }
    fun onCustomerCheckOrderStatus() {
        viewModelScope.launch (Dispatchers.IO) {
            when (val result = userRepository.onCustomerCheckOrderStatus(CustomerCheckOrderStatusParam(userState.value.userInfo.email))
            ) {
                is Resource.Success -> {
                    if (result.data != null) {
                        val customerCheckOderStatus = result.data
                            _customerCheckOrderStatusList.value = GetCustomerCheckOderStatus(customerCheckOderStatus)
                    }
                }
                is Resource.Error -> {
                    Log.d("UserViewModel", "checkCustomerOrderStatus Error ${result.message}")
                }
            }
        }
    }

    fun registerChef() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onChefRegister(userState.value.userInfo.email,userState.value.userInfo.authToken)) {
                is Resource.Success -> {
                    Log.d("RESULT_DATA", "CHEF REGISTERED SUCCESSFULLY");
                    if(result.data == true)
                    {
                        _userState.value = userState.value.copy(chefStatus = ChefStatus.VERIFIED)
                        userRepository.storeChefStatus(ChefStatus.VERIFIED)

                    }
                    else
                    {
                        _userState.value = userState.value.copy(chefStatus = ChefStatus.NOT_VERIFIED)
                        userRepository.storeChefStatus(ChefStatus.NOT_VERIFIED)
                    }

                }
                is Resource.Error -> {
                    Log.d("RESULT_DATA", "CHEF REGISTERED ERROR ${result.message}");
                    _userState.value = userState.value.copy(chefStatus = ChefStatus.NOT_VERIFIED)
                    userRepository.storeChefStatus(ChefStatus.NOT_VERIFIED)
                }
            }
        }

    }
    fun onAcceptToRentalQuotation(event: AcceptToRentalQuotationEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event){
                is AcceptToRentalQuotationEvent.AcceptToRentalQuotation -> {
                    when (
                        val result =
                            userRepository.onAcceptToRentalQuotation(
                                AcceptToRentalQuotationParam(
                                    userState.value.userInfo.email,
                                    event.quotationId,
                                    event.isAccepted,
                                )
                            )
                    ) {
                        is Resource.Success -> {
                            Log.d("RESULT_DATA_QUOTATION", "SUCCESS : ${result.message}")
                        }

                        is Resource.Error -> {
                            Log.d("RESULT_DATA_QUOTATION", "ERROR : ${result.message}")
                        }
                    }
                }
            }
        }
    }

    fun onCurrentRentalOrderStatus() {
        viewModelScope.launch (Dispatchers.IO) {
            when (val result =
                userRepository.onCurrentRentalOrderStatus(CurrentRentalOrderStatusParam(userState.value.userInfo.email))
            ) {
                is Resource.Success -> {
                    if (result.data != null) {
                        val currentRentalOrderStatus = result.data
                            _currentRentalOrderStatusList.value = CurrentRentalOrderStatusList(currentRentalOrderStatus)
                    }
                }
                is Resource.Error -> {
                    Log.d("UserViewModel", "checkCustomerOrderStatus Error ${result.message}")
                }
            }
        }

    }

    fun getRentChefOrderStatusFromChef(orderID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = userRepository.onRentChefStatusCheck(GetClientRentalOrderStatusByChefParam(userState.value.userInfo.email,orderID))) {
                is Resource.Success -> {
                    Log.d("RESULT_DATA", "CHEF REGISTERED SUCCESSFULLY");
                    if(result.data!=null)
                    {
                        _rentalChefActiveOrderStatus.value = result.data
                    }
                }
                is Resource.Error -> {
                    Log.d("RESULT_DATA", "CHEF REGISTERED ERROR ${result.message}");
                }
            }
        }
    }



}
