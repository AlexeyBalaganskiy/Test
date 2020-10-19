package alexb.test


import alexb.test.di.MyApplication
import alexb.test.list.UsersAdapter
import alexb.test.model.Post
import alexb.test.model.Users
import alexb.test.utils.Resource
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var postViewModel: PostViewModel
    private var data:MutableList<Pair<Users, List<Post>>> = mutableListOf()
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = UsersAdapter(this,data)
        list_view.setAdapter(adapter)
        button_start_refresh.setOnClickListener {
            setupObservers()
        }

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            list_view.setIndicatorBounds(list_view.right - 200, list_view.width)
        }
        else {
            list_view.setIndicatorBoundsRelative(list_view.right - 200, list_view.width)
        }
    }

    private fun setupObservers() {
        postViewModel.getData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        list_view.visibility = View.VISIBLE
                        button_start_refresh.visibility = View.GONE
                        progress_bar.visibility = View.GONE
                        text_message.visibility = View.GONE
                        resource.data?.let { dataUser ->
                            retrieveList(dataUser)
                        }
                    }
                    Resource.Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        text_message.visibility = View.VISIBLE
                        button_start_refresh.visibility = View.VISIBLE
                        button_start_refresh.text="Обновить"
                        text_message.text= "Ошибка! Что-то пошло не так"
                    }
                    Resource.Status.LOADING -> {
                        text_message.visibility=View.GONE
                        progress_bar.visibility = View.VISIBLE
                        button_start_refresh.visibility = View.GONE
                    }
                }
            }
        })
    }
    private fun retrieveList(dataUser: List<Pair<Users, List<Post>>>) {
            data.clear()
            data.addAll(dataUser)
            adapter.notifyDataSetChanged()
    }
}