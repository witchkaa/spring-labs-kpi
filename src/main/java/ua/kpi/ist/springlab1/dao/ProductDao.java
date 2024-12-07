package ua.kpi.ist.springlab1.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Product;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public Long create(Product product) {
        String sql = "INSERT INTO products (name, price, category_id) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Long.class, product.getName(), product.getPrice(), product.getCategoryId());
    }

    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, category_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getCategoryId(), product.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Product> findByCategoryId(Long categoryId) {
        String sql = "SELECT * FROM products WHERE category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), categoryId);
    }
}