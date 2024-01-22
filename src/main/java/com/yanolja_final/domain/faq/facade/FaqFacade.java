package com.yanolja_final.domain.faq.facade;

import com.yanolja_final.domain.faq.dto.request.RegisterFaqRequest;
import com.yanolja_final.domain.faq.dto.response.FaqListResponse;
import com.yanolja_final.domain.faq.dto.response.FaqResponse;
import com.yanolja_final.domain.faq.service.FaqService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqFacade {

    private final FaqService faqService;

    public FaqResponse registerFaq(RegisterFaqRequest request) {
        FaqResponse response = faqService.registerFaq(request);
        return response;
    }

    public List<FaqListResponse> getFaqList() {
        List<FaqListResponse> response = faqService.getFaqList();
        return response;
    }

    public FaqResponse getSpecificFaq(Long faqId) {
        FaqResponse response = faqService.getSpecificFaq(faqId);
        return response;
    }
}
