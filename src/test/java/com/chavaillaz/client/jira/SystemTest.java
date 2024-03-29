package com.chavaillaz.client.jira;

import static io.vertx.core.Vertx.vertx;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;
import static org.junit.platform.commons.util.StringUtils.isNotBlank;

import java.util.stream.Stream;

import com.chavaillaz.client.jira.apache.ApacheHttpJiraClient;
import com.chavaillaz.client.jira.domain.CompanyIssue;
import com.chavaillaz.client.jira.domain.Issue;
import com.chavaillaz.client.jira.java.JavaHttpJiraClient;
import com.chavaillaz.client.jira.okhttp.OkHttpJiraClient;
import com.chavaillaz.client.jira.vertx.VertxHttpJiraClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SystemTest {

    static final String JIRA_INSTANCE = "https://jira.mycompany.com";
    static final JiraClient<CompanyIssue> JAVA_HTTP_CLIENT = configure(new JavaHttpJiraClient<>(JIRA_INSTANCE, CompanyIssue.class));
    static final JiraClient<CompanyIssue> APACHE_HTTP_CLIENT = configure(new ApacheHttpJiraClient<>(JIRA_INSTANCE, CompanyIssue.class));
    static final JiraClient<CompanyIssue> OK_HTTP_CLIENT = configure(new OkHttpJiraClient<>(JIRA_INSTANCE, CompanyIssue.class));
    static final JiraClient<CompanyIssue> VERTX_HTTP_CLIENT = configure(new VertxHttpJiraClient<>(vertx(), JIRA_INSTANCE, CompanyIssue.class));

    static <I extends Issue> JiraClient<I> configure(JiraClient<I> client) {
        return client.withUserAuthentication("myUsername", "myPassword");
    }

    static Stream<Arguments> allClients() {
        return Stream.of(
                Arguments.of(named("Java HTTP client", JAVA_HTTP_CLIENT)),
                Arguments.of(named("Apache HTTP client", APACHE_HTTP_CLIENT)),
                Arguments.of(named("OkHttp client", OK_HTTP_CLIENT)),
                Arguments.of(named("Vert.x client", VERTX_HTTP_CLIENT))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("allClients")
    @DisplayName("Check story fields")
    @Disabled("Set Jira URL and credentials before launching")
    void checkStoryFields(JiraClient<CompanyIssue> client) throws Exception {
        CompanyIssue issue = client.getIssueApi().getIssue("PROJECT-12345").get();
        assertTrue(isNotBlank(issue.getFields().getSummary()));
        assertTrue(isNotBlank(issue.getFields().getDescription()));
        // TODO: Add other assertion of fields, depending on your issue and possibly on your custom fields
    }

}
