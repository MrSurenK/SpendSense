package com.MrSurenK.SpendSense_BackEnd.controller;

import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.EditCatDto;
import com.MrSurenK.SpendSense_BackEnd.dto.requestDto.NewCatDto;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.ApiResponse;
import com.MrSurenK.SpendSense_BackEnd.dto.responseDto.CategoryResponse;
import com.MrSurenK.SpendSense_BackEnd.model.Category;
import com.MrSurenK.SpendSense_BackEnd.model.UserAccount;
import com.MrSurenK.SpendSense_BackEnd.service.CategoryService;
import com.MrSurenK.SpendSense_BackEnd.service.SecurityContextService;
import com.MrSurenK.SpendSense_BackEnd.util.EntityToDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    private final SecurityContextService securityContextService;


    public CategoryController(CategoryService categoryService, SecurityContextService securityContextService){
        this.categoryService = categoryService;
        this.securityContextService = securityContextService;
    }


    //POST new category
    @PostMapping("/cat/createCat")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCatApi(@RequestBody NewCatDto dto){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Category newCat = categoryService.createCat(dto, userId);

        CategoryResponse res = EntityToDtoMapper.mapEntityToCatResponseDto(newCat);


        //Send the response to API response
        ApiResponse<CategoryResponse> apiRes = new ApiResponse<>();
        apiRes.setSuccess(true);
        apiRes.setMessage("Successfully created new category");
        apiRes.setData(res);

        return ResponseEntity.ok(apiRes);
    }

    //GET all category created by user and is not deleted
    @GetMapping("/cat/allCategories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCatsApi(){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();
        List<Category> allCat= categoryService.getAllCats(userId); //all non deleted cats
        List<CategoryResponse> catRes = new ArrayList<>();
        for (Category category : allCat) {
            catRes.add(EntityToDtoMapper.mapEntityToCatResponseDto(category));
        }

        ApiResponse<List<CategoryResponse>> res = new ApiResponse<>();
        res.setSuccess(true);
        res.setMessage("Retrieved all categories");
        res.setData(catRes);

        return  ResponseEntity.ok(res);
    }


    //PATCH category
    @PatchMapping("/cat/editCat/{catId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> editCatApi(@RequestBody EditCatDto dto, @PathVariable Long catId){
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Category editedCat = categoryService.editCat(dto,userId,catId);

        CategoryResponse res = EntityToDtoMapper.mapEntityToCatResponseDto(editedCat);

        ApiResponse<CategoryResponse> apiRes = new ApiResponse<>();

        apiRes.setSuccess(true);
        apiRes.setMessage("Category changes saved");
        apiRes.setData(res);

        return ResponseEntity.ok(apiRes);
    }

    //Soft delete
    @PatchMapping("/cat/deleteCat/{catId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> deleteCatApi(@PathVariable Long catId)
            throws IllegalAccessException {
        UserAccount user = securityContextService.getUserFromSecurityContext();
        int userId = user.getId();

        Category deleteThisCat = categoryService.softDeleteCat(userId, catId);

        CategoryResponse res = EntityToDtoMapper.mapEntityToCatResponseDto(deleteThisCat);

        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>();

        apiResponse.setSuccess(true);
        apiResponse.setMessage("Category succesfully deleted");
        apiResponse.setData(res);

        return ResponseEntity.ok(apiResponse);
    }

}
