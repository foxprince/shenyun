package cn.anthony.boot.service;

import cn.anthony.boot.domain.Patient;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class TotalService {
	@Autowired
	private MongoOperations mongoTemplate;

//	public List<KeyGroup> keyGroup(String key) throws Exception {
//		String reduce = "function(doc, aggr){ aggr.count += 1; }";
//		Query query = Query.query(Criteria.where(key).exists(true));
//		// query.with(new Sort(new Sort.Order(Sort.Direction.ASC,
//		// "count"))).limit(1);
//		DBObject result = mongoTemplate.getCollection("patient").group(new BasicDBObject(key, 1), query.getQueryObject(),
//				new BasicDBObject("count", 0), reduce);
//		List<BasicDBObject> list = new ArrayList<BasicDBObject>(result.toMap().values());
//		list.sort((p1, p2) -> ((Double) p2.get("count")).compareTo((Double) p1.get("count")));
//		List<KeyGroup> l = new ArrayList<KeyGroup>();
//		// for (BasicDBObject e : list) {
//		// l.add(new KeyGroup(e.getString(key),e.getInt("count")));
//		// }
//		return l;
//	}

	private List<KeyGroup> aggOne(int limit, String key) throws Exception {
		Aggregation agg = newAggregation(match(Criteria.where(key).exists(true)), project("frontRecords"), unwind("frontRecords"),
				group(key).count().as("count"), project("count").and("key").previousOperation(), sort(Sort.Direction.DESC, "count"),
				limit(limit));
		AggregationResults<KeyGroup> results = mongoTemplate.aggregate(agg, Patient.class, KeyGroup.class);
		List<KeyGroup> keyCount = results.getMappedResults();
		return keyCount;
	}

	/**
	 * 
	 * @param limit
	 *            记录数
	 * @param beginTime
	 * @param endTime
	 * @param cmap
	 *            查询条件
	 * @param keys
	 *            groupby的分组列
	 * @return
	 * @throws Exception
	 */
	public List<KeyGroup> agg(int limit, Date beginTime, Date endTime, Map<String, Object> cmap, String... keys) throws Exception {
		Criteria criteria = new Criteria();
		if(!ObjectUtils.isEmpty(beginTime)){
			criteria.andOperator(Criteria.where("operations.beginTime").gte(beginTime),Criteria.where("operations.beginTime").lt(endTime));
		}
		for (int i = 0; i < keys.length; i++) {
			criteria.and(keys[i]).exists(true).ne(null);
		}
		if (cmap != null && cmap.size() > 0) {
			List<Criteria> clauseCriterias = new ArrayList<Criteria>(cmap.size());
			for (Map.Entry<String, Object> entry : cmap.entrySet()) {
				clauseCriterias.add(Criteria.where(entry.getKey()).is(entry.getValue()));
			}
			criteria = criteria.andOperator(clauseCriterias.toArray(new Criteria[cmap.size()]));
		}
		Aggregation agg = newAggregation(match(criteria), project("frontRecords","operations"), unwind("frontRecords"),unwind("operations"),
				group(keys).count().as("count"), project("count").and("key").previousOperation(), sort(Sort.Direction.DESC, "count"),
				limit(limit));
		log.info("service keys:"+ Arrays.asList(keys));
		if (keys[0].indexOf("operations") > 0) {
			/*
			 * project:列出所有本次查询的字段，包括查询条件的字段和需要搜索的字段；
			 * match:搜索条件criteria
			 * unwind：某一个字段是集合，将该字段分解成数组
			 * group：分组的字段，以及聚合相关查询
			 * 		sum：求和(同sql查询)
			 * 		count：数量(同sql查询)
			 * 		as:别名(同sql查询)
			 * 		addToSet：将符合的字段值添加到一个集合或数组中
			 * sort：排序
			 * skip&limit：分页查询
			 */
			agg = newAggregation(match(criteria), project("operations"),
					unwind("operations"), group(keys).count().as("count"),
					project("count").and("key").previousOperation(), sort(Sort.Direction.DESC, "count"), limit(limit));
		}
		AggregationResults<KeyGroup> results = mongoTemplate.aggregate(agg, Patient.class, KeyGroup.class);
		List<KeyGroup> keyCount = results.getMappedResults();
		return keyCount;
	}
}
