package farmacia.servicios.servicios;


import farmacia.servicios.daomain.Usuario;
import farmacia.servicios.response.ResponseVerificarUsuario;

public interface UsuarioService {
    public ResponseVerificarUsuario verificarUsuario(Usuario usuario);
    Boolean agregarCliente(Usuario usuario);
}
