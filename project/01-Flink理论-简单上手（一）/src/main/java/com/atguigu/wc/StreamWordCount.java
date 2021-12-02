package com.atguigu.wc;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;

public class StreamWordCount {

    public static void main(String[] args) throws Exception {

        //创建执行环境
        ExecutionEnvironment env =  ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(8);

        //从文件中读取数据
        String inputPath = "C:\\Users\\zzj\\Documents\\workspace\\flink\\flink-course\\project\\01-Flink理论-简单上手（一）\\src\\main\\resources\\hello.txt";
        DataSource<String> inputDataStream = env.readTextFile(inputPath);

        SingleOutputStreamOperator<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                .keyBy(0)
                .sum(1);
        resultStream.print();

        env.execute();
    }


}
