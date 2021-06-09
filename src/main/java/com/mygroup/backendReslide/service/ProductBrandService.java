package com.mygroup.backendReslide.service;

import com.mygroup.backendReslide.dto.ProductBrandDto;
import com.mygroup.backendReslide.exceptions.alreadyExists.ProductBrandExistsException;
import com.mygroup.backendReslide.exceptions.notFound.ProductBrandNotFoundException;
import com.mygroup.backendReslide.mapper.ProductBrandMapper;
import com.mygroup.backendReslide.model.ProductBrand;
import com.mygroup.backendReslide.model.status.DatabaseStatus;
import com.mygroup.backendReslide.repository.ProductBrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductBrandService {

    private final ProductBrandRepository productBrandRepository;
    private final ProductBrandMapper productBrandMapper;

    public void create(ProductBrandDto productBrandDto) {
        if(productBrandRepository.findByName(productBrandDto.getName()).isPresent()){
            throw new ProductBrandExistsException(productBrandDto.getName());
        }
        // Using the mapper instead of doing setters and getters.
        ProductBrand productBrand = productBrandMapper.mapToEntity(productBrandDto);
        // Status cannot be mapped because the mapper implementation doesn't import the class used to represent it.
        productBrand.setStatus(DatabaseStatus.ACTIVE);
        // Store it.
        productBrandRepository.save(productBrand);
    }

    public void update(ProductBrandDto productBrandDto) {
        // Searches the brand.
        ProductBrand productBrand = productBrandRepository.findById(productBrandDto.getId())
                .orElseThrow(()->new ProductBrandNotFoundException(productBrandDto.getId()));
        // Check if the brand exists.
        // If the old name is different than the new one and it's taken throws an error.
        if(!productBrandDto.getName().equals(productBrand.getName()) &&
                productBrandRepository.findByName(productBrandDto.getName()).isPresent()){
            throw new ProductBrandExistsException(productBrandDto.getName());
        }
        // Updates the brand information.
        productBrand.setName(productBrandDto.getName());
        productBrand.setNotes(productBrandDto.getNotes());
        // Stores the changes.
        productBrandRepository.save(productBrand);
    }

    public void deactivate(ProductBrandDto productBrandDto) {
        // Deactivates it and stores the changes.
        ProductBrand productBrand = productBrandRepository.findById(productBrandDto.getId())
                .orElseThrow(()->new ProductBrandNotFoundException(productBrandDto.getId()));
        productBrand.setStatus(DatabaseStatus.DELETED);
        productBrandRepository.save(productBrand);
    }

    public List<ProductBrandDto> getAll() {
        return productBrandRepository.findByStatus(DatabaseStatus.ACTIVE)
                .stream()
                .map(productBrandMapper::mapToDto)
                .collect(Collectors.toList());

    }

    public List<ProductBrandDto> getByName(String name) {
        return productBrandRepository.findByNameContainsAndStatus(name, DatabaseStatus.ACTIVE)
                .stream()
                .map(productBrandMapper::mapToDto)
                .collect(Collectors.toList());

    }
}