package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.CustomExceptions.ConflictException;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.NewCatDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepo categoryRepo;

    private final UserAccountRepo userAccountRepo;


    public CategoryService(CategoryRepo categoryRepo, UserAccountRepo userAccountRepo) {
        this.categoryRepo = categoryRepo;
        this.userAccountRepo = userAccountRepo;
    }


    public Category createCat(NewCatDto dto, Integer userId){

        //Create new cat object and pass in
        Category newCat = new Category();

        //Check if the same user has already created a similar cat name and disallow
        String catName = dto.getName();

        if(categoryRepo.existsByUserIdAndName(userId, catName)){
            throw new ConflictException("This category created by user already exists");
        }

        //Pass in dto details to new object
        newCat.setName(catName);
        newCat.setTransactionType(dto.getTransactionType());
        //Fill in additional mandatory info in new object

        //isSystem and isDeleted class variables are set to false by default if not provided
        UserAccount user = userAccountRepo.findById(userId).orElseThrow
                (()-> new EntityNotFoundException("User Account not found"));

        newCat.setUserAccount(user);

        //save and return new object
        categoryRepo.save(newCat);

        return newCat;
    }





}
