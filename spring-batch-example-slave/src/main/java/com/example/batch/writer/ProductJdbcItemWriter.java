package com.example.batch.writer;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import com.example.model.Product;

public class ProductJdbcItemWriter implements ItemWriter<Product> {
	public static final String INSERT_PRODUCT = "insert into products " + "(id,name,description,price,metadata) values(:id,:name,:description,:price,:metadata)";
	public static final String UPDATE_PRODUCT = "update products set " + "name=:name, description=:description, price=:price, metadata=:metadata where id=:id";
	
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private ItemSqlParameterSourceProvider<Product> itemSqlParameterSourceProvider;

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
	
	@Required
	public void setItemSqlParameterSourceProvider(ItemSqlParameterSourceProvider<Product> itemSqlParameterSourceProvider) {
		this.itemSqlParameterSourceProvider = itemSqlParameterSourceProvider;
	}

	public void write(List<? extends Product> items) throws Exception {
		for (Product item : items) {
			item.setMetadata("Thread: " + Thread.currentThread().getName() + "; Machine: " + java.net.InetAddress.getLocalHost().getHostName());
			System.out.println(item);
			SqlParameterSource args = itemSqlParameterSourceProvider.createSqlParameterSource(item);
			int updated = simpleJdbcTemplate.update(UPDATE_PRODUCT, args);
			if (updated == 0) {
				simpleJdbcTemplate.update(INSERT_PRODUCT, args);
			}
		}
	}
}