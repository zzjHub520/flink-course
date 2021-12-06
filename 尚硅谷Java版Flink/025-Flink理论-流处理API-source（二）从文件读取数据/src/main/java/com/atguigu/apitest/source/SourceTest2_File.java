package com.atguigu.apitest.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceTest2_File {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();


        String inputPath = "C:\\Users\\zzj\\Documents\\workspace\\flink\\flink-course\\009-Flink理论-简单上手（三）\\src\\main\\resources\\hello.txt";
        DataStream<String> dataStream = env.readTextFile(inputPath);

        dataStream.print();
        env.execute();
    }
}
