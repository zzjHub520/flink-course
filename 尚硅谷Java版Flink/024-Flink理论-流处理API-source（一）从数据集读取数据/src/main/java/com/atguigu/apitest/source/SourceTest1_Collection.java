package com.atguigu.apitest.source;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class SourceTest1_Collection {
    public static void main(String[] args) throws Exception {
        //创建执行环境
        StreamExecutionEnvironment env =  StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(8);

        DataStream<SensorReading> dataStream = env.fromCollection(Arrays.asList(new SensorReading("sensor_1", 1234L, 32.6),
                new SensorReading("sensor_1", 1234L, 32.6),
                new SensorReading("sensor_1", 1234L, 32.6),
                new SensorReading("sensor_1", 1234L, 32.6)
        ));

        DataStream<Integer> integerDataStream = env.fromElements(1, 2, 33, 4, 1);
        dataStream.print("data");
        integerDataStream.print("int");

        env.execute();
    }
}




