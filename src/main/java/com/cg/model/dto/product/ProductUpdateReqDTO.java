package com.cg.model.dto.product;

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

        String title = productUpdateReqDTO.title;
        String quantity = productUpdateReqDTO.quantity;
        String price = productUpdateReqDTO.price;
        String unit = productUpdateReqDTO.unit;


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

        if (title == null) {
            errors.rejectValue("title", "title.null", "Title là bắt buộc");
        } else {
            if (title.trim().length() == 0) {
                errors.rejectValue("title", "title.empty", "Title là không được để trống");
            } else {
                if (title.trim().length() <= 5) {
                    errors.rejectValue("title", "title.min", "Title phải có ít nhất 5 ký tự");
                }
                if (title.trim().length() > 100) {
                    errors.rejectValue("title", "title.length.min-max", "Title phải nhỏ hơn 100 ký tự");
                }
            }
        }

        if (price == null) {
            errors.rejectValue("price", "price.null", "Giá tiền là bắt buộc");
        } else {
            if (price.trim().length() == 0) {
                errors.rejectValue("price", "price.empty", "Giá tiền là không được để trống");
            } else {
                if (!price.matches("\\d+")) {
                    errors.rejectValue("price", "price.match", "Giá tiền phải là ký tự số");
                }
                if (!Pattern.matches("\\b([1-9]\\d{2,11}|999999999)\\b", price)) {
                    errors.rejectValue("price", "price.length.min-max", "Giá tiền phải lớn hơn 100 VNĐ và nhỏ hơn 999.999.999 VNĐ");
                }
            }
        }

        if (unit == null) {
            errors.rejectValue("unit", "unit.null", "Đơn vị tính là bắt buộc");
        } else {
            if (unit.trim().length() == 0) {
                errors.rejectValue("unit", "unit.empty", "Đơn vị tính là không được để trống");
            } else {
                if (unit.trim().length() <= 2) {
                    errors.rejectValue("unit", "unit.min", "Đơn vị tính phải có ít nhất 3 ký tự");
                }
                if (unit.trim().length() > 20) {
                    errors.rejectValue("unit", "unit.length.min-max", "Đơn vị tính phải nhỏ hơn 20 ký tự");
                }
            }
        }
    }
}
