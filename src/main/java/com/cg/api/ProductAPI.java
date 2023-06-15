package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.*;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ValidateUtils validateUtils;


    @GetMapping
    public ResponseEntity<List<?>> findAllProduct() {
        try {
            List<ProductDTO> productDTOS = productService.findAllProductDTO();

            if (productDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(productDTOS, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable String id) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        try {
            Optional<Product> product = productService.findById(productId);

            if (product.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(product.get().toProductDTO(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/categories")
    public ResponseEntity<List<?>> findAllCategory() {
        try {
            List<Category> categories = categoryService.findAll();

            if (categories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categories, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute ProductCreateReqDTO productCreateReqDTO) {

        Category category = categoryService.findById(productCreateReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục không tồn tại");
        });

        Product product = productService.create(productCreateReqDTO, category);
        ProductCreateResDTO productCreateResDTO = product.toProductCreateResDTO();

        return new ResponseEntity<>(productCreateResDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        Optional<Product> product = productService.findById(productId);

        if (product.isPresent()) {
            productService.delete(product.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @ModelAttribute ProductUpdateReqDTO productUpdateReqDTO) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        Optional<Product> productOptional = productService.findById(productId);

        Category category = categoryService.findById(productUpdateReqDTO.getCategoryId()).orElseThrow(() -> {
            throw new DataInputException("Danh mục không tồn tại");
        });

        if (productUpdateReqDTO.getAvatar() == null) {
            Product product = productUpdateReqDTO.toProductNoChangeImage(category);
            product.setId(productOptional.get().getId());
            product.setProductAvatar(productOptional.get().getProductAvatar());
            productService.save(product);
            return new ResponseEntity<>(product.toProductCreateResDTO(), HttpStatus.OK);
        }

        if (productOptional.isPresent()) {
            Product product = productService.update(productOptional.get().getId() ,productUpdateReqDTO, category);
            ProductUpdateResDTO productUpdateResDTO = product.toProductUpdateResDTO();

            return new ResponseEntity<>(productUpdateResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Invalid product information");
        }

    }
}
