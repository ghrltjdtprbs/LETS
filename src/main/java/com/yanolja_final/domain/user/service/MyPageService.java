package com.yanolja_final.domain.user.service;

import com.yanolja_final.domain.user.dto.request.UpdateMyPageRequest;
import com.yanolja_final.domain.user.dto.response.MyPageResponse;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.user.exception.UserNotFoundException;
import com.yanolja_final.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public MyPageResponse updateUserInfo(UpdateMyPageRequest request, Long userId) {
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());
        existingUser.updateUserInfo(request.phone(), request.addr1(), request.addr2(), request.postCode());
        User user = userRepository.save(existingUser);
        MyPageResponse response = MyPageResponse.from(user);
        return response;
    }
}
