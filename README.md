# Random Twitter Likes

This project contains source code and supporting files for a serverless app built using AWS SAM. Given a Twitter username, this app shows back a random tweet liked by that user.

It includes the following files and folders.

- `functions/RandomLikesFunction/src/main` - Code for the application's Lambda function written in Java.
- `functions/RandomLikesFunction/src/test` - Unit tests for the application code. 
- `functions/RandomLikesFunction/pom.xml` - Maven file defining all the required dependencies and configured to a build an uberjar for deployment.
- `functions/template.yaml` - SAM template that defines the application's AWS resources.
- `public` - Front end code in HTML, some CSS and vanilla JS. You may deploy the same on Netlify.

## Deploy the application on AWS

You can deploy the app on AWS using SAM CLI. To use the SAM CLI for this app, you need the following tools.

* SAM CLI - [Install the SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
* Java8 - [Install the Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Maven - [Install Maven](https://maven.apache.org/install.html)

To build and deploy your application for the first time, run the following in your shell:

```bash
sam build
sam deploy --guided
```

The first command will build the source of your application. The second command will package and deploy your application to AWS, with a series of prompts:

* **Stack Name**: The name of the stack to deploy to CloudFormation. This should be unique to your account and region, and a good starting point would be something matching your project name.
* **AWS Region**: The AWS region you want to deploy your app to.
* **Confirm changes before deploy**: If set to yes, any change sets will be shown to you before execution for manual review. If set to no, the AWS SAM CLI will automatically deploy application changes.
* **Allow SAM CLI IAM role creation**: Many AWS SAM templates, including this example, create AWS IAM roles required for the AWS Lambda function(s) included to access AWS services. By default, these are scoped down to minimum required permissions. To deploy an AWS CloudFormation stack which creates or modified IAM roles, the `CAPABILITY_IAM` value for `capabilities` must be provided. If permission isn't provided through this prompt, to deploy this example you must explicitly pass `--capabilities CAPABILITY_IAM` to the `sam deploy` command.
* **Save arguments to samconfig.toml**: If set to yes, your choices will be saved to a configuration file inside the project, so that in the future you can just re-run `sam deploy` without parameters to deploy changes to your application.

**You will find the API Gateway Endpoint URL in the output values displayed after deployment. Update the outputted URL in `public/index.js` file to invoke the same from UI so as to view the tweet in the browser.**

## Use the SAM CLI to build and test locally

Build your application with the `sam build` command.

```bash
functions$ sam build
```

The SAM CLI installs dependencies defined in `functions/RandomLikesFunction/pom.xml`, creates a deployment package, and saves it in the `functions/.aws-sam/build` folder.

## Unit tests

Tests are defined in the `functions/RandomLikesFunction/src/test` folder in this project.

```bash
functions$ cd RandomLikesFunction
RandomLikesFunction$ mvn test
```

## Cleanup

To delete the sample application that you created, use the AWS CLI. Assuming you used "random-likes" as stack name, you can run the following:

```bash
aws cloudformation delete-stack --stack-name random-likes
```

## Resources

See the [AWS SAM developer guide](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) for an introduction to SAM specification, the SAM CLI, and serverless application concepts.