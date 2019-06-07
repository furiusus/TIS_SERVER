package farmacia.servicios.response;

import farmacia.servicios.daomain.Usuario;

public class ResponseVerificarUsuario {
    private Usuario usuarioResponse;
    private Boolean existe;
    private Boolean admin;

    public Usuario getUsuarioResponse() {
        return usuarioResponse;
    }

    public void setUsuarioResponse(Usuario usuarioResponse) {
        this.usuarioResponse = usuarioResponse;
    }

    public Boolean getExiste() {
        return existe;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
