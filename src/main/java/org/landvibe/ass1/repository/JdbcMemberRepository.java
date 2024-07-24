package org.landvibe.ass1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.landvibe.ass1.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public class MemberRowMapper implements RowMapper<Member> {

        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member(rs.getInt("id"), rs.getString("name"));
            return member;
        }
    }

    @Override
    public Member findById(int id) {
        String query = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(query, new MemberRowMapper(), id);
    }
}
