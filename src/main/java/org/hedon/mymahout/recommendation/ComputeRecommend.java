package org.hedon.mymahout.recommendation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ComputeRecommend {	
	
	private static final String INPUT_FILE = "datafile/finaloutput.csv";
	private static final String PARSED_FILE = "parsed.csv";
	
    public static TreeMap<String, List<Pair<String, Float>>> recommend(int recommend_num)
    		throws IOException, TasteException {
        String file = INPUT_FILE;
        TreeMap<Long, String> idVisitor = new TreeMap<Long, String>();
        TreeMap<Long, String> idVin = new TreeMap<Long, String>();
    	Parser.parse(file, idVisitor, idVin);
        DataModel model = new FileDataModel(new File(PARSED_FILE));
        ItemSimilarity itemsim = new LogLikelihoodSimilarity(model);
        // NearestNItemNeighborhood neighbor = new NearestNItemNeighborhood(NEIGHBORHOOD_NUM, user, model);
        GenericItemBasedRecommender r = new GenericItemBasedRecommender(model, itemsim);
        LongPrimitiveIterator iter = model.getItemIDs();
        TreeMap<String, List<Pair<String, Float>>> result = new TreeMap<String, List<Pair<String, Float>>>();
		//PrintWriter writer = new PrintWriter(OUTPUT_FILE, "UTF-8");
		//int nonZeroCount = 0;
        while (iter.hasNext()) {
            long itemid = iter.nextLong();
            List<RecommendedItem> list = r.mostSimilarItems(itemid, recommend_num);
            /*if (!list.isEmpty()) {
            	nonZeroCount++;
            }*/
            String vinString = idVin.get(itemid);
            //writer.print(vinString + " ");
            result.put(vinString, new ArrayList<Pair<String, Float>>());
            for (RecommendedItem ritem : list) {
            	String recVinString = idVin.get(ritem.getItemID());
                //writer.print(recVinString + " " + ritem.getValue() + " ");
                result.get(idVin.get(itemid)).add(new Pair<String, Float>(recVinString, ritem.getValue()));
            }
            //writer.println();
        }
        /*System.out.println("percentage of items with more than one recommendations: " 
        		+ nonZeroCount * 100.0 / result.size() + "%");*/
        //writer.close();
        return result;
    }
}
