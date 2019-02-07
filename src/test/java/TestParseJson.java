import org.junit.Assert;
import org.junit.Test;
import se.kth.dd2480.grp25.ci.CiServer;

public class TestParseJson {
  String json =
      "{"
          + "\"ref\": \"refs/heads/issue/9\","
          + "\"before\": \"db12254b2e76433780f2a004944cfba38f799606\","
          + "\"after\": \"9fc4d28b68c20a2e5c064d91b955d9c529b86a15\","
          + "\"created\": false,"
          + "\"deleted\": false,"
          + "\"forced\": false,"
          + "\"base_ref\": null,"
          + "\"compare\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration/compare/db12254b2e76...9fc4d28b68c2\","
          + "\"commits\": ["
          + "{"
          + "\"id\": \"9fc4d28b68c20a2e5c064d91b955d9c529b86a15\","
          + "\"tree_id\": \"9c451c8b250b47b25d4df0472da8746b5f1ac98f\","
          + "\"distinct\": true,"
          + "\"message\": \"Add to comment in BuildJob and clearification on last commit: Correct typos, change handling of non-existing project directory, add comments on TestBuildJob.\","
          + "\"timestamp\": \"2019-02-05T17:37:18+01:00\","
          + "\"url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration/commit/9fc4d28b68c20a2e5c064d91b955d9c529b86a15\","
          + "\"author\": {"
          + "\"name\": \"person1\","
          + "\"email\": \"person1@test.com\","
          + "\"username\": \"person1\""
          + "},"
          + "\"committer\": {"
          + "\"name\": \"person1\","
          + "\"email\": \"person1@test.com\","
          + "\"username\": \"person1\""
          + "},"
          + "\"added\": ["
          + ""
          + "],"
          + "\"removed\": ["
          + ""
          + "],"
          + "\"modified\": ["
          + "\"src/main/java/se/kth/dd2480/grp25/ci/BuildJob.java\""
          + "]"
          + "}"
          + "],"
          + "\"head_commit\": {"
          + "\"id\": \"9fc4d28b68c20a2e5c064d91b955d9c529b86a15\","
          + "\"tree_id\": \"9c451c8b250b47b25d4df0472da8746b5f1ac98f\","
          + "\"distinct\": true,"
          + "\"message\": \"Add to comment in BuildJob and clearification on last commit: Correct typos, change handling of non-existing project directory, add comments on TestBuildJob.\","
          + "\"timestamp\": \"2019-02-05T17:37:18+01:00\","
          + "\"url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration/commit/9fc4d28b68c20a2e5c064d91b955d9c529b86a15\","
          + "\"author\": {"
          + "\"name\": \"person1\","
          + "\"email\": \"person1@test.com\","
          + "\"username\": \"person1\""
          + "},"
          + "\"committer\": {"
          + "\"name\": \"person1\","
          + "\"email\": \"person1@test.com\","
          + "\"username\": \"person1\""
          + "},"
          + "\"added\": ["
          + ""
          + "],"
          + "\"removed\": ["
          + ""
          + "],"
          + "\"modified\": ["
          + "\"src/main/java/se/kth/dd2480/grp25/ci/BuildJob.java\""
          + "]"
          + "},"
          + "\"repository\": {"
          + "\"id\": 168540362,"
          + "\"node_id\": \"MDEwOlJlcG9zaXRvcnkxNjg1NDAzNjI=\","
          + "\"name\": \"Continuous-Integration\","
          + "\"full_name\": \"DD2480-Project-group-25/Continuous-Integration\","
          + "\"private\": false,"
          + "\"owner\": {"
          + "\"name\": \"DD2480-Project-group-25\","
          + "\"email\": null,"
          + "\"login\": \"DD2480-Project-group-25\","
          + "\"id\": 46964575,"
          + "\"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjQ2OTY0NTc1\","
          + "\"avatar_url\": \"https://avatars1.githubusercontent.com/u/46964575?v=4\","
          + "\"gravatar_id\": \"\","
          + "\"url\": \"https://api.github.com/users/DD2480-Project-group-25\","
          + "\"html_url\": \"https://github.com/DD2480-Project-group-25\","
          + "\"followers_url\": \"https://api.github.com/users/DD2480-Project-group-25/followers\","
          + "\"following_url\": \"https://api.github.com/users/DD2480-Project-group-25/following{/other_user}\","
          + "\"gists_url\": \"https://api.github.com/users/DD2480-Project-group-25/gists{/gist_id}\","
          + "\"starred_url\": \"https://api.github.com/users/DD2480-Project-group-25/starred{/owner}{/repo}\","
          + "\"subscriptions_url\": \"https://api.github.com/users/DD2480-Project-group-25/subscriptions\","
          + "\"organizations_url\": \"https://api.github.com/users/DD2480-Project-group-25/orgs\","
          + "\"repos_url\": \"https://api.github.com/users/DD2480-Project-group-25/repos\","
          + "\"events_url\": \"https://api.github.com/users/DD2480-Project-group-25/events{/privacy}\","
          + "\"received_events_url\": \"https://api.github.com/users/DD2480-Project-group-25/received_events\","
          + "\"type\": \"Organization\","
          + "\"site_admin\": false"
          + "},"
          + "\"html_url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration\","
          + "\"description\": null,"
          + "\"fork\": false,"
          + "\"url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration\","
          + "\"forks_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/forks\","
          + "\"keys_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/keys{/key_id}\","
          + "\"collaborators_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/collaborators{/collaborator}\","
          + "\"teams_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/teams\","
          + "\"hooks_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/hooks\","
          + "\"issue_events_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/issues/events{/number}\","
          + "\"events_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/events\","
          + "\"assignees_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/assignees{/user}\","
          + "\"branches_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/branches{/branch}\","
          + "\"tags_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/tags\","
          + "\"blobs_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/git/blobs{/sha}\","
          + "\"git_tags_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/git/tags{/sha}\","
          + "\"git_refs_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/git/refs{/sha}\","
          + "\"trees_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/git/trees{/sha}\","
          + "\"statuses_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/statuses/{sha}\","
          + "\"languages_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/languages\","
          + "\"stargazers_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/stargazers\","
          + "\"contributors_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/contributors\","
          + "\"subscribers_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/subscribers\","
          + "\"subscription_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/subscription\","
          + "\"commits_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/commits{/sha}\","
          + "\"git_commits_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/git/commits{/sha}\","
          + "\"comments_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/comments{/number}\","
          + "\"issue_comment_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/issues/comments{/number}\","
          + "\"contents_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/contents/{+path}\","
          + "\"compare_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/compare/{base}...{head}\","
          + "\"merges_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/merges\","
          + "\"archive_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/{archive_format}{/ref}\","
          + "\"downloads_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/downloads\","
          + "\"issues_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/issues{/number}\","
          + "\"pulls_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/pulls{/number}\","
          + "\"milestones_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/milestones{/number}\","
          + "\"notifications_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/notifications{?since,all,participating}\","
          + "\"labels_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/labels{/name}\","
          + "\"releases_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/releases{/id}\","
          + "\"deployments_url\": \"https://api.github.com/repos/DD2480-Project-group-25/Continuous-Integration/deployments\","
          + "\"created_at\": 1548945663,"
          + "\"updated_at\": \"2019-02-05T13:36:41Z\","
          + "\"pushed_at\": 1549384645,"
          + "\"git_url\": \"git://github.com/DD2480-Project-group-25/Continuous-Integration.git\","
          + "\"ssh_url\": \"git@github.com:DD2480-Project-group-25/Continuous-Integration.git\","
          + "\"clone_url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration.git\","
          + "\"svn_url\": \"https://github.com/DD2480-Project-group-25/Continuous-Integration\","
          + "\"homepage\": null,"
          + "\"size\": 110,"
          + "\"stargazers_count\": 0,"
          + "\"watchers_count\": 0,"
          + "\"language\": \"Java\","
          + "\"has_issues\": true,"
          + "\"has_projects\": true,"
          + "\"has_downloads\": true,"
          + "\"has_wiki\": true,"
          + "\"has_pages\": false,"
          + "\"forks_count\": 0,"
          + "\"mirror_url\": null,"
          + "\"archived\": false,"
          + "\"open_issues_count\": 11,"
          + "\"license\": null,"
          + "\"forks\": 0,"
          + "\"open_issues\": 11,"
          + "\"watchers\": 0,"
          + "\"default_branch\": \"master\","
          + "\"stargazers\": 0,"
          + "\"master_branch\": \"master\","
          + "\"organization\": \"DD2480-Project-group-25\""
          + "},"
          + "\"pusher\": {"
          + "\"name\": \"person1\","
          + "\"email\": \"34446119+person1@users.noreply.github.com\""
          + "},"
          + "\"organization\": {"
          + "\"login\": \"DD2480-Project-group-25\","
          + "\"id\": 46964575,"
          + "\"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjQ2OTY0NTc1\","
          + "\"url\": \"https://api.github.com/orgs/DD2480-Project-group-25\","
          + "\"repos_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/repos\","
          + "\"events_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/events\","
          + "\"hooks_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/hooks\","
          + "\"issues_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/issues\","
          + "\"members_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/members{/member}\","
          + "\"public_members_url\": \"https://api.github.com/orgs/DD2480-Project-group-25/public_members{/member}\","
          + "\"avatar_url\": \"https://avatars1.githubusercontent.com/u/46964575?v=4\","
          + "\"description\": null"
          + "},"
          + "\"sender\": {"
          + "\"login\": \"person1\","
          + "\"id\": 34446119,"
          + "\"node_id\": \"MDQ6VXNlcjM0NDQ2MTE5\","
          + "\"avatar_url\": \"https://avatars3.githubusercontent.com/u/34446119?v=4\","
          + "\"gravatar_id\": \"\","
          + "\"url\": \"https://api.github.com/users/person1\","
          + "\"html_url\": \"https://github.com/person1\","
          + "\"followers_url\": \"https://api.github.com/users/person1/followers\","
          + "\"following_url\": \"https://api.github.com/users/person1/following{/other_user}\","
          + "\"gists_url\": \"https://api.github.com/users/person1/gists{/gist_id}\","
          + "\"starred_url\": \"https://api.github.com/users/person1/starred{/owner}{/repo}\","
          + "\"subscriptions_url\": \"https://api.github.com/users/person1/subscriptions\","
          + "\"organizations_url\": \"https://api.github.com/users/person1/orgs\","
          + "\"repos_url\": \"https://api.github.com/users/person1/repos\","
          + "\"events_url\": \"https://api.github.com/users/person1/events{/privacy}\","
          + "\"received_events_url\": \"https://api.github.com/users/person1/received_events\","
          + "\"type\": \"User\","
          + "\"site_admin\": false"
          + "}"
          + "}";

  @Test
  public void testParseCommitID() {
    String[] arg = new String[] {"after"};
    String res = CiServer.parseJsonString(json, arg);
    Assert.assertEquals("9fc4d28b68c20a2e5c064d91b955d9c529b86a15", res);
  }

  @Test
  public void testParseCommitURL() {
    String[] url = new String[] {"repository", "full_name"};
    String res = CiServer.parseJsonString(json, url);
    Assert.assertEquals("DD2480-Project-group-25/Continuous-Integration", res);
  }

  @Test
  public void testParseInvalid() {
    String[] arg = new String[] {"after"};
    String invalidJson = ": \"MDQ6VXNlcjM0NDQ2MTE5\",";
    String res = CiServer.parseJsonString(invalidJson, arg);
    Assert.assertEquals("", res);
  }
}
