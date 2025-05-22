package com.timegate.aircraft.functions;

import com.timegate.aircraft.model.AircraftData;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllData extends ProcessFunction<AircraftData, AircraftData> {
    private static final Logger LOG = LoggerFactory.getLogger(AllData.class);

    @Override
    public void processElement(AircraftData aircraft, Context context, Collector<AircraftData> collector) throws Exception {
        LOG.info("Processing aircraft data: hex={}, speed={}, altitude={}, flight={}",
                aircraft.getHex(), aircraft.getSpeed(), aircraft.getAltitude(), aircraft.getFlightNumber());
        collector.collect(aircraft);
    }
} 