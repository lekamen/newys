package agency.five.assignment.newys.core.exceptions;

public class InvalidArticleIdException extends RuntimeException {

    private Long articleId;

    public InvalidArticleIdException(Long articleId) {
        super();
        this.articleId = articleId;
    }

    public Long getArticleId() {
        return articleId;
    }
}
