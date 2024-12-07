package ua.kpi.ist.springlab1.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Category;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;


    public Long create(Category category) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, category.getName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    public List<Category> searchCategories(String name) {
        String sql = "SELECT * FROM categories WHERE name LIKE ?";

        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, categoryRowMapper());
    }

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getLong("id"));
            category.setName(rs.getString("name"));
            return category;
        };
    }
    public Optional<Category> findById(Long id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try {
            Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), id);
            return Optional.of(category);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Category> findAll() {
        String sql = "SELECT * FROM categories";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }

    public void update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, category.getName(), category.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM categories WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    public void linkProductToCategory(Long categoryId, int productId) {
        String sql = "INSERT INTO category_product (category_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, categoryId, productId);
    }
}