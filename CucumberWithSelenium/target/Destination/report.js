$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("MyTest.feature");
formatter.feature({
  "line": 1,
  "name": "Reset functionality on login page of Application",
  "description": "",
  "id": "reset-functionality-on-login-page-of-application",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "Verification Page Title for MSHS",
  "description": "",
  "id": "reset-functionality-on-login-page-of-application;verification-page-title-for-mshs",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "Open the Chrome and launch the application",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "Click On Page Logo",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "Verify Page title",
  "keyword": "Then "
});
formatter.match({
  "location": "Steps.open_the_Chrome_and_launch_the_application()"
});
formatter.result({
  "duration": 12468110300,
  "status": "passed"
});
formatter.match({
  "location": "Steps.click_On_Page_Logo()"
});
formatter.result({
  "duration": 1027692200,
  "status": "passed"
});
formatter.match({
  "location": "Steps.verify_Page_title()"
});
formatter.result({
  "duration": 8935000,
  "error_message": "java.lang.AssertionError: expected [Mount Sinai Health System - New York City | Mount Sinai - New Yor] but found [Mount Sinai Health System - New York City | Mount Sinai - New York]\r\n\tat org.testng.Assert.fail(Assert.java:94)\r\n\tat org.testng.Assert.failNotEquals(Assert.java:513)\r\n\tat org.testng.Assert.assertEqualsImpl(Assert.java:135)\r\n\tat org.testng.Assert.assertEquals(Assert.java:116)\r\n\tat org.testng.Assert.assertEquals(Assert.java:190)\r\n\tat org.testng.Assert.assertEquals(Assert.java:200)\r\n\tat StepsDefinition.Steps.verify_Page_title(Steps.java:43)\r\n\tat ✽.Then Verify Page title(MyTest.feature:7)\r\n",
  "status": "failed"
});
});