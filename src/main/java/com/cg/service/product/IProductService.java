package com.cg.service.product;

import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.dto.ProductCreateReqDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.ProductUpdateReqDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product, Long> {

    Product create(ProductCreateReqDTO productCreateReqDTO, Category category);

    Product update(Long id, ProductUpdateReqDTO productUpdateReqDTO, Category category);

    List<ProductDTO> findAllProductDTO();
}
