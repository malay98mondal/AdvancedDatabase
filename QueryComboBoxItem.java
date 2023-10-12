package swing;

public class QueryComboBoxItem {
    private String queryName;
    private String query;

    public QueryComboBoxItem(String queryName, String query) {
        this.queryName = queryName;
        this.query = query;
    }

    public String getQueryName() {
        return queryName;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public String toString() {
        return queryName;
    }
}


