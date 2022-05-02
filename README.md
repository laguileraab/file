<div align="center" id="top"> 
  <a href="" rel="noopener">

 <img width=200px height=200px src="https://i.imgur.com/6wj0hh6.jpg" alt="Api"></a>
  &#xa0;
</div>

<h1 align="center">Alfresco Project</h1>

<p align="center">

  <img alt="Github top language" src="https://img.shields.io/github/languages/top/laguileraab/file?color=56BEB8">

  <img alt="Github language count" src="https://img.shields.io/github/languages/count/laguileraab/file?color=56BEB8">

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/laguileraab/file?color=56BEB8">

</p>
<p align="center">
  <a href="#dart-about">About</a> &#xa0; | &#xa0;
    <a href="#white_check_mark-requirements-for-compilation">Requirements</a> &#xa0; | &#xa0;
    <a href="#rocket-technologies">Technologies</a> &#xa0; | &#xa0;
  <a href="#checkered_flag-starting">Starting</a> &#xa0; | &#xa0;
  <a href="#sparkles-usage-of-the-app">Usage</a> &#xa0; | &#xa0;
    <a href="#test_tube-perform-tests">Tests</a> &#xa0; | &#xa0;
  <a href="https://github.com/laguileraab" target="_blank">Author</a>
</p>

<br>

## :dart: About ##

In this project i made a REST API that allow you to upload a file and download it from Alfresco using WebClient. The files are limited for a 200mb files, but it can be changed in the application's properties, there is currently no restriction in the type of file to be uploaded.

Alfresco zip every file when is going to be downloaded.

## :white_check_mark: Requirements for compilation <a name = "requirement"></a>

You need to have Java >= 17 and Maven installed in order to run this project. Check if you have install it already with:

Java
```bash
java --version
```

Maven
```bash
mvn --version
```

Is necessary to have alfresco installed.
In case you're using a cloud or having alfresco installed externally, change ALFRESCOBASE constant in ./utils/Constants.java file, same if you use another user or password.

## :rocket: Technologies <a name = "tech"></a>

The following technologies were used in this project:

- [Java](https://www.java.com/)
- [Spring](https://spring.io/)
- [Alfresco](https://www.alfresco.com/)


## :checkered_flag: Starting ## <a name = "starting"></a>


```bash
# Clone this project
$ git clone https://github.com/laguileraab/file

# Access
$ cd file

# Install dependencies and skip tests
$ mvn install -DskipTests

# Run the project
$ java -jar .\target\file-0.0.1-SNAPSHOT.jar

# The app server will initialize in the <http://127.0.0.1>

```


## :sparkles: Usage of the app <a name = "usage"></a>

In the project is been added Swagger 3 for documentation of the API, this allows also to perform test in the web application and an understanding of the required objects for every endpoint.

```bash
#Swagger 3
http://127.0.0.1/swagger-ui.html
```

## Rest API File

```bash
# Upload file
curl --location --request POST 'localhost/file' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--form 'file=@"/C:/test.txt"'

# Download file
curl --location --request GET 'localhost/file/553f5c88-537d-4bd4-8674-e939f60f335d' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='

# Get file info from alfresco
curl --location --request GET 'localhost/file/553f5c88-537d-4bd4-8674-e939f60f335d/info' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='

# Get url for direct download from alfresco
curl --location --request GET 'localhost/file/553f5c88-537d-4bd4-8674-e939f60f335d/url' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='

# Get versions of file //TODO, needs to be implemented
curl --location --request GET 'localhost/file/553f5c88-537d-4bd4-8674-e939f60f335d/versions' \
--header 'Authorization: Basic YWRtaW46YWRtaW4='
```

## ✍️ Author <a name = "author"></a>

- [@laguileraab](https://github.com/laguileraab)

&#xa0;

<a href="#top">Back to top</a>