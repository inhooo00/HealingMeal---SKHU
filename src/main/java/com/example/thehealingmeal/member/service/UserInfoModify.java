package com.example.thehealingmeal.member.service;


import com.example.thehealingmeal.member.domain.User;
import com.example.thehealingmeal.member.dto.PwdChangeDto;
import com.example.thehealingmeal.member.execption.InvalidUserException;
import com.example.thehealingmeal.member.execption.MismatchException;
import com.example.thehealingmeal.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UserInfoModify {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //비밀번호 변경
    @Transactional
    public void changePwd(PwdChangeDto pwdChangeDto, String user_id) {
        User user = userRepository.findById(Long.valueOf(user_id)).orElseThrow(() -> new InvalidUserException("user not found in the user list table."));
        if (passwordEncoder.matches(pwdChangeDto.getNowPwd(), user.getPassword())) {
            userRepository.updateUserPwd(Long.valueOf(user_id), passwordEncoder.encode(pwdChangeDto.getChangePwd()));
        } else {
            throw new MismatchException("the password is mismatch.");
        }
    }

    //임시 비밀번호 발행
    protected String generateTemPwd(){

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, 8)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }
}
