import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;


public class Data {
    protected String content;
    protected String id;
    protected String title;

    public Document toDoc(){
        Document doc = new Document();
        doc.add(new Field("content",content, TextField.TYPE_STORED));
        doc.add(new Field("id",id, TextField.TYPE_STORED));
        doc.add(new Field("title",title, TextField.TYPE_STORED));

        return doc;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void show() {
        System.out.println(">");
        System.out.println(content);
        System.out.println(id);
        System.out.println(title);

    }
}
