package com.atguigu.wc;

//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.api.java.utils.ParameterTool;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamWordCount {
    public static void main(String[] args) throws Exception {
        //创建执行环境
//        StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(8);
//
////        //从文件中读取数据
////        String inputPath = "C:\\Users\\zzj\\Documents\\workspace\\flink\\flink-course\\009-Flink理论-简单上手（三）\\src\\main\\resources\\hello.txt";
////        DataStream<String> inputDataStream = env.readTextFile(inputPath);
//
//        ParameterTool parameterTool = ParameterTool.fromArgs(args);
//        String host = parameterTool.get("host");
//        int port = parameterTool.getInt("port");
//
//        DataStream<String> inputDataStream = env.socketTextStream(host, port);
//
//        //基于数据流进行转换计算
//        SingleOutputStreamOperator<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
//                .keyBy(0)
//                .sum(1);
//        resultStream.print();
//
//        env.execute();
    }
}
