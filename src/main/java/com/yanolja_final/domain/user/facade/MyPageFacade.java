package com.yanolja_final.domain.user.facade;

import com.yanolja_final.domain.user.dto.request.UpdateMyPageRequest;
import com.yanolja_final.domain.user.dto.request.UpdatePasswordRequest;
import com.yanolja_final.domain.user.dto.response.MyPageResponse;
import com.yanolja_final.domain.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageFacade {

    private final MyPageService myPageService;

    public MyPageResponse updateUserInfo(UpdateMyPageRequest request, Long userId) {
        MyPageResponse response = myPageService.updateUserInfo(request, userId);
        return response;
    }

    public MyPageResponse getUserInfo(Long userId) {
        MyPageResponse response = myPageService.getUserInfo(userId);
        return response;
    }

    public void updatePassword(UpdatePasswordRequest request, Long userId) {
        myPageService.updatePassword(request, userId);
    }
}
