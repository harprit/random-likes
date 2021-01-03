# Random Twitter Likes

Given a Twitter username, this application shows back a random tweet liked by that user.

### Requirements

* AWS SAM CLI
* Java 8
* Maven

## Build and deploy

```bash
sam build
```

```bash
sam deploy -g
```

When deploy command completes, update the outputted URL to this [JS file](public/index.js) to invoke the same from UI.
