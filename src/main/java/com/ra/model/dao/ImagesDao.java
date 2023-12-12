package com.ra.model.dao;

import com.ra.model.entity.Images;
import com.ra.model.entity.Products;

import java.util.List;

public interface ImagesDao {
    List<Images> findAll();
    boolean save(Images images, int ProductId);
    boolean update(Images images);
    Images findById(Integer id);

    void delete(Integer id);
    void deleteForeign(Integer id);

    List<Images> findByProductId(Integer id);
}