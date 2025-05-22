package com.timegate.aircraft;

import com.timegate.aircraft.functions.AllData;
import com.timegate.aircraft.model.AircraftData;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AircraftSpeedJob {
    private static final Logger LOG = LoggerFactory.getLogger(AircraftSpeedJob.class);
    private static final String INPUT_TOPIC = "air-send-jbj";
    private static final String OUTPUT_TOPIC = "air-receive-jbj";
    private static final String BOOTSTRAP_SERVERS = "13.209.157.53:9092,15.164.111.153:9092,3.34.32.69:9092";
    private static final String CONSUMER_GROUP = "aircraft-speed-group";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        LOG.info("Starting Aircraft Data Processing Job");
        LOG.info("Input Topic: {}", INPUT_TOPIC);
        LOG.info("Output Topic: {}", OUTPUT_TOPIC);
        LOG.info("Bootstrap Servers: {}", BOOTSTRAP_SERVERS);

        // Flink 실행 환경 설정
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);  // 로컬 테스트를 위해 병렬 처리를 1로 설정

        // Kafka 소스 설정
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers(BOOTSTRAP_SERVERS)
                .setTopics(INPUT_TOPIC)
                .setGroupId(CONSUMER_GROUP)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        LOG.info("Kafka Source configured successfully");

        // Kafka 싱크 설정
        Properties producerProps = new Properties();
        producerProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        producerProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.setProperty(ProducerConfig.ACKS_CONFIG, "all");  // 모든 복제본이 데이터를 받았는지 확인
        producerProps.setProperty(ProducerConfig.RETRIES_CONFIG, "3");  // 실패 시 3번 재시도

        KafkaSink<String> sink = KafkaSink.<String>builder()
                .setBootstrapServers(BOOTSTRAP_SERVERS)
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(OUTPUT_TOPIC)
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build())
                .setKafkaProducerConfig(producerProps)
                .build();

        LOG.info("Kafka Sink configured successfully for topic: {}", OUTPUT_TOPIC);

        // 데이터 스트림 처리
        DataStream<String> stream = env
                .fromSource(source, org.apache.flink.api.common.eventtime.WatermarkStrategy.noWatermarks(), "Kafka Source")
                .map(json -> {
                    try {
                        LOG.info("=== 데이터 수신 시작 ===");
                        LOG.info("수신된 JSON: {}", json);
                        AircraftData aircraft = objectMapper.readValue(json, AircraftData.class);
                        LOG.info("파싱된 데이터: hex={}, speed={}, altitude={}, flight={}, status={}",
                                aircraft.getHex(), aircraft.getSpeed(), aircraft.getAltitude(), 
                                aircraft.getFlightNumber(), aircraft.getStatus());
                        LOG.info("=== 데이터 수신 완료 ===");
                        return aircraft;
                    } catch (Exception e) {
                        LOG.error("JSON 파싱 에러: {}", json, e);
                        return null;
                    }
                })
                .filter(aircraft -> {
                    if (aircraft == null) {
                        LOG.warn("널 데이터 필터링됨");
                        return false;
                    }
                    LOG.info("데이터 처리 중: {}", aircraft.getHex());
                    return true;
                })
                .process(new AllData())
                .map(aircraft -> {
                    try {
                        String result = objectMapper.writeValueAsString(aircraft);
                        LOG.info("Kafka로 전송할 데이터: {}", result);
                        return result;
                    } catch (Exception e) {
                        LOG.error("데이터 직렬화 에러", e);
                        return null;
                    }
                })
                .filter(json -> {
                    if (json == null) {
                        LOG.warn("널 JSON 필터링됨");
                        return false;
                    }
                    LOG.info("최종 데이터 준비 완료");
                    return true;
                });

        // 결과를 Kafka로 전송
        stream.sinkTo(sink);
        LOG.info("Stream processing pipeline configured with Sink to topic: {}", OUTPUT_TOPIC);

        // 디버깅용 출력
        stream.print("처리된 데이터");

        // 작업 실행
        LOG.info("Starting job execution...");
        env.execute("Aircraft Data Processing");
    }
}