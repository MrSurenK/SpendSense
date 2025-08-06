package com.MrSurenK.SpendSense_BackEnd.service_test;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditCatDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.NewCatDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.TransactionType;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import com.MrSurenK.SpendSense_BackEnd.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("Test funcionality of custom category feature")
public class CategoryServiceTest {

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private UserAccountRepo userAccountRepo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Test for category creation")
    void addCategory(){
        //Arrange a dto
        NewCatDto dto = new NewCatDto();

        dto.setName("Test_Category");
        dto.setTransactionType(TransactionType.EXPENSE);

        UserAccount user = new UserAccount();
        user.setId(1);

        //Act
        when(categoryRepo.existsByUserAccount_IdAndName(user.getId(),dto.getName())).thenReturn(false);
        when(userAccountRepo.findById(user.getId())).thenReturn(Optional.of(user));
        Category newCat = categoryService.createCat(dto, user.getId());

        //Assert
        assertEquals(dto.getName(),newCat.getName());
        assertEquals(dto.getTransactionType(),newCat.getTransactionType());
        assertFalse(newCat.isSystem());
        assertFalse(newCat.isDeleted());
        assertEquals(user, newCat.getUserAccount());


    }

    @Test
    @DisplayName("Edit an existing category")
    void editCategory(){
        EditCatDto dto = new EditCatDto();

        dto.setName("New_Category");
        dto.setTransactionType(TransactionType.EXPENSE);

        int userId = 1;

        Category oldCat = new Category();
        oldCat.setId(100L);
        oldCat.setName("Old_Category");
        oldCat.setTransactionType(TransactionType.INCOME);

        when(categoryRepo.getValidCat(userId, oldCat.getId())).thenReturn(Optional.of(oldCat));
        categoryService.editCat(dto, userId, oldCat.getId());

        assertEquals(dto.getName(),oldCat.getName());
        assertEquals(dto.getTransactionType(),oldCat.getTransactionType());
    }



}
