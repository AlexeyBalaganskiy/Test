package alexb.test.base

import alexb.test.PostViewModel
import alexb.test.api.JSONPlaceHolderApi
import alexb.test.repository.PostRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory@Inject constructor(private val apiService: JSONPlaceHolderApi):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(PostRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}