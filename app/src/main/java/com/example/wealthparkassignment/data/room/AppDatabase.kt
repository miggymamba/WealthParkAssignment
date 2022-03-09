package  com.example.wealthparkassignment.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse

@Database(
    entities = [GetCitiesResponse.GetCitiesResponseItem::class, GetFoodsResponse.GetFoodsResponseItem::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun citiesDAO(): CitiesDAO
    abstract fun foodsDAO(): FoodsDAO

}