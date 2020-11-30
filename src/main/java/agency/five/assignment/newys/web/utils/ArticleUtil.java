package agency.five.assignment.newys.web.utils;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.Comment;

import java.nio.charset.StandardCharsets;

public class ArticleUtil {
    private ArticleUtil() {
    }

    public static String getReadableContent(byte[] content) {
        return new String(content, StandardCharsets.UTF_8);
    }

    public static String getReadableContent(Article article) {
        return getReadableContent(article.getContent());
    }

    public static String getReadableContent(Comment comment) {
        return getReadableContent(comment.getContent());
    }
}
