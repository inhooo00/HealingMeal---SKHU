package com.example.thehealingmeal.bookmark.repository;

import com.example.thehealingmeal.menu.domain.Meals;
import com.example.thehealingmeal.bookmark.domain.SnackBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SnackBookmarkRepository extends JpaRepository<SnackBookmark, Long> {
    List<SnackBookmark> findByUserId(Long userId);

    @Query("SELECT s FROM SnackBookmark s WHERE s.snack_or_tea = :snack_or_tea AND s.meals = :meals")
    Optional<SnackBookmark> findDuplicateValues(@Param("snack_or_tea") String snack_or_tea,
                                                @Param("meals") Meals meals);
}
