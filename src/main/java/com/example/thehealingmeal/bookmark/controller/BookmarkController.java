package com.example.thehealingmeal.bookmark.controller;

import com.example.thehealingmeal.bookmark.dto.BookmarkRequestDto;
import com.example.thehealingmeal.bookmark.service.BookmarkGenerater;
import com.example.thehealingmeal.bookmark.service.BookmarkManager;
import com.example.thehealingmeal.menu.api.dto.MenuResponseDto;
import com.example.thehealingmeal.menu.api.dto.SnackOrTeaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkGenerater bookmarkGenerater;
    private final BookmarkManager bookmarkManager;

    // 아점저 즐겨찾기 추가
    @PostMapping("/{userId}/bookmark")
    public ResponseEntity<String> bookmark(@PathVariable Long userId, @RequestBody BookmarkRequestDto request) {
        bookmarkGenerater.createMenuBookmark(userId, request);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    // 아점저 즐겨찾기 확인
    @GetMapping("/{userId}/bookmark")
    public ResponseEntity<List<MenuResponseDto>> bookmarkList(@PathVariable Long userId) {
        return new ResponseEntity<>(bookmarkManager.menuBookmarkList(userId), HttpStatus.OK);
    }

    // 아점저 즐겨찾기 삭제
    @DeleteMapping("/{bookmarkId}/bookmark")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long bookmarkId) {
        bookmarkManager.deleteMenuBookmark(bookmarkId);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }

    //간식 즐겨찾기 추가
    @PostMapping("/{userId}/snack/bookmark")
    public ResponseEntity<String> snackBookmark(@PathVariable Long userId, @RequestBody BookmarkRequestDto request) {
        bookmarkGenerater.createSnackBookmark(userId, request);
        return new ResponseEntity<>("성공", HttpStatus.OK);
    }

    // 간식 즐겨찾기 확인
    @GetMapping("/{userId}/snack/bookmark")
    public ResponseEntity<List<SnackOrTeaResponseDto>> snackBookmarkList(@PathVariable Long userId) {
        return new ResponseEntity<>(bookmarkManager.snackBookmarkList(userId), HttpStatus.OK);
    }

    // 간식 즐겨찾기 삭제
    @DeleteMapping("/{snackBookmarkId}/snack/bookmark")
    public ResponseEntity<String> deleteSnackBookmark(@PathVariable Long snackBookmarkId) {
        bookmarkManager.deleteSnackBookmark(snackBookmarkId);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }
}
