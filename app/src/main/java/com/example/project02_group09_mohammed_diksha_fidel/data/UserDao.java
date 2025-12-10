package com.example.project02_group09_mohammed_diksha_fidel.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Query("SELECT * FROM User WHERE userId = :id")
    User getUserById(int id);

    @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("DELETE FROM User")
    void deleteAll();

    // NEW: get all users (for admin list)
    @Query("SELECT * FROM User ORDER BY username ASC")
    List<User> getAllUsers();

    // NEW: delete one user
    @Delete
    void deleteUser(User user);
}