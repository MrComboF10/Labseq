# labseq-service

## Build and run the project
```
git clone https://github.com/MrComboF10/labseq-service.git
```
```
cd labseq-service
```
```
docker-compose up --build
```
- Frontend: http://localhost:4200/
- Backend: http://localhost:8080/labseq/10
- SwaggerUI: http://localhost:8080/swagger-ui/

## Performance measurement
### Logs format
(executor-thread-<**thread_number**>) Labseq(<**n**>) executed in <**execution_time**> ms [source: <**source_type**>]
- **thread_number**: Current thread number
- **n**: Sequence index
- **execution_time**: Execution time in milliseconds
- **source_type**: From **cache** or **computation**
### Enable/Disable logs
1. Set MEASURETIME_LOGGING_ENABLED in .env
2. Restart containers:
```
docker compose up
```
