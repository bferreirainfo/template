package infra.filter;

/**
 * Representa um filtro que possui atributos de paginação.
 * 
 * @author mdms
 * 
 * @param <X> a entidade do tipo que esta sendo fitlrado.
 */
public interface PagedFilter<X> extends Filter<X> {
    /**
     * Retorna a posição inicial que será retornada
     * 
     * @return o valor inicial
     */
    public int getStartPosition();

    /**
     * Seta a posicão inicial da página
     * 
     * @param startPositition a posição inicial da página
     */
    public void setStartPosition(int startPositition);

    /**
     * Retorna o tamanho da página
     * 
     * @return o tamanho da página
     */
    public int getPageSize();

    /**
     * Seta o tamanho da página
     * 
     * @param pageSize o tamanho da página.
     */
    public void setPageSize(int pageSize);

    public void setOrderBy(String orderBy);

    public String getOrderBy();

    public boolean isAsc();

    public void setAsc(boolean asc);

}
