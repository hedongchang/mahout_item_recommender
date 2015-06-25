package org.hedon.mymahout.dao;

import java.util.List;
import java.util.TreeMap;

import org.hedon.mymahout.recommendation.Pair;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoDAO {
	public static void insertToMongo(TreeMap<String, List<Pair<String, Float>>> output, String website) {
		@SuppressWarnings({ "deprecation", "resource" })
		DB db = (new MongoClient("localhost", 27017)).getDB("inv_intern_result");
		DBCollection dbCollection = db.getCollection(website);
		for (String pre: output.keySet()) {
			String json = "{\"pre\":{\"vin\":\"" + pre + "\"},\"vehicles\":[";
			int count = 0;
			for (Pair<String, Float> post: output.get(pre)) {
				count++;
				json = json + "{\"vehicle\":{\"vin\":\"" + post.getFirst() + "\", \"value\":\"" + post.getSecond() + "\"}}";
				if (count != output.get(pre).size()) {
					json = json + ",";
				}
			}
			json = json + "]}";
			DBObject basicDBObject = (DBObject) JSON.parse(json);
			dbCollection.insert(basicDBObject);
		}
		/*DBCursor cursor = dbCollection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}*/
		System.out.println("finish uploading");
	}
}
