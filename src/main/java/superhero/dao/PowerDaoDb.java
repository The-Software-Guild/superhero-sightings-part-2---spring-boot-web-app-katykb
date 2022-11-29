package superhero.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import superhero.model.Location;
import superhero.model.Power;
import superhero.model.Super;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PowerDaoDb implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power getPowerById(int powerId) {
        return null;
    }

    @Override
    public List<Power> getAllPowers() {
        final String SELECT_ALL_POWERS = "SELECT * FROM power";
        List<Power> powers = jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
        return powers;
    }

    @Override
    @Transactional
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power(powerDescription)"
                + "VALUES(?)";
        jdbc.update(INSERT_POWER,
                power.getPowerDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setPowerId(newId);
        return power;
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET powerDescription = ?";
        jdbc.update(UPDATE_POWER,
                power.getPowerDescription(),
                power.getPowerId());
    }

    @Override
    public void deletePowerById(int powerId) {
        final String DELETE_POWER = "DELETE POWER from power WHERE powerId = ?";
        jdbc.update(DELETE_POWER, powerId);

    }

    @Override
    public List<Power> getPowerForSuper(Super superHero) {
        return null;
    }

    public static final class PowerMapper implements RowMapper<Power> {
        @Override
        public Power mapRow (ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setPowerId(rs.getInt("powerId"));
            power.setPowerDescription(rs.getString("powerDescription"));

            return power;
        }
    }
}