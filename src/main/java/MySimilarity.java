import org.apache.lucene.search.similarities.BasicStats;
import org.apache.lucene.search.similarities.SimilarityBase;

public class MySimilarity extends SimilarityBase {


    protected float score(BasicStats basicStats, float freq, float docLen) {


        float wordfrq_all = (basicStats.getTotalTermFreq()+1F) / (basicStats.getNumberOfFieldTokens()+1F);

        float docfreq = basicStats.getDocFreq();
//        包含个词的文档的频数
        float wordfrq = freq/docLen;
//        词在这个文档频数/文档长度
        float boost = basicStats.getBoost();

        return boost*wordfrq/docfreq/wordfrq_all;
    }

    public String toString() {
        return null;
    }
}
