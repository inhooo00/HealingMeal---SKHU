package com.example.thehealingmeal.bookmark.service;

import com.example.thehealingmeal.bookmark.domain.Bookmark;
import com.example.thehealingmeal.bookmark.domain.SnackBookmark;
import com.example.thehealingmeal.bookmark.repository.BookmarkRepository;
import com.example.thehealingmeal.bookmark.repository.SnackBookmarkRepository;
import com.example.thehealingmeal.menu.api.dto.MenuResponseDto;
import com.example.thehealingmeal.menu.api.dto.SnackOrTeaResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkManager {
    private final BookmarkRepository bookmarkRepository;
    private final SnackBookmarkRepository snackBookmarkRepository;

    // 아점저 메뉴 즐겨찾기 조회
    public List<MenuResponseDto> menuBookmarkList(Long userId) {
        List<Bookmark> bookmarkList = bookmarkRepository.findByUserId(userId);

        List<MenuResponseDto> menuResponseDtos = new ArrayList<>(); // 반환할 값

        for (Bookmark bookmark : bookmarkList) {
            MenuResponseDto menuResponseDto = MenuResponseDto.createMenu(
                    bookmark.getId(),
                    bookmark.getMain_dish(),
                    bookmark.getImageURL(),
                    bookmark.getRice(),
                    bookmark.getMeals(),
                    bookmark.getSideDishForUserMenu(),
                    bookmark.getKcal(),
                    bookmark.getProtein(),
                    bookmark.getCarbohydrate(),
                    bookmark.getFat()
            );
            menuResponseDtos.add(menuResponseDto);
        }

        return menuResponseDtos;
    }

    // 아점저 메뉴 즐겨찾기 삭제.
    @Transactional
    public void deleteMenuBookmark(Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow();
        bookmarkRepository.delete(bookmark);
    }

    //    // 간식 메뉴 즐겨찾기 조회
    public List<SnackOrTeaResponseDto> snackBookmarkList(Long userId) {
        List<SnackBookmark> snackBookmarkList = snackBookmarkRepository.findByUserId(userId);

        List<SnackOrTeaResponseDto> snackOrTeaResponseDtos = new ArrayList<>(); // 반환할 값


        for (SnackBookmark snackBookmark : snackBookmarkList) {
            SnackOrTeaResponseDto snackOrTeaResponseDto = SnackOrTeaResponseDto.createMenu(
                    snackBookmark.getId(),
                    snackBookmark.getSnack_or_tea(),
                    snackBookmark.getImageUrl(),
                    snackBookmark.getMeals(),
                    snackBookmark.getKcal(),
                    snackBookmark.getProtein(),
                    snackBookmark.getCarbohydrate(),
                    snackBookmark.getFat()
            );
            snackOrTeaResponseDtos.add(snackOrTeaResponseDto);
        }
        return snackOrTeaResponseDtos;
    }
    // 간식 메뉴 즐겨찾기 삭제.
    @Transactional
    public void deleteSnackBookmark(Long snackBookmarkId) {
        SnackBookmark snackBookmark = snackBookmarkRepository.findById(snackBookmarkId).orElseThrow();
        snackBookmarkRepository.delete(snackBookmark);
    }
}
