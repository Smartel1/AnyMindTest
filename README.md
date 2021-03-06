### This is test app for AnyMind
- Application represents a wallet which allows sending BTC to and getting history of balance  
- deploy: 
` ./gradlew bootRun`
- send BTC to wallet:
`POST /api/v1/transfers` with json body:
`{"datetime": "2022-03-01T11:02:18.286Z", "amount": 10.2 }`.
  Constraints: positive amount, up to 20 symbols and up to 10 decimals
- get balance history of period:
`POST /api/v1/transfers/history` with json body:
`{"startDatetime": "2023-03-01T11:02:18.286Z", "endDatetime": "2023-04-01T12:02:18.286Z"}`.
  Constraints: startDate is before endDate

### features:
- stack: kotlin + spring boot + gradle
- used H2 in-memory database just to simplify things
- used liquibase migrations
- requests are being validated
- did not use Flux not to make app complicated
- unit-tests for service