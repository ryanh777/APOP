---
title: README
---

# Pet Obesity Survey App

![Logo](apop-logo.png)

## For The Association for Pet Obesity Prevention, under Dr. Ernie Ward & Prof. Jeff Terrell
### Comp 523 Fall 2020 - Group M: Matthew Cahill, Ryan Humphrey, Robbie Errico

**Overview**

Veterinarians currently record information on pet obesity on paper, which must then be manually translated to digital data before it can be analyzed, which is inefficient. We will attempt to create an app to streamline this process, while still being simple and intuitive for use.

**Getting started**
- Once deployed, the app should run on any Android device. 
- To work with the repository in Android Studio, you’ll need the Amplify CLI and an Amazon Web Services account.
- docs.amplify.aws/lib/project-setup/prereq/q/platform/android
- Follow the instructions for local CLI setup in the link above if you want to connect to an Amazon Web Services backend. 
- To run locally after setup, just emulate a device on Android Studio and run the built project.
- These instructions should work for any version of the project on Android Studio.

**Testing**
- Android Studio can run the test suite by changing the run configuration to “Run Tests” in Android Studio.
- Unit tests in the test suite can be run through Android Studio, integration tests can be run using sample user input from a shell. We're in the process of finishing our automated input script now. To make your own, follow the link below.
- androidpedia.net/en/tutorial/9408/adb-shell#send-text-key-pressed-and-touch-events-to-android-device-via-adb

**Deployment**
- The project will be linked to an AWS backend, which you must configure for yourself. We recommend creating a Lambda function to consolidate the S3 data into a DynamoDB database.
- Continuous Deployment is not yet enabled.

**Technologies used**
- We chose to work with Amazon Web Services for the following services: S3, Lambda, & DynamoDB. We connect to an S3 bucket using the Amplify Framework, a system designed to interact with AWS components. In the bucket, object creation triggers a Lambda function, which takes the object and passes it to the DynamoDB database, where the JSON object is treated as an entry in an SQL database which can be queried.
- ADR's are located at the link below.
- https://github.com/mtcahill57/523-fa20-m.github.io/blob/gh-pages/architecture.md

**Contributing**
- A new developer to the project should get access to the repository (a contributor role if applicable).
- Our project currently has a board on Trello, but that will be used in our transition to future applications with this project.
- Project Site: mtcahill57.github.io/523-fa20-m.github.io

**Authors**
- Frontend development was led by Ryan Humphrey.
- Backend integration was researched and developed by Robbie Errico and Matthew Cahill.

[**License**](License.md)
- This software is licensed under the MIT License, a permissive open-source software license. 

**Acknowledgements**
- We'd like to thank Jeff Terrell and Jim Fletcher for their guidance and supoort with this project
