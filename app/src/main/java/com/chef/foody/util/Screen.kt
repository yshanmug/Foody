package com.chef.foody.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
    object CookHomeScreen: Screen("cook_home_screen")
    object ChefCertificateUploadScreen: Screen("chef_certificate_upload_screen")
    object ChefCertificateVerificationInProgressScreen: Screen("chef_certificate_verification_in_progress_screen")
    object OrderFoodScreen: Screen("order_food")
    object ChefHomeScreen: Screen("chef_home")
    object ChefOrderConfirmedPickup: Screen("chef_pickup")
    object ChefOrderConfirmedDoor: Screen("chef_door_delivery")
    object CartScreen: Screen("cart_screen")
    object UserOrderPickupStatusScreen: Screen("check_pickup_order_status_screen")
    object UserOrderDeliveryStatusScreen: Screen("check_delivery_order_status_screen")
    object ProfileScreen: Screen("profile_screen")
    object ChefHomeRouteScreen: Screen("chef_home_route")

    object RentAChefScreen: Screen("Rent_A_Chef_Screen")

    object FindRentalChef: Screen("Find_Rental_Chef")
    object FindOffersScreen: Screen("find_offers_route")

    object ChefConfirmedScreen: Screen("chef_rental_confirmed_status_screen")

    object RentalChefStatusScreen: Screen("rental_chef_status_screen")
}
