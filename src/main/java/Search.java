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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search {


    public static void demo(MultiFieldQueryParser parser, IndexSearcher isearcher )throws IOException, ParseException
    {

        Query query;
        String str = "";
        while (str!= null)
        {
            str = JOptionPane.showInputDialog("Test query:");
            System.out.println(">:"+str);

            if (str!= null)
            {
                query = parser.parse(str);

                ScoreDoc[] hits = isearcher.search(query,10).scoreDocs;
                for (int i = 0; i < hits.length; i++) {
                    Document hitDoc = isearcher.doc(hits[i].doc);
                    System.out.println(hits[i].score+' '+hitDoc.get("title"));
                }
            }
            System.out.println(">>>>");
        }

    }

    public static void main(String[] args) throws IOException, ParseException {
        GetData.getInstance();
        GetData.setFilename("Data/ntcir14_test_doc.json");
        GetQuery.getInstance();

        System.out.println(System.getProperty("user.dir"));
        GetQuery.setFilename("Data/ntcir14_test_query.json");
        ArrayList<SQuery> qList = GetQuery.getqList();
        BM25Similarity mySimilarity = new BM25Similarity();

//        MySimilarity mySimilarity = new MySimilarity();
        Directory directory = GetData.getDirectory(mySimilarity);

        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        isearcher.setSimilarity(mySimilarity);

        String[] fields = {
                 "title"
                ,"content"
                };
        Map<String,Float> boosts = new HashMap<String, Float>();

        boosts.put("title",0.30f);
        boosts.put("content",0.010f);

        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,GetData.getAnalyzer(),boosts);

//        demo(parser,isearcher);
        Query query;
        for (int i = 0; i < qList.size(); i++) {
            query = parser.parse(qList.get(i).query);
            ScoreDoc[] hits = isearcher.search(query,200).scoreDocs;
            for (int j = 0; j < hits.length; j++) {
                Document hitDoc = isearcher.doc(hits[j].doc);

                String rank = "";

                if (j < 10) {
                    rank = "00" + String.valueOf(j + 1);
                }
                else
                if (j>10 && j<100) {
                    rank = "0" + String.valueOf(j+1);
                }else
                {
                    rank = String.valueOf(j+1);
                }


                System.out.println(qList.get(i).id+" Q0 "+hitDoc.get("id")+" "+rank+" "+hits[j].score+" BM25");

//                System.out.println(hits[j].score+' '+hitDoc.get("title")+"   "+hitDoc.get("id"));
//                System.out.println(String.valueOf(i)+" - "+String.valueOf(j)+' '+qList.get(i).id+" Q0 "+hitDoc.get("id")+" "+String.valueOf(j)+" "+hits[j].score+" BM25");
            }
        }
        ireader.close();
        directory.close();

    }
}


