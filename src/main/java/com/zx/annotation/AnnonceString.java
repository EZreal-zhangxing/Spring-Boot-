package com.zx.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/**
 * @Auother zhangxing
 * @Date 2019-05-23 17:59
 * @note
 */
public class AnnonceString implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        annotationMetadata.getAnnotationTypes().forEach(System.out::println);
        return new String[0];
    }
}
