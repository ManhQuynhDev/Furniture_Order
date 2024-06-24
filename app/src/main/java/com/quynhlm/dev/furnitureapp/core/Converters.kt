import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quynhlm.dev.furnitureapp.models.Product

class Converters {
    @TypeConverter
    fun fromProduct(product: Product?): String? {
        return if (product == null) {
            null
        } else {
            Gson().toJson(product)
        }
    }

    @TypeConverter
    fun toProduct(productString: String?): Product? {
        return if (productString == null) {
            null
        } else {
            Gson().fromJson(productString, object : TypeToken<Product>() {}.type)
        }
    }
}