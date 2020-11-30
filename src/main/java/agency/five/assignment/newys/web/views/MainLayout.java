package agency.five.assignment.newys.web.views;

import agency.five.assignment.newys.core.security.SecurityUtils;
import agency.five.assignment.newys.web.utils.MsgUtil;
import agency.five.assignment.newys.web.views.admin.ReviewAuthorRequestsView;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends VerticalLayout implements RouterLayout {

    private final Tabs tabs;

    public MainLayout() {
        tabs = new Tabs();
        tabs.add(new Tab(new RouterLink(MsgUtil.get("menu.home.link"), HomeView.class)));

        if (SecurityUtils.currentUserCanEditArticle()) {
            tabs.add(new Tab(new RouterLink(MsgUtil.get("menu.createArticle.link"), EditArticleView.class)));
        }

        if (SecurityUtils.currentUserCanApproveAuthorRequests()) {
            tabs.add(new Tab(new RouterLink(MsgUtil.get("menu.approveAuthorRequests.link"), ReviewAuthorRequestsView.class)));
        }

        if (SecurityUtils.currentUserCanRequestForAuthor()) {
            tabs.add(new Tab(new RouterLink(MsgUtil.get("menu.requestForAuthor.link"), RequestForAuthorView.class)));
        }

        HorizontalLayout header = new HorizontalLayout();
        header.addAndExpand(tabs);
        header.add(new Label(MsgUtil.get("menu.loggedInAs", SecurityUtils.getCurrentUser().getUsername())));
        header.add(new Anchor("logout", "Log out"));

        add(header);
    }
}