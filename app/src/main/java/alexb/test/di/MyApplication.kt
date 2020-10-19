package alexb.test.di

import alexb.test.MainActivity
import android.app.Application
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}
class MyApplication:Application(){
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}
