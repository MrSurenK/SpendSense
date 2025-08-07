package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Category;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    //JPQL to ensure user only has access to default cats and user created cats
    @Query(value = "Select c from Category c where c.id = :catId and (c.userAccount.id = :userId or c.userAccount.id is null) and c.isDeleted = false")
    Optional<Category> getValidCat(@Param("userId")Integer userId, @Param("catId")Long catId);

    @Query(value="Select c from Category c where (c.userAccount.id = :userId or c.userAccount.id is null) and c.isDeleted = false")
    List<Category> getAllValidCats(@Param("userId")Integer userId);

    @Query(value = "Select case when count(c) > 0 then true else false end from Category c where c.isDeleted=false and c.userAccount.id = :userId and c.name=:catName")
    boolean isActiveUserCreatedCat(@Param("userId")Integer userId, @Param("catName")String name);

    @Query(value = "Select case when count(c) > 0 then true else false end from Category c where c.isDeleted=true and c.userAccount.id = :userId and c.name=:catName")
    boolean isDeletedUserCreatedCat(@Param("userId")Integer userId, @Param("catName")String name);

    Category findByNameAndUserAccount_id(String name,Integer userId);
}
