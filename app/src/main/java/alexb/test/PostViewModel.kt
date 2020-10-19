package alexb.test

import alexb.test.utils.Resource
import alexb.test.repository.PostRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostViewModel @Inject constructor(private val postRepository: PostRepository):ViewModel() {
    fun getData() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            delay(1000)
            emit(Resource.success(data = postRepository.getData()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error"))
        }
    }
}
