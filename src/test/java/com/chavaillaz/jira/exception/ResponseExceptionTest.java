package com.chavaillaz.jira.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseExceptionTest {

    @Test
    @DisplayName("Check error extraction from HTML format")
    void checkHtmlErrors() {
        String body = """
                <div class="aui-page-panel">
                    <div class="aui-page-panel-inner">
                        <main role="main" id="main" class="aui-page-panel-content">
                            <div class="aui-page-header">
                                <div class="aui-page-header-inner">
                                    <div class="aui-page-header-main">
                                        <h1>Unauthorized (401)</h1>
                                    </div>
                                </div>
                            </div>
                            <div class="aui-message aui-message-warning warning">
                                <p>Encountered a <code>&quot;401 - Unauthorized&quot;</code> error while loading this page.</p>
                                <p>Basic Authentication Failure - Reason : AUTHENTICATED_FAILED</p>
                                <p><a href="/secure/MyJiraHome.jspa">Go to Jira home</a></p>
                            </div>
                        </main>
                    </div>
                </div>
                """;

        Exception exception = new ResponseException(401, body);
        assertEquals("Jira responded with 401: Encountered a 401 - Unauthorized error while loading this page. "
                + "Basic Authentication Failure - Reason : AUTHENTICATED_FAILED Go to Jira home", exception.getMessage());
    }

    @Test
    @DisplayName("Check error extraction from JSON format")
    void checkJsonErrors() {
        String body = """
                {
                    "errorMessages": [
                        "Issue Does Not Exist"
                    ],
                    "errors": {}
                }
                """;

        Exception exception = new ResponseException(404, body);
        assertEquals("Jira responded with 404: Issue Does Not Exist", exception.getMessage());
    }

    @Test
    @DisplayName("Check error extraction from unknown format")
    void checkUnknownErrors() {
        String body = "I'm a teapot";

        Exception exception = new ResponseException(418, body);
        assertEquals("Jira responded with 418: I'm a teapot", exception.getMessage());
    }

}
