package com.ra.model.dao;

import com.ra.model.entity.Image;
import com.ra.model.service.ProductService;
import com.ra.utils.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImagesImpl implements ImagesDao{
    @Autowired
    ProductService productService;

    @Override
    public List<Image> findAll() {
        Connection connection= ConnectionDatabase.openConnection();
        List<Image>imagesList=new ArrayList<>();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_ALL_IMAGE");
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                Image images=new Image();
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
                imagesList.add(images);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return imagesList;
    }

    @Override
    public boolean save(Image images, int ProductId) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_IMAGE(?,?)");
            callableStatement.setString(1,images.getImageUrl());
            callableStatement.setInt(2,ProductId);
            int check=callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Image images) {
        return false;
    }

    @Override
    public Image findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Image images=new Image();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_BY_ID_IMAGE(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                images.setId(resultSet.getInt("id"));
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return images;
    }

    @Override
    public void delete(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_IMAGE(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void deleteForeign(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_FOREIGN_IMAGE(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Image> findByProductId(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
       List<Image> imagesList=new ArrayList<>();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_BY_PRODUCT_ID_IMAGE(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                Image images=new Image();
                images.setId(resultSet.getInt("id"));
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
                imagesList.add(images);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return imagesList;
    }
}
