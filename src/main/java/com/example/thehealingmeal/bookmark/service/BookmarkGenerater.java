package com.example.thehealingmeal.bookmark.service;

import com.example.thehealingmeal.bookmark.domain.Bookmark;
import com.example.thehealingmeal.bookmark.domain.SnackBookmark;
import com.example.thehealingmeal.bookmark.dto.BookmarkRequestDto;
import com.example.thehealingmeal.bookmark.repository.BookmarkRepository;
import com.example.thehealingmeal.bookmark.repository.SnackBookmarkRepository;
import com.example.thehealingmeal.member.domain.User;
import com.example.thehealingmeal.member.repository.UserRepository;
import com.example.thehealingmeal.menu.domain.MenuForUser;
import com.example.thehealingmeal.menu.domain.SideDishForUserMenu;
import com.example.thehealingmeal.menu.domain.SnackOrTea;
import com.example.thehealingmeal.menu.domain.repository.MenuRepository;
import com.example.thehealingmeal.menu.domain.repository.SideDishForUserMenuRepository;
import com.example.thehealingmeal.menu.domain.repository.SnackOrTeaMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkGenerater {
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final BookmarkRepository bookmarkRepository;
    private final SideDishForUserMenuRepository sideDishForUserMenuRepository;
    private final SnackOrTeaMenuRepository snackOrTeaMenuRepository;
    private final SnackBookmarkRepository snackBookmarkRepository;

    @Value("${bucket-name}")
    private String bucket_name;

    // 아점저 메뉴 즐겨찾기 저장
    @Transactional
    public void createMenuBookmark(Long userId, BookmarkRequestDto bookmarkRequestDto) {
        User user = userRepository.findById(userId).orElseThrow();
        MenuForUser menuForUser = menuRepository.findByUserAndMeals(user, bookmarkRequestDto.getMeals());
        List<SideDishForUserMenu> sideMenus = sideDishForUserMenuRepository.findAllByMenuForUser_Id(menuForUser.getId());
        List<String> sideDishNames = sideMenus.stream()
                .map(SideDishForUserMenu::getSide_dish)
                .toList();

        if (menuForUser == null) {
            throw new IllegalArgumentException("MenuForUser not found for the given user and meals");
        }

        // 이미 존재하는 menuForUserId인지 확인
        Bookmark existingBookmark = bookmarkRepository.
            findDuplicateValues
                    ( menuForUser.getMain_dish()
                            ,menuForUser.getRice()
                            ,menuForUser.getMeals()
                            ,user)
                .orElseThrow();
        if (existingBookmark != null) {
            throw new IllegalArgumentException("Bookmark already exists for the given user and menuForUserId");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .main_dish(menuForUser.getMain_dish())
                .imageURL("https://storage.googleapis.com/" + bucket_name + "/" + menuForUser.getMain_dish() + ".jpg")
                .rice(menuForUser.getRice())
                .meals(menuForUser.getMeals())
                .sideDishForUserMenu(sideDishNames)
                .kcal(menuForUser.getKcal())
                .protein(menuForUser.getProtein())
                .carbohydrate(menuForUser.getCarbohydrate())
                .fat(menuForUser.getFat())
                .build();

        bookmarkRepository.save(bookmark);
    }

     //간식 메뉴 즐겨찾기 저장.
    @Transactional
    public void createSnackBookmark(Long userId, BookmarkRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow();
        SnackOrTea snackOrTea = snackOrTeaMenuRepository.findByUserAndMeals(user, requestDto.getMeals());
        if (snackOrTea == null) {
            throw new IllegalArgumentException("SnackOrTea not found for the given user and meals");
        }

        // 이미 존재하는 snackOrTeaId인지 확인
        SnackBookmark existingSnackBookmark = snackBookmarkRepository.findDuplicateValues
                (snackOrTea.getSnack_or_tea(), snackOrTea.getMeals())
                .orElseThrow();
        if (existingSnackBookmark != null) {
            throw new IllegalArgumentException("SnackBookmark already exists for the given user and snackOrTeaId");
        }

        SnackBookmark snackBookmark = SnackBookmark.builder()
                .user(user)
                .snack_or_tea(snackOrTea.getSnack_or_tea())
                .imageUrl("https://storage.googleapis.com/" + bucket_name + "/" + snackOrTea.getImageUrl())
                .meals(snackOrTea.getMeals())
                .kcal(snackOrTea.getKcal())
                .protein(snackOrTea.getProtein())
                .carbohydrate(snackOrTea.getCarbohydrate())
                .fat(snackOrTea.getFat())
                .build();

        snackBookmarkRepository.save(snackBookmark);
    }
}