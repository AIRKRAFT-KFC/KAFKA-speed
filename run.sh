#!/bin/bash

echo "Building Payment Limit Application..."
./gradlew shadowJar

if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi

echo "Build successful! JAR created at build/libs/payment-limit.jar"
echo
echo "To submit to Flink:"
echo "flink run -c org.example.payment_over_limit.PaymentLimitApp build/libs/payment-limit.jar"
