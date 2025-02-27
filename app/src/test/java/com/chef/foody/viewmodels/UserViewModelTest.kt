package com.chef.foody.viewmodels

import android.app.Instrumentation
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.chef.foody.MainCoroutineRule
import com.chef.foody.R
import com.chef.foody.data.remote.CartInfo
import com.chef.foody.data.repository.FakeUserRepositoryImpl
import com.chef.foody.events.CartEvent
import com.chef.foody.events.CartItem
import com.chef.foody.events.FoodOrderObj
import com.chef.foody.screens.convertOrderToString
import com.chef.foody.util.CredentialValidation
import com.chef.foody.util.Screen
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class UserViewModelTest {

    private lateinit var viewModel: UserViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = UserViewModel(FakeUserRepositoryImpl())
    }

    // *******************************************
    // *******************************************
    // ************ TESTING FOOD LIST ************
    // *******************************************
    // *******************************************

    @Test
    fun testIncreaseTheQuantityOfTheItemInTheFoodList() {
        viewModel.onCartEvent(CartEvent.ChangeQuantity(change = "+", item = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 0)))
        assertThat(viewModel.foodState.value.foodList.get(viewModel.foodState.value.foodList.indexOf(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 1))).quantity).isEqualTo(1)
    }

    @Test
    fun testDecreaseTheQuantityOfTheItemInTheFoodList() {
        viewModel.onCartEvent(CartEvent.ChangeQuantity(change = "-", item = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 1)))
        assertThat(viewModel.foodState.value.foodList.get(viewModel.foodState.value.foodList.indexOf(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 0))).quantity).isEqualTo(0)
    }


    // *******************************************
    // *******************************************
    // ************ TESTING CART LIST ************
    // *******************************************
    // *******************************************
    @Test
    fun testItemIsAddedInTheCart() {
        viewModel.onCartEvent(CartEvent.AddToCart(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5)))
        assertThat(viewModel.cartState.value.cartList.indexOf(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 5, objID = 2, title = "Pepperoni Pizza"))).isGreaterThan(-1)
    }

    @Test
    fun testIncreaseTheQuantityOfTheItemInTheCartList() {
        viewModel.onCartEvent(CartEvent.AddToCart(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5)))
       viewModel.onCartEvent(CartEvent.ChangeCartQuantity(change = "+", item = CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 5, objID = 2, title = "Pepperoni Pizza")))
        assertThat(viewModel.cartState.value.cartList.indexOf(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 6, objID = 2, title = "Pepperoni Pizza"))).isGreaterThan(-1)
    }

    @Test
    fun testDecreaseTheQuantityOfTheItemInTheCartList() {
        viewModel.onCartEvent(CartEvent.AddToCart(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5)))
        viewModel.onCartEvent(CartEvent.ChangeCartQuantity(change = "-", item = CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 5, objID = 2, title = "Pepperoni Pizza")))
        assertThat(viewModel.cartState.value.cartList[viewModel.cartState.value.cartList.indexOf(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 4, objID = 2, title = "Pepperoni Pizza"))].quantity).isEqualTo(4)
    }

    @Test
    fun testDecreaseTheQuantityOfTheItemInTheCartListDeletesAtZero() {
        viewModel.onCartEvent(CartEvent.AddToCart(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 1)))
        viewModel.onCartEvent(CartEvent.ChangeCartQuantity(change = "-", item = CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 1), quantity = 5, objID = 2, title = "Pepperoni Pizza")))
        val updatedCartList = viewModel.cartState.value.cartList
        assertThat(updatedCartList).doesNotContain(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 5), quantity = 0, objID = 2, title = "Pepperoni Pizza"))
    }
    @Test
    fun testItemIsDeletedFromTheCart() {
        viewModel.onCartEvent(CartEvent.AddToCart(FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 3)))
        viewModel.onCartEvent(CartEvent.DeleteCartItem(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 3), quantity = 3, objID = 2, title = "Pepperoni Pizza")))
        assertThat(viewModel.cartState.value.cartList.indexOf(CartItem(foodItem = FoodOrderObj(2, "Pepperoni Pizza", "Treat yourself to the comforting aroma and taste of our Homemade pizza.", R.drawable.pngegg2, "NON_VEG", 3), quantity = 3, objID = 2, title = "Pepperoni Pizza"))).isEqualTo(-1)
    }

    // *******************************************
    // *******************************************
    // ************ TESTING LOGIN/SIGNUP ****************
    // *******************************************
    // *******************************************

    // Email Validation Testing
    @Test
    fun testEmailValidationWithEmptyString()
    {assertThat(CredentialValidation.emailValid("")).isEqualTo(false)}

//    cant run these 2 tests because dependency on email validation pattern matcher
//    @Test
//    fun testEmailValidationWithNoAtSign()
//    {assertThat(CredentialValidation.emailValid("abcgoogle.com")).isEqualTo(false)}
//
//    @Test
//    fun testEmailValidationWithCorrectString()
//    {assertThat(CredentialValidation.emailValid("kishore@hotmail.com")).isEqualTo(true)}

    // Password Validation Testing
    @Test
    fun testPasswordValidationWithShortString()
    {assertThat(CredentialValidation.passwordValid("pAsS12@")).isEqualTo(false)}
    @Test
    fun testPasswordValidationWithEmptyString()
    {assertThat(CredentialValidation.passwordValid("")).isEqualTo(false)}
    @Test
    fun testPasswordValidationWithNoLowerCharacters()
    {assertThat(CredentialValidation.passwordValid("PASS@123")).isEqualTo(true)}
    @Test
    fun testPasswordValidationWithTooManyCharacters()
    {assertThat(CredentialValidation.passwordValid("PASSword@123456789")).isEqualTo(false)}
    @Test
    fun testPasswordValidationWithNoSpecialCharacters()
    {assertThat(CredentialValidation.passwordValid("PASSword1234")).isEqualTo(true)}
    @Test
    fun testPasswordValidationWithInvalidSpecialCharacters()
    {assertThat(CredentialValidation.passwordValid("PASSwd&&1")).isEqualTo(false)}
    @Test
    fun testPasswordValidationWithNoUpperCharacters()
    {assertThat(CredentialValidation.passwordValid("pass@1234")).isEqualTo(true)}
    @Test
    fun testPasswordValidationWithNoNumbers()
    {assertThat(CredentialValidation.passwordValid("P@SSword")).isEqualTo(true)}
    @Test
    fun testPasswordValidationWithCorrectFormat()
    {assertThat(CredentialValidation.passwordValid("Password@123")).isEqualTo(true)}

    // New Password Validation Testing
    @Test
    fun testNewPasswordValidationWithShortString()
    {assertThat(CredentialValidation.newPasswordValid("pAsS12@")).isEqualTo(false)}
    @Test
    fun testNewPasswordValidationWithEmptyString()
    {assertThat(CredentialValidation.newPasswordValid("")).isEqualTo(false)}
    @Test
    fun testNewPasswordValidationWithNoLowerCharacters()
    {assertThat(CredentialValidation.newPasswordValid("PASS@123")).isEqualTo(true)}
    @Test
    fun testNewPasswordValidationWithTooManyCharacters()
    {assertThat(CredentialValidation.newPasswordValid("PASSword@123456789")).isEqualTo(false)}
    @Test
    fun testNewPasswordValidationWithNoSpecialCharacters()
    {assertThat(CredentialValidation.newPasswordValid("PASSword1234")).isEqualTo(true)}
    @Test
    fun testNewPasswordValidationWithInvalidSpecialCharacters()
    {assertThat(CredentialValidation.newPasswordValid("PASSwd&&1")).isEqualTo(false)}
    @Test
    fun testNewPasswordValidationWithNoUpperCharacters()
    {assertThat(CredentialValidation.newPasswordValid("pass@1234")).isEqualTo(true)}
    @Test
    fun testNewPasswordValidationWithNoNumbers()
    {assertThat(CredentialValidation.newPasswordValid("P@SSword")).isEqualTo(true)}
    @Test
    fun testNewPasswordValidationWithCorrectFormat()
    {assertThat(CredentialValidation.newPasswordValid("Password@123")).isEqualTo(true)}

    // Confirm Password Testing
    @Test
    fun testConfirmPasswordValidationWithShortString()
    {assertThat(CredentialValidation.confirmPasswordValid("pAsS12@")).isEqualTo(false)}
    @Test
    fun testConfirmPasswordValidationWithEmptyString()
    {assertThat(CredentialValidation.confirmPasswordValid("")).isEqualTo(false)}
    @Test
    fun testConfirmPasswordValidationWithNoLowerCharacters()
    {assertThat(CredentialValidation.confirmPasswordValid("PASS@123")).isEqualTo(true)}
    @Test
    fun testConfirmPasswordValidationWithTooManyCharacters()
    {assertThat(CredentialValidation.confirmPasswordValid("PASSword@123456789")).isEqualTo(false)}
    @Test
    fun testConfirmPasswordValidationWithNoSpecialCharacters()
    {assertThat(CredentialValidation.confirmPasswordValid("PASSword1234")).isEqualTo(true)}
    @Test
    fun testConfirmPasswordValidationWithInvalidSpecialCharacters()
    {assertThat(CredentialValidation.confirmPasswordValid("PASSwd&&1")).isEqualTo(false)}
    @Test
    fun testConfirmPasswordValidationWithNoUpperCharacters()
    {assertThat(CredentialValidation.confirmPasswordValid("pass@1234")).isEqualTo(true)}
    @Test
    fun testConfirmPasswordValidationWithNoNumbers()
    {assertThat(CredentialValidation.confirmPasswordValid("P@SSword")).isEqualTo(true)}
    @Test
    fun testConfirmPasswordValidationWithCorrectFormat()
    {assertThat(CredentialValidation.confirmPasswordValid("Password@123")).isEqualTo(true)}


    // *********************************************
    // *********************************************
    // ************ TESTING FIND OFFER  ************
    // *********************************************
    // *********************************************

    @Test
    fun testConvertOrderToString(){
        val cart_info = listOf(
            CartInfo("Pizza", 3),
            CartInfo("Burger", 2)
        )
        assertThat(convertOrderToString(cart_info)).isEqualTo("Pizza, Burger")
    }

}