package com.springboot.blog.service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(long id);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, Long id);
}
