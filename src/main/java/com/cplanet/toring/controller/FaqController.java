package com.cplanet.toring.controller;

import com.cplanet.toring.domain.FaqCategory;
import com.cplanet.toring.domain.FaqInfo;
import com.cplanet.toring.service.FaqService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api")
public class FaqController extends BaseController {

    final private FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping(value = "/faq")
    public ResponseEntity faqInfo(@RequestParam(name = "page", defaultValue = "0")int page,
                                  @RequestParam(name = "category", required = false)String category) {

        Map<String, Object> result = new HashMap<>();
        List<FaqInfo> faqInfoList = faqService.getFaqInfo(page, category);

        List<FaqCategory> categories = faqService.classifyCategory(faqInfoList);

        result.put("faqInfoList", faqInfoList);
        result.put("categories", categories);

        return new ResponseEntity(result, HttpStatus.OK);


    }
}
