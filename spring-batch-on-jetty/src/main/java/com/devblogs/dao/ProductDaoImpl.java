package com.devblogs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.devblogs.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	private static final String SQL_INSERT = 
			"INSERT INTO products (id, name, description, price) VALUES (:id, :name, :description, :price)";
	
	private static final String SQL_SELECT = 
			"SELECT id, name, description, price FROM products WHERE name = :name";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public int saveProduct(Product product) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", product.getId());
		params.addValue("name", product.getName());
		params.addValue("description", product.getDescription());
		params.addValue("price", product.getPrice());
		return jdbcTemplate.update(SQL_INSERT, params);
	}

	public Product findProductByName(String name) {
		List<Product> products = jdbcTemplate.query(SQL_SELECT, Collections.singletonMap("name", name), new RowMapper<Product>() {
			@Override
			public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Product(rs.getString("id"), rs.getString("name"), rs.getString("description"), rs.getBigDecimal("price"));
			}
			
		});
		
		Product product = null;
		if (products != null && products.size() > 0) {
			product = products.get(0);
		}
		return product;
	}

}