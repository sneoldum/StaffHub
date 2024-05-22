package com.emplomanage.staffhub.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.emplomanage.staffhub.model.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM items WHERE name = :name LIMIT 1")
    LiveData<Item> getItemByName(String name);

    @Query("SELECT * FROM items ORDER BY name ASC")
    LiveData<List<Item>> getAllItems();


    @Query("SELECT * FROM items WHERE name = :name LIMIT 1")
    Item getItemByNameSync(String name);
}
