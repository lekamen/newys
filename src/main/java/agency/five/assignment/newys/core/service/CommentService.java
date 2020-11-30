package agency.five.assignment.newys.core.service;

import agency.five.assignment.newys.core.model.Article;
import agency.five.assignment.newys.core.model.Comment;
import agency.five.assignment.newys.core.model.UserAccount;
import agency.five.assignment.newys.core.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean isUsersComment(UserAccount user, Comment comment) {
        return commentRepository.existsByCommentIdAndAuthor(comment.getCommentId(), user);
    }

    public Page<Comment> findByArticle(Article article, Pageable pageable) {
        return commentRepository.findByArticle(article, pageable);
    }

    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
