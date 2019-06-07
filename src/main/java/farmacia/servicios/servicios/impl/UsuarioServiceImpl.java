package farmacia.servicios.servicios.impl;

import farmacia.servicios.dao.UsuarioDao;
import farmacia.servicios.daomain.Usuario;
import farmacia.servicios.response.ResponseVerificarUsuario;
import farmacia.servicios.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioDao usuarioDao;
    @Override
    public ResponseVerificarUsuario verificarUsuario(Usuario usuario) {
        ResponseVerificarUsuario responseService = new ResponseVerificarUsuario();
        Usuario usuarioResponse;
        try{
        usuarioResponse = usuarioDao.obtenerUsuario(usuario);
        }catch (NullPointerException e){
            usuarioResponse=null;
        }

        if(usuarioResponse!=null) {
            if (usuarioResponse.getTipoUsuario() == 0) {
                responseService.setAdmin(true);
            } else {
                responseService.setAdmin(false);
            }
            responseService.setUsuarioResponse(usuarioResponse);
            responseService.setExiste(true);
        }else{
            responseService.setUsuarioResponse(null);
            responseService.setAdmin(null);
            responseService.setExiste(false);
        }
        return responseService;
    }

    @Override
    public Boolean agregarCliente(Usuario usuario) {
        Usuario usuario1 = usuarioDao.verificarUsuario(usuario);
        if(usuario1==null){
            usuario.setTipoUsuario(1);
            usuario.setNombres(usuario.getNombres().toUpperCase());
            usuario.setApellidos(usuario.getApellidos().toUpperCase());
            usuarioDao.registrarUsuario(usuario);
            return true;
        }else{
            return false;
        }


    }
}
