package com.example.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.example.model.Product;

public class ProductRowMapper implements RowMapper<Product> {
	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Product product = new Product();
	    product.setId(rs.getString("id"));
	    product.setName(rs.getString("name"));
	    product.setDescription(rs.getString("description"));
	    product.setPrice(rs.getBigDecimal("price"));
	    return product;
	}
}