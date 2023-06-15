package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class ProductUpdateReqDTO implements Validator {
    private String title;
    private String quantity;
    private String price;
    private String unit;
    private String description;
    private Long categoryId;
    private MultipartFile avatar;
    private ProductAvatar productAvatar;

    public Product toProductChangeImage(Category category) {
        return new Product()
                .setTitle(title)
                .setQuantity(Long.parseLong(quantity))
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setUnit(unit)
                .setDescription(description)
                .setCategory(category)
                ;
    }

    public Product toProductNoChangeImage(Category category) {
        return new Product()
                .setTitle(title)
                .setQuantity(Long.parseLong(quantity))
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setUnit(unit)
                .setDescription(description)
                .setCategory(category)
                ;
    }

    public ProductCreateReqDTO toProductCreateReqDTO () {
        return new ProductCreateReqDTO()
                .setTitle(title)
                .setPrice(price)
                .setUnit(unit)
                .setDescription(description)
                .setCategoryId(categoryId)
                .setAvatar(avatar)
                ;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductUpdateReqDTO productUpdateReqDTO = (ProductUpdateReqDTO) target;

        String quantity = productUpdateReqDTO.quantity;
        String price = productUpdateReqDTO.price;


        if (quantity == null) {
            errors.rejectValue("quantity", "quantity.null", "Số lượng là bắt buộc");
        } else {
            if (quantity.trim().length() == 0) {
                errors.rejectValue("quantity", "quantity.empty", "Số lượng là không được để trống");
            } else {
                if (!quantity.matches("\\d+")) {
                    errors.rejectValue("quantity", "quantity.match", "Số lượng phải là ký tự số");
                    if(Long.parseLong(quantity) < 0 || Long.parseLong(quantity) > 1000) {
                        errors.rejectValue("quantity", "quantity.match", "Số lượng phải lớn hơn 0 và nhỏ hơn hoặc bằng 1000");
                    }
                }
            }
        }

        if (price == null) {
            errors.rejectValue("price", "price.null", "Giá tiền là bắt buộc");
        } else {
            if (price.trim().length() == 0) {
                errors.rejectValue("v", "price.empty", "Giá tiền là không được để trống");
            } else {
                if (!price.toString().matches("\\d+")) {
                    errors.rejectValue("price", "price.match", "Giá tiền phải là ký tự số");
                }
                if (Long.parseLong(price) <= 0 || Long.parseLong(price) > 1000000) {
                    errors.rejectValue("price", "price.length.min-max", "Giá tiền phải lớn hơn 0 và nhỏ hơn 1.000.000đ");
                }
            }
        }
    }
}
