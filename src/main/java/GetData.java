import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import net.sf.json.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GetData {
    private static GetData cur;

    private static String filename;
    private static Analyzer analyzer ;
    private static Directory directory = new RAMDirectory();
    private static Data myData;
    public static GetData getInstance(){
        if (cur == null){
            cur = new GetData();
            myData = new Data();
        }

        return cur;
    }


    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static Data jason2Data(String jason){
        String content = "";
        String id = "";
        String title = "";


        JSONObject jsonObject = JSONObject.fromObject(jason);
        title = jsonObject.getString("title");
        id = jsonObject.getString("uid");
        content = jsonObject.getString("content");


        myData = new Data();
        myData.setContent(content);
        myData.setId(id);
        myData.setTitle(title);

        return myData;
    }

    public static void setFilename(String filename) {
        GetData.filename = filename;
    }


    public static Directory getDirectory(Similarity mySimilarity) throws IOException {
        analyzer = new ComplexAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setSimilarity(mySimilarity);
        IndexWriter iwriter = new IndexWriter(directory, config);

        File file = new File(filename);
        BufferedReader reader = null;

        reader = new BufferedReader(new FileReader(file));
        String tempString = null;



        while ((tempString = reader.readLine()) != null) {

            iwriter.addDocument(jason2Data(tempString).toDoc());
        }
        reader.close();
        iwriter.close();
        return directory;

    }

}




