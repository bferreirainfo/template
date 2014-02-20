package business.usuario;

import infra.dao.AbstractDaoCriteria;
import infra.filter.Filter;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDao extends AbstractDaoCriteria<Usuario, Long> {

    @Override
    protected void fillCriteriaFilter(Criteria criteria, Filter<? extends Usuario> filtro) {
    }

}
