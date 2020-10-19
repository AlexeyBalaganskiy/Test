package alexb.test.api

import alexb.test.model.Post
import alexb.test.model.Users

import retrofit2.http.GET

interface JSONPlaceHolderApi {
    @GET("posts")
    suspend fun getPosts():List<Post>
    @GET("users")
    suspend fun getUsers():List<Users>
}