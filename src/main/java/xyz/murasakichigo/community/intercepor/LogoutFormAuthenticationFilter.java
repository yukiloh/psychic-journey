package xyz.murasakichigo.community.intercepor;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/*用于提供给shiro的logoutFilter（重写）*/
public class LogoutFormAuthenticationFilter extends LogoutFilter {

    @Override
    protected boolean preHandle (ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        subject.logout();
        issueRedirect(request, response, "/homepage");

        return false;
    }
}
