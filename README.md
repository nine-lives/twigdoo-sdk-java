# Twigdoo Client Java SDK

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.9ls/twigdoo-java-sdk/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.9ls/twigdoo-java-sdk)
[![Build Status](https://api.travis-ci.org/nine-lives/twigdoo-sdk-java.png)](https://travis-ci.org/nine-lives/twigdoo-sdk-java)
[![Code Quality](https://api.codacy.com/project/badge/grade/751e84aa61ac44c0a524dcf144791568)](https://www.codacy.com/app/nine-lives/twigdoo-sdk-java)
[![Coverage](https://api.codacy.com/project/badge/coverage/751e84aa61ac44c0a524dcf144791568)](https://www.codacy.com/app/nine-lives/twigdoo-sdk-java)

## Getting Started

The Twigdoo API requires you to have an api key/token. 

All API calls are routed from the `Twigdoo` object.

```
    Twigdoo twigdoo = Twigdoo.make(apiKey);
```

The sdk is hosted on maven central so you can include it as a dependency 
in your projects as follows:

### Gradle/Grails
```
    compile 'com.9ls:twigdoo-java-sdk:1.0.4'
```

### Apache Maven
```
    <dependency>
        <groupId>com.9ls</groupId>
        <artifactId>twigdoo-java-sdk</artifactId>
        <version>1.0.4</version>
    </dependency>
```

### Apache Ivy
```
    <dependency org="com.9ls" name="twigdoo-java-sdk" rev="1.0.4" />
```

## Create a Lead

To create a lead:

```
        LeadRequest lead = new LeadRequest()
                .withClient(new Client()
                    .withEmail("jake@test.com")
                    .withName("Jake Blues")
                .withService(new Service()
                    .withName("Ray's Music Exchange")
                    .withAddress("Chicago")
                .withSourceId("12345")
        
        Lead result = twigdoo.create(lead);
```

## Update a Lead

To do a full update on a lead:
```
        LeadRequest lead = new LeadRequest()
                .withClient(new Client()
                    .withEmail("elwood@test.com")
                    .withName("Elwood Blues")
                .withService(new Service()
                    .withName("Ray's Music Exchange")
                    .withAddress("Chicago")
                .withSourceId("12345")
        
        Lead result = twigdoo.update(lead);
        long twigdooId = result.getId();
```

## Patch a Lead

To update one or more fields on a lead:
```
        LeadRequest lead = new LeadRequest()
                .withService(new Service()
                    .withName("Ray's Music Exchange")
                .withSourceId("12345")
        
        Lead result = twigdoo.patch(lead);
        long twigdooId = result.getId();
```

## Mark a LeadRequest as qualified

To mark a lead as qualified:
```
        LeadRequest lead = new LeadRequest()
                .withService(new Service()
                    .withStatus("qualified")
                .withSourceId("12345")
        
        Lead result = twigdoo.patch(lead);
        long twigdooId = result.getId();
```

## Webhooks

To parse a webhook payload you can use the WebhookCallProcessor and register a WebhookListener. 
The WebhookCallProcessor.process call forks calls to the listeners into a separate thread so it will return
immediately. All registered threads are run in a single thread so they will be called serially. The default 
number of threads for WebhookCallProcessor is 5.

You may implement as many or as few of the methods on the WebhookListener interface as you require. Any errors
that occur during either parsing the payload or in a listener will fire the error method on the listener.  

### The WebhookListener

```

    WebhookListener listener = new WebhookListener() {
            public void created(Webhook<Lead> hook, Lead lead) { }
            public void updated(Webhook<Lead> hook, Lead lead) { }
            public void created(Webhook<Call> hook, Call call) { }
            public void updated(Webhook<Call> hook, Call call) { }
            public void created(Webhook<Sms> hook, Sms sms) { }
            public void updated(Webhook<Sms> hook, Sms sms) { }
            public void created(Webhook<Email> hook, Email email) { }
            public void updated(Webhook<Email> hook, Email email) { }
            public void error(Exception e, String payload) { }
    });
```

### An HttpServlet example
```
    public class TwigdooWebhookServlet extends HttpServlet {
        private static final TWIGDOO_VERIFY_KEY = "VERIFICATION_KEY";
        private WebhookCallProcessor processor = new WebhookCallProcessor()
        private WebhookListener listener = ...

        public void init() throws ServletException {
            processor.addListener(listener);
        }
    
        protected void doPost(HttpServletRequest request, HttpServletResponse response) {
            if (!TWIGDOO_VERIFY_KEY.equals(request.getHeader("x-twigdoo-verify"))) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                String payload = IOUtils.toString(request.getReader());
                processor.process(payload);
            }
        }
    }
``` 

### A Spring Controller example
```
    @RequestMapping(value = "twigdoo/*")
    @Controller
    public class TwigdooWebhookServlet extends HttpServlet {
        private static final TWIGDOO_VERIFY_KEY = "VERIFICATION_KEY";
        private WebhookCallProcessor processor = new WebhookCallProcessor();
        private WebhookListener listener = ...;

        @PostConstruct
        public void init() {
            processor.addListener(listener);
        }
    
        @ResponseBody
        @RequestMapping(value = "/webhook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
        public String twigdoo(HttpEntity<String> httpEntity, HttpServletResponse)  {
            if (!TWIGDOO_VERIFY_KEY.equals(httpEntity.getHeaders().get("x-twigdoo-verify"))) {
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                processor.process(httpEntity.getBody());
            }
            return "";
        }
    }
``` 

## Custom Configuration

You can also use `ClientConfiguration` to configure the SDK. Apart
from the the api key all the other values have defaults.

```
    Twigdoo twigdoo = Twigdoo.make(new Configuration()
        .withApiKey(apiKey)
        .withEndpoint("https://api.twigdoo.com")
        .withMaxConnectionsPerRoute(20)
        .withUserAgent("twigdoo-sdk-java 1.0.4")
        .withBlockTillRateLimitReset(false)
        .withRequestsPerSecond(5)
        .withRequestBurstSize(20);
```

| Configuration Attribute | Description |
| ----------------------- | ----------- |
| Endpoint | The base api url. Defaults to https://api.twigdoo.com |
| MaxConnectionsPerRoute | The effective maximum number of concurrent connections in the pool. Connections try to make use of the keep-alive directive. Defaults to 20
| UserAgent | The user agent string sent in the request
| BlockTillRateLimitReset | If set to true then the client will block if the rate limit has been reached until the reset timestamp has expired. Defaults to false
| RequestsPerSecond | If rate limited is true then the maximum requests per second 
| RequestBurstSize | If rate limited the number of consecutive requests allowed before rate limit is enforced 


## Build

Once you have checked out the project you can build and test the project with the following command:

```
    gradlew check -x integrationTest -x jacocoTestReport
```

 