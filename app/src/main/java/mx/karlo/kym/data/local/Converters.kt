package mx.karlo.kym.data.local

import androidx.room.TypeConverter
import mx.karlo.kym.core.model.Uom

class Converters {
    @TypeConverter
    fun fromUom(value: Uom): String {
        return value.name
    }

    @TypeConverter
    fun toUom(value: String): Uom {
        return Uom.valueOf(value)
    }
}