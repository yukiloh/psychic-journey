package xyz.murasakichigo.community.dto;


/*用于封装github返回的user数据*/
public class GithubUser {
    private String login;
    private long id;
    private String updated_at;

    @Override
    public String toString() {
        return "GithubUser{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    /* github页面获取的js数据:
    地址:https://api.github.com/user?access_token=f37ffdeea155884091d3ead4d1c4b1e976a967e4&scope=&token_type=bearer


    {
  "login": "yukiloh",
  "id": 54669645,
  "node_id": "MDQ6VXNlcjU0NjY5NjQ1",
  "avatar_url": "https://avatars0.githubusercontent.com/u/54669645?v=4",
  "gravatar_id": "",
  "url": "https://api.github.com/users/yukiloh",
  "html_url": "https://github.com/yukiloh",
  "followers_url": "https://api.github.com/users/yukiloh/followers",
  "following_url": "https://api.github.com/users/yukiloh/following{/other_user}",
  "gists_url": "https://api.github.com/users/yukiloh/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/yukiloh/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/yukiloh/subscriptions",
  "organizations_url": "https://api.github.com/users/yukiloh/orgs",
  "repos_url": "https://api.github.com/users/yukiloh/repos",
  "events_url": "https://api.github.com/users/yukiloh/events{/privacy}",
  "received_events_url": "https://api.github.com/users/yukiloh/received_events",
  "type": "User",
  "site_admin": false,
  "login": null,
  "company": null,
  "blog": "",
  "location": null,
  "email": null,
  "hireable": null,
  "updated_at": null,
  "public_repos": 1,
  "public_gists": 0,
  "followers": 0,
  "following": 0,
  "created_at": "2019-08-29T11:26:52Z",
  "updated_at": "2019-08-29T15:07:21Z"
}*/
}
