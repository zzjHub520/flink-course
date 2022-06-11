package com.atguigu.chapter06_TimeAndWindows;

import com.atguigu.chapter05_DataStreamBasics.ClickSource;
import com.atguigu.chapter05_DataStreamBasics.Event;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

public class WindowsTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.getConfig().setAutoWatermarkInterval(100);

//        SingleOutputStreamOperator<Event> stream = env.fromElements(
//                        new Event("Mary", "./home", 1000L),
//                        new Event("Bob", "./cart", 2000L),
//                        new Event("Alice", "./prod?id=100", 3000L),
//                        new Event("Alice", "./prod?id=200", 3500L),
//                        new Event("Bob", "./prod?id=2", 2500L),
//                        new Event("Alice", "./prod?id=300", 3600L),
//                        new Event("Bob", "./home", 3000L),
//                        new Event("Bob", "./prod?id=1", 2300L),
//                        new Event("Bob", "./prod?id=3", 3300L))
        SingleOutputStreamOperator<Event> stream =  env.addSource(new ClickSource())
                //乱序流的watermark生成
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ZERO)
                        .withTimestampAssigner(new SerializableTimestampAssigner<Event>() {
                            @Override
                            public long extractTimestamp(Event element, long recordTimestamp) {
                                return element.timestamp;
                            }
                        }));

        stream.map(new MapFunction<Event, Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> map(Event value) throws Exception {
                        return Tuple2.of(value.user, 1L);
                    }
                })
                .keyBy(data -> data.f0)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
//                .window(SlidingEventTimeWindows.of(Time.seconds(1),Time.minutes(5)))
//                .countWindow(10,2)
                .reduce(new ReduceFunction<Tuple2<String, Long>>() {
                    @Override
                    public Tuple2<String, Long> reduce(Tuple2<String, Long> value1, Tuple2<String, Long> value2) throws Exception {
                        return Tuple2.of(value1.f0, value1.f1 + value2.f1);
                    }
                })
                .print();

        env.execute();

    }
}






















