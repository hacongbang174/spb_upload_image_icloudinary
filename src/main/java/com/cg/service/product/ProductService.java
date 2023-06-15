package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.ProductCreateReqDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.ProductUpdateReqDTO;
import com.cg.repository.IProductAvatarRepository;
import com.cg.repository.IProductRepository;
import com.cg.service.upload.IUploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductAvatarRepository productAvatarRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product create(ProductCreateReqDTO productCreateReqDTO, Category category) {

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatarRepository.save(productAvatar);

        uploadAndSaveProductImage(productCreateReqDTO, productAvatar);

        Product product = productCreateReqDTO.toProduct(category);
        product.setQuantity(0L);
        product.setProductAvatar(productAvatar);

        productRepository.save(product);

        return product;
    }

    @Override
    public Product update(Long id, ProductUpdateReqDTO productUpdateReqDTO, Category category) {
        ProductAvatar productAvatar = new ProductAvatar();

        productAvatarRepository.save(productAvatar);

        uploadAndSaveProductImage(productUpdateReqDTO.toProductCreateReqDTO(), productAvatar);

        Product product = productUpdateReqDTO.toProductChangeImage(category);
        product.setId(id);
        product.setProductAvatar(productAvatar);

        productRepository.save(product);

        return product;
    }

    @Override
    public List<ProductDTO> findAllProductDTO() {
        return productRepository.findAllProductDTO();
    }

    private void uploadAndSaveProductImage(ProductCreateReqDTO productCreateReqDTO, ProductAvatar productAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(productCreateReqDTO.getAvatar(), uploadUtils.buildImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(UploadUtils.IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            productAvatarRepository.save(productAvatar);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
        productAvatarRepository.delete(product.getProductAvatar());
    }

    @Override
    public void deleteById(Long id) {

    }
}
