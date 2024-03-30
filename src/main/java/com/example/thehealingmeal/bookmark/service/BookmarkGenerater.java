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
import java.util.Optional;

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

        // 이미 동일한 메인 요리와 밥, 식사 유형을 가진 즐겨찾기가 있는지 확인
        Optional<Bookmark> duplicateBookmark = bookmarkRepository.findDuplicateValues(
                menuForUser.getMain_dish(),
                menuForUser.getRice(),
                menuForUser.getMeals(),
                user
        );

        if (duplicateBookmark.isPresent()) {
            // 중복된 즐겨찾기가 존재하는 경우 예외를 발생시킵니다.
            throw new IllegalStateException("같은 이름의 즐겨찾기가 존재합니다.");
        }

        List<SideDishForUserMenu> sideMenus = sideDishForUserMenuRepository.findAllByMenuForUser_Id(menuForUser.getId());
        List<String> sideDishNames = sideMenus.stream()
                .map(SideDishForUserMenu::getSide_dish)
                .toList();

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
        // 이미 동일한 간식을 가진 즐겨찾기가 있는지 확인
        Optional<SnackBookmark> duplicateBookmark = snackBookmarkRepository.findDuplicateValues(
                snackOrTea.getSnack_or_tea(),
                snackOrTea.getMeals()
        );

        if (duplicateBookmark.isPresent()) {
            // 중복된 즐겨찾기가 존재하는 경우 예외를 발생시킵니다.
            throw new IllegalStateException("같은 이름의 즐겨찾기가 존재합니다.");
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