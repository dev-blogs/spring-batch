package com.devblogs.batch.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import com.devblogs.model.Product;

public class ProductJdbcItemWriter implements ItemWriter<Product> {
	public static final String INSERT_PRODUCT = "insert into products (id,name,description,price) values(:id,:name,:description,:price)";
	public static final String UPDATE_PRODUCT = "update products set name=:name, description=:description, price=:price where id=:id";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public void write(List<? extends Product> items) throws Exception {
		for (Product item : items) {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("id", item.getId());
			parameters.put("name", item.getName());
			parameters.put("description", item.getDescription());
			parameters.put("price", item.getPrice());
			SqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameters);
			
			int updated = namedParameterJdbcTemplate.update(UPDATE_PRODUCT, sqlParameterSource);
			if (updated == 0) {
				namedParameterJdbcTemplate.update(INSERT_PRODUCT, sqlParameterSource);
			}
		}
	}
}