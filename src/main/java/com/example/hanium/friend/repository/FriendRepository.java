package com.example.hanium.friend.repository;



import com.example.hanium.Auth.model.User;
import com.example.hanium.friend.model.Friend;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend,Long> {
    User findByUser(User user);

    @Query("SELECT f from Friend f where f.user.userId=:userId")
    Optional<List<Friend>> findAllByUserId(@Param("userId") Long userId);
}

