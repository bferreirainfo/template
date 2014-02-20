package infra.dao;

import infra.filter.Filter;
import infra.filter.PagedFilter;

import java.util.Collection;

import net.jodah.typetools.TypeResolver;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import exceptions.DaoException;

@SuppressWarnings("unchecked")
public abstract class AbstractDaoCriteria<T, K> extends AbstractDao<T, K> {
    @Autowired
    private SessionFactory sessionFactory;

    public AbstractDaoCriteria() {
        //no método getEntityKlass() resultado terá cache;
        TypeResolver.enableCache();
    }

    /**
     * Politicas de CASE em comparacoes de strings.
     * 
     * SENSITIVE: A consulta é case sensitive, ou seja, diferencas entre maiusculas e minusculas
     * serao levadas em conta nas comparacoes.
     * 
     * INSENSITIVE: A consulta é case insensitive, ou seja, diferencas entre maiusculas e
     * minusculas não serao levadas em conta nas comparacoes.
     * 
     * ALWAYS_UPPER: (para compatibiliade com codigo legado) A consulta sempre transformara o valor
     * informado para maiusculas antes de comparar com o valor do banco.
     * 
     * @author bruno
     */
    public static enum CASE {
        SENSITIVE, INSENSITIVE, ALWAYS_UPPER
    };

    /**
     * Politicas de comparacao com LIKE.
     * 
     * START: O sinal de '%' e' colocado apenas no incio da string. END: O sinal de '%' e' colocado
     * apenas no final da string. START_END: O sinal de '%' e' colocado tanto no inicio quanto
     * EVERYWHERE: O sinal de '%' e' substituido por todos os espacos em branco somente após a
     * primeira palavra. no final da string.
     * 
     * @author bruno
     */
    public static enum LIKE {
        START, END, START_END, EVERYWHERE
    };

    /**
     * Realiza uma consulta por {@link Filter}, usando um {@link Criteria} preenchido pelo método
     * abstrato {@link #fillCriteriaFilter(Criteria, Filter)}.
     * 
     * @param filtro O filtro para restringir os dados da consulta.
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<T> findByFilter(Filter<? extends T> filtro) throws DaoException {

        Criteria criteria = createCriteria();

        fillCriteriaFilter(criteria, filtro);
        updatePageCriteria(criteria, filtro);
        orderByFilter(criteria, filtro);

        return criteria.list();
    }

    /**
     * Disponivel para o usuario sobrescrever de acordo com a aplicação, a forma que deseja que
     * seja feita a ordenação.
     * 
     * @param filtro O filtro para restringir os dados da consulta.
     * 
     */
    public void orderByFilter(Criteria criteria, Filter<? extends T> filtro) {
    }

    /**
     * <p>
     * O resultado do filtro é divido em páginas de tamanhos definido, esse método tem o objetivo
     * de atualiza o Criteria com as páginas que ele vai trazer.
     * </p>
     * 
     * @param criteria O Criteria onde sera' adicionado novo criterio de busca.
     * @param filtro O filtro para restringir os dados da consulta.
     */
    @SuppressWarnings("unchecked")
    protected void updatePageCriteria(Criteria criteria, Filter<? extends T> filtro) {

        if (filtro instanceof PagedFilter) {
            PagedFilter<T> filtroPaginado = (PagedFilter<T>) filtro;

            int pageSize = filtroPaginado.getPageSize();

            if (pageSize != 0) {
                criteria.setFirstResult(filtroPaginado.getStartPosition());
                criteria.setMaxResults(pageSize);
            }
        }
    }

    /**
     * Retorna o resultado, a partir do filtro que restringe os dados da consulta
     * 
     * @param criteria O Criteria onde sera' adicionado novo criterio de busca.
     * @param filtro O filtro para restringir os dados da consulta.
     */
    @SuppressWarnings("unchecked")
    protected Collection<T> findByFilter(Filter<? extends T> filtro, Session sessao)
            throws DaoException {

        Criteria criteria = createCriteria(sessao);
        fillCriteriaFilter(criteria, filtro);

        return criteria.list();
    }

    /**
     * Para uma sessao informada é criado uma intancia da classe Criteria de uma determinada classe
     * ou superclasse.
     * 
     * @param sessao Oferece um servico de persistencia, ou seja, criar, ler e apagar instancias de
     *        uma entidade
     * @return Criteria
     */
    protected Criteria createCriteria(Session sessao) {

        Criteria criteria = sessao.createCriteria(getEntityKlass());

        return criteria;
    }

    /**
     * Para uma sessao já existente é criado uma intancia da classe Criteria de uma determinada
     * classe ou superclasse.
     * 
     * @param sessao Oferece um servico de persistencia, ou seja, criar, ler e apagar instancias de
     *        uma entidade
     * @return Criteria
     */
    protected Criteria createCriteria() {

        Session sessao = getSession();
        Criteria criteria = sessao.createCriteria(getEntityKlass());

        return criteria;
    }

    /**
     * Metodo que obriga a fornecer a implementacao para preencher os criterio para restringir a
     * consulta
     * 
     * @param criteria O Criteria onde sera' adicionado novo criterio de busca.
     * @param filtro
     */
    protected abstract void fillCriteriaFilter(Criteria criteria, Filter<? extends T> filtro);

    /**
     * Retorna a quantidade de elementoa que foram restringidos pelo filtro
     * 
     * @param fitro O filtro para restringir os dados da consulta
     * @return long
     * 
     */
    public long count(Filter<? extends T> filtro) {

        Criteria criteria = createCriteria();

        fillCriteriaFilter(criteria, filtro);
        criteria.setProjection(Projections.rowCount());

        long value = ((Number) criteria.uniqueResult()).longValue();

        return value;
    }

    public long countAll() {

        Criteria criteria = createCriteria();

        criteria.setProjection(Projections.rowCount());

        long value = ((Number) criteria.uniqueResult()).longValue();

        return value;
    }

    public Object executeScalar(Filter<? extends T> filtro) throws DaoException {

        Criteria criteria = createCriteria();

        fillCriteriaFilter(criteria, filtro);

        return criteria.uniqueResult();
    }

    /**
     * Retorna verdadeiro se existe resultado(s) que foram restringidos através do filtro
     * informado, ou falso se nao houve resultado.
     * 
     * @param fitro O filtro para restringir os dados da consulta
     * @return boolean
     */
    public boolean exists(Filter<? extends T> filtro) {
        return count(filtro) > 0;
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Class<T> getEntityKlass() {
        return (Class<T>) TypeResolver.resolveRawArguments(AbstractDaoCriteria.class,
                this.getClass())[0];
    }
}
