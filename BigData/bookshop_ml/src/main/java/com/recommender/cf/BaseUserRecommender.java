package com.recommender.cf;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 * 基于用户的推荐
 * 
 * @author Lenovo
 *
 */
public class BaseUserRecommender {
	public static void main(String[] args) throws Exception {
		// 准备数据 这里是电影评分数据
//		 String file = BaseUserRecommender.class.getClassLoader()
//		 .getResource("data/ratings.dat")
//		 .getFile();
//		 System.out.println(file);
		String file = "C:\\Users\\pang1\\Desktop\\BookRecSys\\BigData\\bookshop_ml\\src\\main\\resources\\data\\bx_ratings.csv";
		// 将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
		DataModel dataModel = new GroupLensDataModel(new File(file));
		// 计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		// 计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
		UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		// 构建推荐器，协同过滤推荐有多种，如：基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
		Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
		// 给用户ID等于5的用户推荐10部电影
		List<RecommendedItem> recommendedItemList = recommender.recommend(125779, 10);
		// 打印推荐的结果
		System.out.println("使用基于用户的协同过滤算法");
		System.out.println("为用户5推荐10个商品");
		for (RecommendedItem recommendedItem : recommendedItemList) {
			System.out.println(recommendedItem);
		}
	}
}
