package com.atguigu.wc;

import java.lang.reflect.Executable;
import java.util.stream.Collector;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;

public class WordCount {
    public static void main(String[] args) throws Exception {
        //创建执行环境
        ExecutionEnvironment env =  ExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取数据
        String inputPath = "C:\\Users\\zzj\\Documents\\workspace\\flink\\flink-course\\project\\01-Flink理论-简单上手（一）\\src\\main\\resources\\hello.txt";
        DataSet<String> inputDataSet = env.readTextFile(inputPath);

        //对数据集进行处理，按空格分词展开，转换成（word，1）二元组进行统计
        DataSet<Tuple2<String, Integer>> resultSet = inputDataSet.flatMap(new MyFlatMapper())
                .groupBy(0) // 按照第一个位置的word分组
                .sum(1);//将第二个位置上的数据求和

        resultSet.print();
    }

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>>{
        @Override
        public void flatMap(String s, org.apache.flink.util.Collector<Tuple2<String, Integer>> collector) throws Exception {
            // 按空格分词
            String[] words = s.split(" ");
            //遍历所有word，包成二元组输出
            for (String word : words) {
                collector.collect(new Tuple2<>(word, 1));
            }

        }
    }
}













