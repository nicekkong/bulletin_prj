package com.cplanet.toring.service;

import com.cplanet.toring.domain.FaqCategory;
import com.cplanet.toring.domain.FaqInfo;
import com.cplanet.toring.dto.response.FaqResultResponse;
import com.cplanet.toring.repository.FaqInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqService {

    final private FaqInfoRepository faqInfoRepository;

    final private ModelMapper modelMapper;

    public FaqService(FaqInfoRepository faqInfoRepository, ModelMapper modelMapper) {
        this.faqInfoRepository = faqInfoRepository;
        this.modelMapper = modelMapper;
    }

    private final static int PAGE_SIZE = 3;

    public List<FaqInfo> getFaqInfo(int page, String category) {

        Page<FaqInfo> faqInfoList;
        if(StringUtils.isEmpty(category)) {
            // 전체 목록
            faqInfoList = faqInfoRepository.findAllFaq(PageRequest.of(page, PAGE_SIZE));
        } else {
            // 특정 카테고리
            faqInfoList = faqInfoRepository.findAllFaqByCategory(category, PageRequest.of(page, PAGE_SIZE));
        }

        List<FaqInfo> faqInfos = faqInfoList.getContent().stream()
                .peek(f -> f.setCategoryName(f.getFaqCategory().getName())) // 특정 필드 변환
                .collect(Collectors.toList());

        FaqResultResponse response = FaqResultResponse.builder()
                .build();



        return faqInfos;
    }

    public List<FaqCategory> classifyCategory(List<FaqInfo> faqInfos) {
        List<FaqCategory> categories = faqInfos.stream()
                .map(FaqInfo::getFaqCategory).distinct()
                .sorted(Comparator.comparing(FaqCategory::getOrdering).reversed())
                .collect(Collectors.toList());

        return categories;
    }
}
