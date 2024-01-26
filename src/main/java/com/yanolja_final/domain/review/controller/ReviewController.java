package com.yanolja_final.domain.review.controller;

import com.yanolja_final.domain.review.dto.request.CreateReviewRequest;
import com.yanolja_final.domain.review.dto.response.ReviewResponse;
import com.yanolja_final.domain.review.dto.response.ReviewSummaryResponse;
import com.yanolja_final.domain.review.facade.ReviewFacade;
import com.yanolja_final.global.config.argumentresolver.LoginedUserId;
import com.yanolja_final.global.util.PagedResponseDTO;
import com.yanolja_final.global.util.ResponseDTO;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFacade reviewFacade;

    @PostMapping("/orders/{orderId}")
    public ResponseDTO<ReviewResponse> createReview(@PathVariable Long orderId,
        @RequestBody @Valid CreateReviewRequest request,
        @LoginedUserId Long userId) {
        ReviewResponse response = reviewFacade.createReview(orderId, request, userId);
        return ResponseDTO.okWithData(response);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseDTO<Void> deleteReview(@PathVariable Long reviewId,@LoginedUserId Long userId) {
        reviewFacade.deleteReview(reviewId,userId);
        return ResponseDTO.ok();
    }

    @GetMapping("/my")
    public ResponseEntity<PagedResponseDTO<ReviewResponse>> getUserReviews(
        @LoginedUserId Long userId,
        @PageableDefault(size = 5) Pageable pageable) {

        Page<ReviewResponse> reviewsPage = reviewFacade.getUserReviews(userId, pageable);
        PagedResponseDTO<ReviewResponse> response = PagedResponseDTO.okWithData(reviewsPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/packages/{packageId}/list/summary")
    public ResponseDTO<ReviewSummaryResponse> getPackageReviewsSummary(@PathVariable Long packageId) {
        ReviewSummaryResponse summary = reviewFacade.getPackageReviewsSummary(packageId);
        return ResponseDTO.okWithData(summary);
    }

    @GetMapping("/packages/{packageId}/list")
    public ResponseEntity<PagedResponseDTO<ReviewResponse>> getPackageReviews(
        @PathVariable Long packageId,
        @PageableDefault(size = 6) Pageable pageable) {

        Page<ReviewResponse> reviewsPage = reviewFacade.getPackageReviews(packageId, pageable);
        PagedResponseDTO<ReviewResponse> response = PagedResponseDTO.okWithData(reviewsPage);

        return ResponseEntity.ok(response);
    }
}
