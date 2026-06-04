package com.example.demo.dao;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Person getPerson() {
        String sql = "SELECT * FROM person WHERE ID = 1;";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Person p = new Person();
            p.setFirstName(rs.getString("FirstName"));
            p.setLastName(rs.getString("LastName"));
            p.setLocation(rs.getString("Location"));
            p.setEmail(rs.getString("Email"));
            p.setPhone(rs.getString("Phone"));
            p.setShortIntroduction(rs.getString("ShortIntroduction"));
            p.setLongIntroduction(rs.getString("LongIntroduction"));

            return p;
        });
    }
}
          