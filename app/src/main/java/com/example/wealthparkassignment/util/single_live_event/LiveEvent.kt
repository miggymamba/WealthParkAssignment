package com.example.wealthparkassignment.util.single_live_event

sealed class LiveEvent {
    class Loading(val loadingMessage : String = "") : LiveEvent()
    class Success(val successMessage : String = "") : LiveEvent()
    class Error(val errorMessage : String = "") : LiveEvent()
    object Loaded : LiveEvent()
}