package com.project.questionanswerapp.repository;

import com.project.questionanswerapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Query(value = "SELECT id FROM post  WHERE user_id= :userId ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<Long> findLastPostsById(@Param("userId") Long userId);
}
