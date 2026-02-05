package ru.jabki.x6.user.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.jabki.x6.user.exception.BadRequestException;
import ru.jabki.x6.user.model.User;

@Repository
@AllArgsConstructor
public class UserRepository {
    private static final String INSERT = """
        INSERT INTO x6_user.user(email, name)
        VALUES (:email, :name)
        RETURNING *
        """;

    private static final String UPDATE = """
            UPDATE x6_user.user
            SET email = :email, name = :name, updated_at = now()
            WHERE id = :id
            RETURNING *
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM x6_user.user
            WHERE id = :id
            """;

    private static final String EXISTS_BY_ID =  """
            SELECT EXISTS (
                SELECT 1
                FROM x6_user.user
                WHERE id = :id
            )
            """;

    private final UserMapper userMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public User insert(final User user){
        return jdbcTemplate.queryForObject(INSERT, userToSql(user), userMapper);
    }

    public User update(final User user){
        return jdbcTemplate.queryForObject(UPDATE, userToSql(user), userMapper);
    }

    public User getById(final Long id) {
        try {
            return jdbcTemplate.queryForObject(GET_BY_ID, new MapSqlParameterSource("id", id), userMapper);
        } catch (Exception e) {
            throw new BadRequestException(String.format("Пользователь с id %d не найден", id));
        }
    }

    public boolean existsById(final Long id) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(EXISTS_BY_ID, new MapSqlParameterSource("id", id), Boolean.class));
    }

    private MapSqlParameterSource userToSql(final User user) {
        final MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", user.getId());
        params.addValue("name", user.getName());
        params.addValue("email", user.getEmail());
        return params;
    }
}