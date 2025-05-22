package com.timegate.aircraft.functions;

import com.timegate.aircraft.model.AircraftData;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeedAnomalyDetector extends ProcessFunction<AircraftData, AircraftData> {
    private static final Logger LOG = LoggerFactory.getLogger(SpeedAnomalyDetector.class);
    
    // 속도 임계값 (km/h)
    private static final double SPEED_THRESHOLD = 500.0;
    private static final double MIN_NORMAL_SPEED = 450.0;
    private static final double MAX_NORMAL_SPEED = 480.0;

    @Override
    public void processElement(AircraftData aircraft, Context context, Collector<AircraftData> collector) throws Exception {
        double speed = aircraft.getSpeed();
        
        // 속도가 임계값을 초과하는 경우
        if (speed > SPEED_THRESHOLD) {
            LOG.error("심각한 속도 이상 감지 - 항공기: {} ({}), 속도: {} km/h (임계값: {} km/h)", 
                    aircraft.getHex(), aircraft.getFlightNumber(), speed, SPEED_THRESHOLD);
            aircraft.setStatus("CRITICAL_SPEED");
        }
        // 정상 속도 범위를 벗어난 경우
        else if (speed < MIN_NORMAL_SPEED || speed > MAX_NORMAL_SPEED) {
            LOG.warn("속도 이상 감지 - 항공기: {} ({}), 속도: {} km/h (정상 범위: {}~{} km/h)", 
                    aircraft.getHex(), aircraft.getFlightNumber(), speed, MIN_NORMAL_SPEED, MAX_NORMAL_SPEED);
            aircraft.setStatus("WARNING_SPEED");
        }
        // 정상 속도인 경우
        else {
            aircraft.setStatus("NORMAL");
        }
        
        collector.collect(aircraft);
    }
}