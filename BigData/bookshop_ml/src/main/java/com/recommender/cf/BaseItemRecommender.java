package com.recommender.cf;

import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 * 基于物品的推荐
 * 
 * @author Lenovo
 *
 */
public class BaseItemRecommender {
	public static void main(String[] args) {
		try {
			// 准备数据 这里是电影评分数据
//		 String file = BaseUserRecommender.class.getClassLoader()
//		 .getResource("data/ratings.dat")
//		 .getFile();
//		 System.out.println(file);
			// 准备数据 这里是电影评分数据
			File file = new File(
					"D:\\GaoLei\\2019年工作\\大数据实验室\\00 项目案例研发\\实训项目案例列表\\Hadoop相关\\3 智能图书销售系统\\4 示例代码\\机器学习模块\\bookshop_ml\\src\\main\\resources\\data\\ratings.dat");
			// 将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
			DataModel dataModel = new GroupLensDataModel(file);
			// 计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
			ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
			// 构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
			GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);

			// 给用户ID等于5的用户推荐10个与2398相似的商品
			List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(5, 2398, 10);
			// 打印推荐的结果
			System.out.println("使用基于物品的协同过滤算法");
			System.out.println("根据用户5当前浏览的商品2398，推荐10个相似的商品");
			for (RecommendedItem recommendedItem : recommendedItemList) {
				System.out.println(recommendedItem);
			}
			long start = System.currentTimeMillis();
			recommendedItemList = recommender.recommendedBecause(5, 34, 10);
			// 打印推荐的结果
			System.out.println("使用基于物品的协同过滤算法");
			System.out.println("根据用户5当前浏览的商品34，推荐10个相似的商品");
			for (RecommendedItem recommendedItem : recommendedItemList) {
				System.out.println(recommendedItem);
			}
			System.out.println(System.currentTimeMillis() - start);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
