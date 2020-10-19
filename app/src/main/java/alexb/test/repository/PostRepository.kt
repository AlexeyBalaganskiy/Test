package alexb.test.repository

import alexb.test.api.JSONPlaceHolderApi
import alexb.test.model.Post
import alexb.test.model.Users
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostRepository @Inject constructor(private val apiService:JSONPlaceHolderApi) {
    suspend fun getData():List<Pair<Users, List<Post>>> {
        val users = apiService.getUsers()
        val posts = apiService.getPosts()
        return users.map { user ->
            Pair(user, posts.filter { it.userId == user.id })
        }
    }
}
