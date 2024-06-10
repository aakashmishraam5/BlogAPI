package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category=modelMapper.map(categoryDto,Category.class);
        Category savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(long id) {
        Category category=categoryRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Category Not found " ,"id", id));


        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map((category) -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
        Category category=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not fount","id",id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setId(categoryDto.getId());
        Category updateCategory=categoryRepository.save(category);
        return modelMapper.map(updateCategory,CategoryDto.class);
    }
}
