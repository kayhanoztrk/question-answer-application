package com.project.questionanswerapp;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.entity.Post;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.repository.CommentRepository;
import com.project.questionanswerapp.repository.PostRepository;
import com.project.questionanswerapp.repository.UserRepository;
import com.project.questionanswerapp.security.JwtTokenProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.Date;

//@ComponentScan(basePackages = {"com.project.questionanswerapp.*"})
@SpringBootApplication(scanBasePackages = "com.project.questionanswerapp.*")
public class QuestionanswerappApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public QuestionanswerappApplication(UserRepository userRepository,
                                        PostRepository postRepository,
                                        CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuestionanswerappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        //user.setId(6L);
        user.setUsername("Kayhan");
        user.setPassword("ozturk");

        Post post = new Post();
        //post.setId(19L);
        post.setUser(user);
        post.setTitle("ornekTitle");
        post.setText("ornekText");

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText("commentSampleText");

        userRepository.save(user);
        postRepository.save(post);
        commentRepository.save(comment);
    }
}
