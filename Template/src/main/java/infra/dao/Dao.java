package infra.dao;

import infra.filter.Filter;

import java.util.Collection;

import exceptions.DaoException;
import exceptions.NonUniqueReferenceException;
import exceptions.ObjectExistsException;
import exceptions.ObjectNotFoundException;

/**
 * <p>
 * Assinatura comun a todas as classes Dao.
 * </p>
 * 
 * <p>
 * Dao representa a interface de acesso aos objetos persistidos, estando eles em bancos de dados
 * XML, ou bancos de dados OO, RDBMS (Relacional), utilizando-se ou não ferramentas de mapeamento
 * (como hibernate, EJB3 etc.).
 * </p>
 * 
 * <p>
 * As implementações desta interface devem ser obtidas através da fábrica de Daos (
 * {@link DaoFactory}).
 * </p>
 * 
 * <p>
 * Para utilizar o dao, é necessário recupera-lo apartir da fabrica de Daos, como segue o exemplo
 * abaixo:
 * 
 * <pre>
 * DaoFactory factory = DaoFactory.getInstance();
 * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
 * //...Faz-se a utilização do dao
 * </pre>
 * 
 * 
 * <p>
 * Todos os exemplos serão utilizados <code>Pessoa</code> como objeto persistido e {@link Long}
 * como a chave primaria de <code>Pessoa</code>.
 * 
 * @author bruno, marcio
 * 
 * @param <X> O tipo que este Dao persiste
 * @param <Key> O tipo da chave primaria de &lt;X&gt;.
 * 
 * @version 1.0
 */
public interface Dao<X, Key> {

    /**
     * Consulta um elemento dada sua chave primaria.<br>
     * Exemplo:<br>
     * Supondo que existe uma pessoa na base de dados cujo a chave primaria é 10
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * 
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * 
     * Pessoa pessoa = dao.consultar(10L);
     * </pre>
     * 
     * </p>
     * 
     * @param key A chave primaria do elemento do tipo Key.
     * 
     * @return O elemento consultado do tipo X, se encontrado.
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     * @throws ObjectNotFoundException Caso o elemento nao exista.
     */
    public X findByKey(Key key) throws DaoException, ObjectNotFoundException;

    /**
     * Consulta os elementos do tipo <X> que satisfaçam o criterio definido pelo filtro. A ideia
     * principal de utilizar um filtro é reduzir a complexidade do número de metodos que há nos
     * Dao, simplificando assim o desenvolvimento.
     * 
     * No caso de nenhum elemento ser encontrado que satisfaçam os critérios do filtro, uma coleção
     * vazia deve ser retornada. As implementações deste mátodo nunca devem retornar null nem
     * lançar alguma exceção neste caso.
     * 
     * Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * FiltroPessoa filtro = new FiltroPessoa();
     * filtro.setNome(&quot;Antonio&quot;);
     * Collection&lt;Pessoa&gt; pessoas = dao.consultar(filtro);
     * </pre>
     * 
     * </p>
     * 
     * @param filtro Um filtro com criterios para a consulta.
     * 
     * @return Uma colecao, possivelmente vazia, dos elementos que satisfazem os criterios do
     *         filtro.
     * 
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public Collection<X> findByFilter(Filter<? extends X> filtro) throws DaoException;

    /**
     * Consulta todos os elementos do tipo <X> que estiverem na base de dadso. .
     * 
     * No caso de não haver nenhuma entidade persistida na base, uma coleção vazia deve ser
     * retornada. As implementações deste método nunca devem retornar null nem lançar alguma
     * exceção neste caso.
     * 
     * Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * 
     * Collection&lt;Pessoa&gt; pessoas = dao.consultarTodos();
     * </pre>
     * 
     * </p>
     * 
     * @param filtro Um filtro com criterios para a consulta.
     * 
     * @return Uma colecao, possivelmente vazia, dos elementos que satisfazem os criterios do
     *         filtro.
     * 
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public Collection<X> findAll() throws DaoException;

    /**
     * Insere o novo elemento x na base de dados, onde X é o tipo do elemento.<br>
     * Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * Pessoa pessoa = new Pessoa();
     * pessoa.setNome(&quot;Antonio&quot;);
     * Long chave = dao.inserir(pessoa);
     * </pre>
     * 
     * </p>
     * 
     * @param x O elemento a ser inserido.
     * 
     * @throws ObjectExistsException Caso o elemento nao exista.
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public Key insert(X x) throws DaoException, ObjectExistsException;

    /**
     * Atualiza o elemento x na base de dados. Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * dao.atualizar(pessoa);
     * </pre>
     * 
     * </p>
     * 
     * @param x O elemento a ser inserido.
     * 
     * @throws ObjectNotFoundException Caso o elemento nao exista.
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public void update(X x) throws DaoException, ObjectNotFoundException;

    /**
     * Remove o elemento cuja chave á 'key' da base de dados. Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * dao.remover(10L);
     * </pre>
     * 
     * </p>
     * 
     * @param key A chave primária do elemento a ser removido.
     * 
     * @throws ObjectNotFoundException Caso o elemento nao exista.
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public void remove(Key key) throws DaoException, ObjectNotFoundException;

    /**
     * Remove o elemento cuja chave á 'key' da base de dados. Exemplo:<br>
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * dao.remover(10L);
     * </pre>
     * 
     * </p>
     * 
     * @param objeto o objeto a ser removido.
     * 
     * @throws ObjectNotFoundException Caso o elemento nao exista.
     * @throws DaoException Caso ocorra algum problema com a api de persistencia
     */
    public void removeElement(X objeto) throws DaoException, ObjectNotFoundException;

    /**
     * Retorna o elemento recuperado utilizando o filtro como consulta.
     * 
     * <p>
     * 
     * <pre>
     * FiltroPessoa filtro = new FitlroPessoa();
     * filtro.setCns(&quot;4746354895835&quot;);
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * Pessoa pessoa = dao.findUniqueByFilter(filtro);
     * </pre>
     * 
     * </p>
     * 
     * @param filtro o filtro utilizado na consulta
     * @return o elemento que foi filtrado ou <code>null</code> caso não seja encontrada.
     * @throws DaoException se ocorrer algum erro.
     */
    public X findUniqueByFilter(Filter<? extends X> filtro) throws NonUniqueReferenceException,
            DaoException;

    /**
     * Retorna o número de elementos utilizando o filtro como consulta.
     * 
     * <p>
     * 
     * <pre>
     * FiltroPessoa filtro = new FitlroPessoa();
     * filtro.setNome(&quot;Maria&quot;);
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * long numero_de_marias = dao.count(filtro);
     * </pre>
     * 
     * </p>
     * 
     * @param filtro
     * @return
     * @throws DaoException
     */
    public long count(Filter<? extends X> filtro) throws DaoException;

    /**
     * Retorna <code>true</code> se existe elemento utilizando o filtro como consulta.
     * <code>false</code> caso contrário.
     * 
     * <p>
     * 
     * <pre>
     * FiltroPessoa filtro = new FitlroPessoa();
     * filtro.setNome(&quot;Maria&quot;);
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * long numero_de_marias = dao.existe(filtro);
     * </pre>
     * 
     * </p>
     * 
     * @param filtro o filtro a ser utilizado na consutla.
     * @return <code>true</code> se existe elemento utilizando o filtro como consulta.
     *         <code>false</code> caso contrário.
     * @throws DaoException
     */
    public boolean exists(Filter<? extends X> filter) throws DaoException;

    /**
     * Retorna <code>true</code> se existe elemento com a chave especificada pelo parametro k.
     * <code>false</code> caso contrário.
     * 
     * <p>
     * 
     * <pre>
     * DaoFactory factory = DaoFactory.getInstance();
     * Dao&lt;Pessoa, Long&gt; dao = (Dao&lt;Pessoa, Long&gt;) factory.getDao(Pessoa.class);
     * boolean existe_pessoa_com_cns = dao.existe(&quot;74652357283&quot;);
     * </pre>
     * 
     * @param k parametro que é a chave
     * @return <code>true</code> se existe elemento com a chave especificada pelo parametro k.
     *         <code>false</code> caso contrário.
     * @throws DaoException
     */
    public boolean existsKey(Key k) throws DaoException;
}
