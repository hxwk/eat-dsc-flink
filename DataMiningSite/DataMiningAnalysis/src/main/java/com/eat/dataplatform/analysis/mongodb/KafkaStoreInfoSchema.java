package com.eat.dataplatform.analysis.mongodb;

import com.eat.dataplatform.analysis.entity.ResInfo;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class KafkaStoreInfoSchema implements DeserializationSchema<ResInfo>, SerializationSchema<ResInfo> {

    private static final long serialVersionUID = 6154188370181669752L;

    @Override
    public byte[] serialize(ResInfo event) {
        return event.toString().getBytes();
    }

    @Override
    public ResInfo deserialize(byte[] message) throws IOException {
        return ResInfo.fromString(new String(message));
    }

    @Override
    public boolean isEndOfStream(ResInfo nextElement) {
        return false;
    }

    @Override
    public TypeInformation<ResInfo> getProducedType() {
        return TypeInformation.of(ResInfo.class);
    }
}
