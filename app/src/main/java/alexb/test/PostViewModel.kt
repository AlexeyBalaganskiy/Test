package alexb.test

import alexb.test.model.Post
import alexb.test.model.Users
import alexb.test.utils.Resource
import alexb.test.repository.PostRepository
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostViewModel @Inject constructor(private val postRepository: PostRepository):ViewModel() {
    private val refreshTrigger = MutableLiveData<Unit>()
    val data = refreshTrigger.switchMap {
        liveData(Dispatchers.IO){
            emit(Resource.loading(data = null))
            val resultState = try {
                delay(1000)
                Resource.success(postRepository.getData())
        }
            catch (exception: Exception)
            {
                Resource.error(data = null, message = exception.message ?: "Error")
            }
            emit(resultState)
        }
    }
    fun update(){
        refreshTrigger.postValue(Unit)
    }
}

