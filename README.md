# labseq-service

## Build and run the project
```
git clone https://github.com/MrComboF10/labseq-service.git
```
```
cd labseq-service
```
```
docker run -p 8080:8080 labseq-service:1.0.0
```

## Examples
### HTTP request for Labseq(10)
```
http://localhost:8080/labseq/10
```
### Response
```
{"index":10,"value":3}
```

## Performance measurement
### Logs format
```
(executor-thread-<thread_number>) Labseq(<n>) executed in <execution_time> ms [source: <source_type>]
```
- **thread_number**: Number of the current thread
- **n**: Index of the sequence
- **execution_time**: Execution time in milliseconds
- **source_type**: Can be from cache or from computation
### Disable logs
```
docker run -p 8080:8080 -e MEASURETIME_LOGGING_ENABLED=false labseq-service:1.0.0
```
