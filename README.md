# dot-log-analyzer

## DATABASE CONFIGURATION
- Database Name: `req_limit`
- Username: `root`, Password: `password`
- MySQL Version: `mysql  Ver 8.0.27 for macos11.6 on x86_64 (Homebrew)`

## POSTMAN DOCUMENTATION
- This documentation details how to upload new text file via an endpoint
- Doc path is `https://github.com/falodunos/dot-log-analyzer/tree/master/postman-document`

## Application JAR File Path
- The compiled JAR file is available at project root folder as shown below
- ``dot-log-analyser/dot-log-analyzer-0.0.1-SNAPSHOT.jar`` 
- OR ``https://github.com/falodunos/dot-log-analyzer/blob/master/dot-log-analyzer-0.0.1-SNAPSHOT.jar``

## How To Run Application
- Create `uploads` folder in project root directory
- Copy `user_access.txt` to the created `uploads` folder
- Run application from the CLI as shown below:
- ``java -jar dot-log-analyzer-0.0.1-SNAPSHOT.jar 2022-01-01.00:00:11 hourly 200``
- In case you need to test application against a different file, please upload via the controller endpoint as shown 
in the postman API documentation



