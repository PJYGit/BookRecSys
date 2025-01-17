package com.recommender.cf;

import java.io.File;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;

/**
 * 获取推荐的准确率和召回率
 *
 * @author Lenovo
 */
public class MyIRStatistics {
    public static void main(String[] args) throws Exception {
        String path = "D:\\GaoLei\\2019年工作\\大数据实验室\\00 项目案例研发\\实训项目案例列表\\Hadoop相关\\3 智能图书销售系统\\4 示例代码\\机器学习模块\\bookshop_ml\\src\\main\\resources\\data\\ratings.dat";
        //String path=MyIRStatistics.class.getClassLoader().getResource("data/ratings.dat").getFile();
        System.out.println(path);
        // 准备数据 这里是电影评分数据
        File file = new File(path);
        // 将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // 计算推荐4个结果时的查准率和召回率
        // 使用评估器，并设定评估期的参数
        // 4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 4,
                GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }

}
