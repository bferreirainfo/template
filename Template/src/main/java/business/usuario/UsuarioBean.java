package business.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import exceptions.PersistenceManagerException;

@Controller
@Scope(WebApplicationContext.SCOPE_APPLICATION)
public class UsuarioBean {
    Long a;

    @Autowired
    private UsuarioManager usuarioManager;

    public void salvar() throws PersistenceManagerException {
        Usuario element = new Usuario();
        element.setNome("nome");
        a = usuarioManager.insert(element);
    }

    public Long getA() {
        return a;
    }
}
