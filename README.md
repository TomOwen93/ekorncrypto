# Read me
### Build/Running
Clone this repo, cd to the root folder and run this command to build the docker image:
   `docker build -t crypto-aggregate-app . `

Next run this to start the image in detached mode:
   `docker run -d -p 8080:8080 --name crypto-container crypto-aggregate-app`

To check logs run:
   `docker logs -f crypto-container`

For accessing the h2 memory navigate to http://localhost:8080/h2-console/ and use the credentials in the application.yaml

For hitting the price endpoint, you can use postman or similar and send a GET to http://localhost:8080/prices/{symbol}

### Assumptions and Notes

* Controller:
    * Assuming a user will call for either a single symbol ("BTC") or a dual symbol ("BTC-USD")
    * There is an @ExceptionHandler for a custom exception (ProductNotFound). Ideally, if this service grew to have more
      than one controller, multiple custom exceptions I'd probably add a global exception handler.
    * Added basic controller tests
* Structure:
    * Kept a pretty basic spring MVC structure I'd be working with at Ocado. dto/bo in seperate packages, distinct
      controller/service/persistence packaging as well. If I had a global exception handler I'd have put it in a class
      in the exception package
* Persistence:
    * Using an in-memory h2 db for the sake of this exercise
    * Could have implemented an in memory cache (hashmap/concurrenthashmap) but felt like using Spring JPA was fine for
      this.
    * I did however use @Cacheable on the method for getting the product pairs from coinbase as we don't need to get
      that info every 10 seconds.
    * Two JPA queries to support the two "symbol" options described previously. One for byProductId (full currency) and
      another for base currency. Always getting the latest price from the saved data.
* Client calls
    * Polling Coinbase for prices using @Scheduled(fixedDelay = 10000)
    * Realised my @Async save method wasn't working as I had not configured a configuration class correctly with
      @EnableAsync. I then ran into rate limiting problems and used AI which suggested Resilience4j (used that for
      adding circuit breaker pattern @ Ocado)
    * Reason I used @Async was so that I wasn't blocking the main thread with frequent scheduled calls to coinbase. In
      hindsight this probably wasn't needed, given how few product pairs exist. If this were to scale we could benefit
      potentially from using a thread pool to handle this scheduled task.
* SOLID/Clean Code
    * If I were to extend this, I'd look to write to abstractions as we could then add a second exchange client and
      keeping our service calls agnostic to the implementation details.
    * Tried to stick to SRP where possible with services/controllers etc.
* Model
    * Trimmed/added to the coinbase responses when mapping to dtos/bos/entities etc.
    * Assumed that "delisted" status fields meant we shouldn't persist/show them to the caller.
    * Added productId/base/quote currency to the ProductPricePairEntity for querying purposes. Could definitely change
      it to show these to the user in our endpoint's response body though.
* Scaling
  * At scale we could utilise a distributed cache (for prices AND product pairs) as opposed to having an in memory one. This would decouple our persistence from the service and mean when we horizontally scale with more instances, they could all access the cached data.
  * Would have to take into account rate limiting issues (which is where a cache might help anyway) and use a circuit breaker on the client calls
  * Extending this app to use multiple exchanges would require some refactoring, mainly using abstraction (ClientProvider interface as opposed to CoinbaseClient for example)

### AI Usage
I used Gemini for solving a few issues with MockMvc tests, Dockerfile setup, as well as fixing my issues with @Async/rate limiting.
Everything else was written/designed by myself.
