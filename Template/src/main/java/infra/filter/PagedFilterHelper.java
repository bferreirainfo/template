package infra.filter;

/**
 * Classe auxiliar para paginação.
 * 
 * @author bruno
 */
public class PagedFilterHelper<X> implements PagedFilter<X> {

    private int pageSize;
    private int startPosition;
    private String orderBy;
    private boolean asc = false;

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the startPosition
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * @param startPosition the startPosition to set
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isAsc() {

        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

}