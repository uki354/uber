
package com.uki.uber.anotation;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        boolean result = true;

        String contentType = value.getContentType();
        assert contentType != null;
        if(!isSupportedContentType(contentType)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only png or jpg images are allowed").addConstraintViolation();
            result = false;
            return result;
        }
        double sizeInKB = value.getSize() * 0.000977;
        if(sizeInKB > 120){
            context.buildConstraintViolationWithTemplate(
                    "Too large image file").addConstraintViolation();
            result = false;
        }

        return  result;
    }


    private boolean isSupportedContentType(String contentType){
        return contentType.equals("image/png") ||
                contentType.equals("image/jpg") ||
                contentType.equals("image/jpeg");
    }
}
