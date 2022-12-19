# Jira Client

[CompletableFuture]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html
[Jackson]: https://github.com/FasterXML/jackson

![Dependency Check](https://github.com/chavaillaz/jira-client/actions/workflows/snyk.yml/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.chavaillaz/jira-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.chavaillaz/jira-client)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This library allows you to interact with a Jira instance and choose which HTTP client to use. It is also easily
extendable, allowing you to have custom clients and issues classes. All requests are performed asynchronously and 
return a [CompletableFuture][CompletableFuture]. The serialization and deserialization of domain objects are performed 
using [Jackson][Jackson].

Presently, it supports the two following HTTP clients:

- Java HTTP client (included since Java 11)
- Apache HTTP client 5.2

## Installation

The dependency is available in maven central (see badge for version):

```xml
<dependency>
    <groupId>com.chavaillaz</groupId>
    <artifactId>jira-client</artifactId>
</dependency>
```

Don't forget to also declare the HTTP client you want to use as a dependency (see below), as it is only indicated as 
optional in the project, to avoid gathering them all together despite the fact that only one is needed.

### Java HTTP client

It does not require any dependency.

### Apache HTTP client

It requires the following dependency:

```xml
<dependency>
  <groupId>org.apache.httpcomponents.client5</groupId>
  <artifactId>httpclient5</artifactId>
  <version>5.2.x</version>
</dependency>
```

## Usage

### Features

- **[IssueClient](src/main/java/com/chavaillaz/jira/client/IssueClient.java) -
  Everything for issues, including comments, links, transitions, attachments and work logs**
  - Issues
    - `addIssue(Issue issue)`
    - `getIssue(String issueKey)`
    - `updateIssue(Issue issue)`
    - `deleteIssue(String issueKey)`
    - `assignIssue(String issueKey, User user)`
    - `deleteAssignIssue(String issueKey)`
    - `getTransitions(String issueKey)`
    - `doTransition(String issueKey, IssueTransition transition)`
  - Comments
    - `getComments(String issueKey)`
    - `getComments(String issueKey, Integer startAt, Integer maxResults)`
    - `getComment(String issueKey, String id)`
    - `addComment(String issueKey, Comment comment)`
    - `updateComment(String issueKey, Comment comment)`
    - `deleteComment(String issueKey, String id)`
  - Votes
    - `addVote(String issueKey)`
    - `getVotes(String issueKey)`
  - Watchers
    - `getWatchers(String issueKey)`
    - `addWatcher(String issueKey, String username)`
    - `deleteWatcher(String issueKey, String username)`
  - Work Logs
    - `addWorkLog(String issueKey, WorkLog workLog)`
    - `getWorkLogs(String issueKey)`
    - `getWorkLog(String issueKey, String id)`
    - `updateWorkLog(String issueKey, WorkLog workLog)`
    - `deleteWorkLog(String issueKey, String id)`
  - Attachments
    - `getAttachment(String id)`
    - `getAttachmentContent(String url)`
    - `addAttachment(String issueKey, File... files)`
    - `deleteAttachment(String id)`
  - Remote Links
    - `getRemoteLinks(String issueKey)`
    - `getRemoteLink(String issueKey, String id)`
    - `addRemoteLink(String issueKey, RemoteLink remoteLink)`
    - `updateRemoteLink(String issueKey, RemoteLink remoteLink)`
    - `deleteRemoteLink(String issueKey, String id)`
  - Issue Links
    - `getIssueLink(String id)`
    - `addIssueLink(Link link)`
    - `deleteIssueLink(String id)`
- **[SearchClient](src/main/java/com/chavaillaz/jira/client/SearchClient.java) -
  Everything for searches, including filters**
  - Searches
    - `searchIssues(String jql)`
    - `searchIssues(String jql, Integer startAt, Integer maxResults)`
    - `searchIssues(String jql, Integer startAt, Integer maxResults, String expand)`
  - Filters
    - `addFilter(Filter filter)`
    - `getFilter(String id)`
    - `getFavoriteFilters()`
    - `updateFilter(String id, Filter filter)`
    - `deleteFilter(String id)`
- **[ProjectClient](src/main/java/com/chavaillaz/jira/client/ProjectClient.java) -
  Everything for projects, including components, versions, statuses and roles**
  - `addProject(ProjectChange project)`
  - `getProjects()`
  - `getProjects(boolean includeArchived, String expand)`
  - `getProject(String projectKey)`
  - `getProjectVersions(String projectKey)`
  - `getProjectComponents(String projectKey)`
  - `getProjectStatuses(String projectKey)`
  - `getProjectRoles(String projectKey)`
  - `updateProject(String projectKey, ProjectChange project)`
  - `deleteProject(String projectKey)`
- **[UserClient](src/main/java/com/chavaillaz/jira/client/ProjectClient.java) -
  Everything for users**
  - `getUsers(String search)`
  - `getUsers(String search, Integer startAt, Integer maxResults, Boolean includeInactive)`
  - `getAssignableUsers(String search, String key)`
  - `getAssignableUsers(String search, String key, Integer startAt, Integer maxResults, Boolean includeInactive)`
  - `getUser(String username)`
  - `getCurrentUser()`

### Client instantiation

Instantiate the Jira client of your choice by giving your Jira instance URL:

- [JavaHttpJiraClient](src/main/java/com/chavaillaz/jira/client/java/JavaHttpJiraClient.java)
- [ApacheHttpJiraClient](src/main/java/com/chavaillaz/jira/client/apache/ApacheHttpJiraClient.java)

From this `JiraClient` you will then be able to get the desired clients described in the [feature chapter](#features).

Depending on your needs, you may also want to add authentication with a personal access token (PAT) or with username and 
password using `withAuthentication` method. If you need to connect via a proxy, you can specify it with `withProxy`. 
Below an example with Apache HTTP client using both methods:

```java
JiraClient client = new ApacheHttpJiraClient("https://jira.mycompany.com")
    .withAuthentication("myUsername","myPassword")
    .withProxy("http://proxy.mycompany.com:1234");
```

### Iterable results

When requesting a list of elements, the client returns an object encapsulating this list, sometimes with more pieces of
information depending on the Jira endpoint called. From this object, you can iterate directly on its child elements as
in the example below:

```java
Consumer<Filter> deleteFilter = filter -> client.getSearchClient().deleteFilter(filter.getId());
client.getSearchClient().getFavoriteFilters()
    .thenAccept(filters -> filters.forEach(deleteFilter));
```

### Issue custom fields

If your project has custom fields for issues, you have two choices in order to get and set them.

#### Use attribute containing all custom fields

First possibility, simply use `issue.getFields().getCustomFields().get("customfield_12345")` to get the field and
`issue.getFields().getCustomFields().put("customfield_12345", businessEngineer)` to set a value for this field.

#### Reimplement fields and issue class

Second possibility, create a new class extending `Fields` to hide the custom fields identifiers and interact with Jira
in a more understandable way:

```java
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyFields extends Fields {

    @JsonProperty("customfield_12345")
    private User businessEngineer;
    
    ...

}
```

A new `Issue` class will also be needed to override the `fields` attribute:

```java
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CompanyIssue extends Issue {

    private CompanyFields fields;

}
```

Finally, specify it when getting the `IssueClient`: `client.getIssueClient(CompanyIssue.class)`. Please be aware that 
both solutions cannot live together as when you have attributes in the class for your fields, it will then not be in 
the `customFields` map (containing all the unmapped fields).

## Contributing

If you have a feature request or found a bug, you can:

- Write an issue
- Create a pull request

Please be aware the attributes of classes are ordered alphabetically.

## License

This project is under Apache 2.0 License.