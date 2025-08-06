package com.MrSurenK.SpendSense_BackEnd.service;

import com.MrSurenK.SpendSense_BackEnd.CustomExceptions.ConflictException;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditCatDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.NewCatDto;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.repository.CategoryRepo;
import com.MrSurenK.SpendSense_BackEnd.repository.UserAccountRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

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

        if(categoryRepo.existsByUserAccount_IdAndName(userId, catName)){
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


    public Category editCat(EditCatDto dto, int userId, Long catId){
        //Get the cat to be edited
        Category cat = categoryRepo.getValidCat(userId, catId).orElseThrow(
                ()-> new EntityNotFoundException("Category does not exist"));
        //Send dto form to edit information in category
        if(dto.getName()!=null){
            cat.setName(dto.getName());
        }
        if(dto.getTransactionType()!= null){
            cat.setTransactionType(dto.getTransactionType());
        }
        if(dto.getName() == null && dto.getTransactionType() ==null){
            throw new IllegalArgumentException("No inputs were given to category");
        }
        //save category
        categoryRepo.save(cat);

        return cat; //So that front end can debug easier
    }
}
