package org.hedon.mymahout.recommendation;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.apache.mahout.cf.taste.common.TasteException;
import org.hedon.mymahout.dao.FactDAO;
import org.hedon.mymahout.dao.MongoDAO;

public class App {
	public static void main(String[] args) {
		try {
			System.out.println("start computing recommendation");
			TreeMap<String, List<Pair<String, Float>>> result = ComputeRecommend.recommend(5);
			System.out.println("finish computing recommendation");
			System.out.println("start storing webid");
			TreeMap<String, String> vinWebId = FactDAO.selectVinWebId(result.keySet());
			System.out.println("finish storing webiid");
			for (String vin: vinWebId.keySet()) {
				System.out.println(vinWebId.get(vin));
			}
			MongoDAO.insertToMongo(result, "Lexus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
