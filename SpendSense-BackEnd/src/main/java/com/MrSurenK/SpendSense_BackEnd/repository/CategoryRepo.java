package com.MrSurenK.SpendSense_BackEnd.repository;

import com.MrSurenK.SpendSense_BackEnd.model.Category;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    //JPQL to ensure user only has access to default cats and user created cats
    @Query(value = "Select c from Category c where c.id = :catId and (c.userAccount.id = :userId or c.userAccount.id is null)")
    Optional<Category> getValidCat(@Param("userId")Integer userId, @Param("catId")Long catId);

}
