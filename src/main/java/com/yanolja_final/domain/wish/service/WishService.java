package com.yanolja_final.domain.wish.service;

import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.domain.wish.entity.Wish;
import com.yanolja_final.domain.wish.exception.DuplicateWishException;
import com.yanolja_final.domain.wish.exception.WishNotFoundException;
import com.yanolja_final.domain.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;


    public Wish createWish(Package aPackage, User user) {
        if (wishRepository.existsByUserAndAPackage(user, aPackage)) {
            throw new DuplicateWishException();
        }
        return wishRepository.save(new Wish(user, aPackage));
    }

    public void deleteWish(Long packageId, Long userId) {
        Wish wish = wishRepository.findByAPackage_IdAndUser_Id(packageId, userId)
            .orElseThrow(() -> new WishNotFoundException());
        wishRepository.delete(wish);
    }

    @Transactional(readOnly = true)
    public Page<Wish> getUserWishes(Long userId, Pageable pageable) {
        return wishRepository.findByUserId(userId, pageable);
    }

    public boolean isWish(User user, Package aPackage) {
        if (user == null) {
            return false;
        }
        return wishRepository.existsByUserAndAPackage(user, aPackage);
    }
}
