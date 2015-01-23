package net.ausiasmarch.helper;

/**
 *
 * @author rafa
 */
public class FilterBean {

    private String filter;
    private String filterOperator;
    private String filterValue; //Equals,Like,NotLike,NotEqualTo,Less,LessOrEqual,Greater,GreaterOrEqual
    private String filterOrigin; //user, system

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFilterOperator() {
        return filterOperator;
    }

    public void setFilterOperator(String filterOperator) {
        this.filterOperator = filterOperator;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getFilterOrigin() {
        return filterOrigin;
    }

    public void setFilterOrigin(String filterOrigin) {
        this.filterOrigin = filterOrigin;
    }

}
