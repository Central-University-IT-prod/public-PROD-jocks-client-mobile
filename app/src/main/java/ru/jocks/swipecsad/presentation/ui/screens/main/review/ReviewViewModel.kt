package ru.jocks.swipecsad.presentation.ui.screens.main.review

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import ru.jocks.domain.review.models.PromoCode
import ru.jocks.data.reviews.models.ReviewFields
import ru.jocks.data.reviews.repository.ReviewsRepository
import timber.log.Timber
import kotlin.properties.Delegates


sealed interface ReviewFieldsUiState {
    data class Success(val fields: ReviewFields) : ReviewFieldsUiState
    data object Loading : ReviewFieldsUiState

    data object Error : ReviewFieldsUiState
}

sealed interface ReviewResponseUiState {
    data class Success(val promoCode: PromoCode) : ReviewResponseUiState
    data object Loading : ReviewResponseUiState

    data object Error : ReviewResponseUiState
}

class ReviewViewModel(private val application: android.app.Application) : AndroidViewModel(application)  {

    private val reviewsRepository : ReviewsRepository by KoinJavaComponent.inject(ReviewsRepository::class.java)

    var reviewFieldsUiState : ReviewFieldsUiState by mutableStateOf(ReviewFieldsUiState.Loading)
        private set


    var reviewResponseUiState : ReviewResponseUiState by mutableStateOf(ReviewResponseUiState.Loading)
        private set

    private var checkId by Delegates.notNull<Int>()

    fun getFields(id : Int) {
        checkId = id
        viewModelScope.launch {
            reviewFieldsUiState = ReviewFieldsUiState.Loading
            reviewFieldsUiState = try {
                ReviewFieldsUiState.Success(reviewsRepository.getFields(id)!!)
            } catch (_ : Exception) {
                ReviewFieldsUiState.Error
            }
        }
    }


    suspend fun sendReport() : Boolean {
        if (reviewFieldsUiState !is ReviewFieldsUiState.Success) {
            return false
        }

        val form = (reviewFieldsUiState as ReviewFieldsUiState.Success)

        reviewResponseUiState = ReviewResponseUiState.Loading

        reviewResponseUiState = try {
            ReviewResponseUiState.Success(reviewsRepository.sendReview(
                baseReviewFields = form.fields.baseFields,
                goodsReviewFields = form.fields.goodsFields,
                businessId = form.fields.businessId,
                checkId = checkId
            )!!.promoCode)
        } catch (e : Exception) {
            Toast.makeText(application, e.message, Toast.LENGTH_SHORT).show()
            ReviewResponseUiState.Error
        }

        return reviewResponseUiState is ReviewResponseUiState.Success

    }

}