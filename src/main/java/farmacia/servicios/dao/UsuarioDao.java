package farmacia.servicios.dao;

import farmacia.servicios.daomain.Usuario;

public interface UsuarioDao {
    public Usuario obtenerUsuario (Usuario usuario);
    void registrarUsuario(Usuario usuario);
    public Usuario verificarUsuario (Usuario usuario);
}
