package ru.jocks.domain.review.models

interface ProfileState {
    data object None :  ProfileState
    data class Success(val token: String, val businessId: String) :  ProfileState
    data object Loading :  ProfileState
    data class Error(val message : String) :  ProfileState
}