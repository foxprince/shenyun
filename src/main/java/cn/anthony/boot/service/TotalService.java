package cn.anthony.boot.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import cn.anthony.boot.domain.Patient;

@Service
public class TotalService {
    @Autowired
    private MongoOperations mongoTemplate;

    public List<KeyGroup> keyGroup(String key) throws Exception {
	String reduce = "function(doc, aggr){ aggr.count += 1; }";
	Query query = Query.query(Criteria.where(key).exists(true));
	// query.with(new Sort(new Sort.Order(Sort.Direction.ASC,
	// "count"))).limit(1);
	DBObject result = mongoTemplate.getCollection("patient").group(new BasicDBObject(key, 1),
		query.getQueryObject(), new BasicDBObject("count", 0), reduce);
	List<BasicDBObject> list = new ArrayList<BasicDBObject>(result.toMap().values());
	list.sort((p1, p2) -> ((Double) p2.get("count")).compareTo((Double) p1.get("count")));
	List<KeyGroup> l = new ArrayList<KeyGroup>();
	// for (BasicDBObject e : list) {
	// l.add(new KeyGroup(e.getString(key),e.getInt("count")));
	// }
	return l;
    }

    private List<KeyGroup> aggOne(int limit, String key) throws Exception {
	Aggregation agg = newAggregation(match(Criteria.where(key).exists(true)), project("frontRecords"),
		unwind("frontRecords"), group(key).count().as("count"), project("count").and("key").previousOperation(),
		sort(Sort.Direction.DESC, "count"), limit(limit));

	AggregationResults<KeyGroup> results = mongoTemplate.aggregate(agg, Patient.class, KeyGroup.class);
	List<KeyGroup> keyCount = results.getMappedResults();
	return keyCount;
    }

    public List<KeyGroup> agg(int limit, String... keys) throws Exception {
	Criteria criteria = new Criteria();
	List<Criteria> docCriterias = new ArrayList<Criteria>(keys.length);
	for(int i=0;i<keys.length;i++) {
	    docCriterias.add(Criteria.where(keys[i]).exists(true).ne(null));
	}
	criteria = criteria.andOperator(docCriterias.toArray(new Criteria[keys.length]));
	Aggregation agg = newAggregation(match(criteria),project("frontRecords"), unwind("frontRecords"),
		group(keys).count().as("count"), project("count").and("key").previousOperation(),
		sort(Sort.Direction.DESC, "count"), limit(limit));
	if(keys[0].indexOf("operationDetails")>0)
	    agg = newAggregation(match(criteria),project("frontRecords","frontRecords.operationDetails"), unwind("frontRecords"),
			unwind("frontRecords.operationDetails"),
			group(keys).count().as("count"), project("count").and("key").previousOperation(),
			sort(Sort.Direction.DESC, "count"), limit(limit));
	AggregationResults<KeyGroup> results = mongoTemplate.aggregate(agg, Patient.class, KeyGroup.class);
	List<KeyGroup> keyCount = results.getMappedResults();
//	for (KeyGroup e : keyCount) {
//	    System.out.println(e.getKey() + "," + e.toString());
//	}
	return keyCount;
    }
}
