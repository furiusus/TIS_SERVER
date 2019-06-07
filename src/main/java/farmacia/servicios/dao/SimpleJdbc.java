package farmacia.servicios.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SimpleJdbc {
    @Autowired
    @Qualifier(value="jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value="dataSource")
    private DataSource dataSource;
    public SimpleJdbc(DataSource dataSource,JdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcTemplate.setDataSource(dataSource);
    }

    public SimpleJdbc() {
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
