@echo off
echo Building Payment Limit Application...
call gradlew.bat shadowJar

if %ERRORLEVEL% neq 0 (
    echo Build failed!
    exit /b %ERRORLEVEL%
)

echo Build successful! JAR created at build/libs/payment-limit.jar
echo.
echo To submit to Flink:
echo flink run -c org.example.payment_over_limit.PaymentLimitApp build/libs/payment-limit.jar
