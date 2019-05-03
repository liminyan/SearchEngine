import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Search {
    public static void main(String[] args) throws IOException, ParseException {
        GetData.getInstance();
        GetData.setFilename("test.json");
        BM25Similarity mySimilarity = new BM25Similarity();
        Directory directory = GetData.getDirectory("test.json",mySimilarity);
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(mySimilarity);

        String[] fields = {"title"
                ,"content"
                };
        Map<String,Float> boosts = new HashMap<String, Float>();

        boosts.put("title",0.350f);
        boosts.put("content",0.0110f);

        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,GetData.getAnalyzer(),boosts);
        Query query;

        String str = "";
        while (str!= null)
        {
            str = JOptionPane.showInputDialog("What you query?");
            System.out.println(">:"+str);
            if (str!= null)
            {
                query = parser.parse(str);
                ScoreDoc[] hits = isearcher.search(query,10).scoreDocs;
                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    System.out.println(hits[i].score+' '+hitDoc.get("title")+"   "+hitDoc.get("id"));
                }
            }
            System.out.println(">>>>");
        }

        ireader.close();
        directory.close();

    }
}


