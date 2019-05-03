public class SQuery {
    protected String id;
    protected String intent_seg;
    protected String intent;
    protected String query;



    public  SQuery() {
        id = "";
        intent_seg = "";
        intent = "";
        query = "";

    }
    public void setId(String id) {
        this.id = id;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setIntent_seg(String intent_seg) {
        this.intent_seg = intent_seg;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getId() {
        return id;
    }

    public String getIntent() {
        return intent;
    }

    public String getIntent_seg() {
        return intent_seg;
    }

    public String getQuery() {
        return query;
    }
}

