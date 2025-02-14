package com.mek.foodrecipeapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.mek.foodrecipeapp.model.Meal

/*
onConflict = OnConflictStrategy.REPLACE ne işe yarar
Eğer veritabanında zaten aynı birincil anahtar (primary key) değerine sahip bir kayıt varsa, bu strateji eski kaydı siler ve yenisiyle değiştirir.
Çakışma olduğunda eski veriyi korumak yerine yenisiyle değiştirmeyi seçmiş olursunuz.
Room'da kullanabileceğiniz diğer çakışma stratejileri:
IGNORE: Çakışma olduğunda yeni veriyi yok sayar, eski veri korunur.
ABORT: Çakışma olduğunda işlem iptal edilir (varsayılan davranış).
FAIL: Çakışma olduğunda hata fırlatılır.
ROLLBACK: Çakışma olduğunda işlem geri alınır.
REPLACE: Eski veriyi yeni veriyle değiştirir.
 */
@Dao
interface mealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Delete
    suspend fun delete(meal:Meal)

    @androidx.room.Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>
}