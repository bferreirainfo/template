package business.usuario;

import infra.dao.AbstractDaoCriteria;
import infra.manager.PersistenceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioManager extends PersistenceManager<Usuario, Long> {

    @Autowired
    private UsuarioDao usuarioDao;

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    protected AbstractDaoCriteria<Usuario, Long> getDao() {
        return usuarioDao;
    }

}
