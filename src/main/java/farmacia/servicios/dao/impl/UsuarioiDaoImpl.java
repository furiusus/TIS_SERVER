package farmacia.servicios.dao.impl;

import com.sun.org.apache.xpath.internal.operations.Bool;
import farmacia.servicios.dao.SimpleJdbc;
import farmacia.servicios.dao.UsuarioDao;
import farmacia.servicios.daomain.Usuario;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsuarioiDaoImpl extends SimpleJdbc implements UsuarioDao {
    @Override
    public Usuario obtenerUsuario(Usuario usuario) {
        Usuario usuarioResponse ;
        try {
            String sql = "select ID_USUARIO, DNI, NOMBRES, APELLIDOS, CORREO, CLAVE, TIPO_USUARIO,TELEFONO from usuario where CORREO = ? and CLAVE = ?";

            usuarioResponse = getJdbcTemplate().queryForObject(sql, new Object[]{usuario.getCorreo(),usuario.getClave()},
                    new RowMapper<Usuario>() {
                        @Override
                        public Usuario mapRow(ResultSet resultSet, int i) throws SQLException {
                            Usuario obj = new Usuario();
                            obj.setClave(resultSet.getString("CLAVE"));
                            obj.setTipoUsuario(resultSet.getInt("TIPO_USUARIO"));
                            obj.setCorreo(resultSet.getString("CORREO"));
                            obj.setNombres(resultSet.getString("NOMBRES"));
                            obj.setApellidos(resultSet.getString("APELLIDOS"));
                            obj.setIdUsuario(resultSet.getInt("ID_USUARIO"));
                            obj.setDni(resultSet.getString("dni"));
                            obj.setTelefono(resultSet.getString("TELEFONO"));
                            return obj;
                        }
                    });
        }catch (NullPointerException | EmptyResultDataAccessException e){
            usuarioResponse = null;
        }
        return usuarioResponse;
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        try{
            String sql ="insert into usuario(ID_USUARIO, DNI, NOMBRES, APELLIDOS, CORREO, CLAVE, TIPO_USUARIO,TELEFONO)\n" +
                    "    values(?,?,?,?,?,?,?,?)";
            int id=getJdbcTemplate().queryForObject("select count(*) from usuario",Integer.class)+1;
            getJdbcTemplate().update(sql,new Object[]{id,usuario.getDni(),usuario.getNombres(),usuario.getApellidos(),
            usuario.getCorreo(),usuario.getClave(),usuario.getTipoUsuario(),usuario.getTelefono()});

        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    public Usuario verificarUsuario(Usuario usuario) {
        Usuario usuarioResponse ;
        try {
            String sql = "select ID_USUARIO, DNI, NOMBRES, APELLIDOS, CORREO, CLAVE, TIPO_USUARIO from usuario where CORREO = ? or DNI = ?";
            usuarioResponse = getJdbcTemplate().queryForObject(sql, new Object[]{usuario.getCorreo(),usuario.getDni()},
                    new RowMapper<Usuario>() {
                        @Override
                        public Usuario mapRow(ResultSet resultSet, int i) throws SQLException {
                            Usuario obj = new Usuario();
                            obj.setClave(resultSet.getString("CLAVE"));
                            obj.setTipoUsuario(resultSet.getInt("TIPO_USUARIO"));
                            obj.setCorreo(resultSet.getString("CORREO"));
                            obj.setNombres(resultSet.getString("NOMBRES"));
                            obj.setApellidos(resultSet.getString("APELLIDOS"));
                            obj.setIdUsuario(resultSet.getInt("ID_USUARIO"));
                            obj.setDni(resultSet.getString("dni"));
                            return obj;
                        }
                    });
        }catch (NullPointerException | EmptyResultDataAccessException e){
            usuarioResponse = null;
        }
        return usuarioResponse;
    }
}
