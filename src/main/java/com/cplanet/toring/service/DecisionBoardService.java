package com.cplanet.toring.service;

import com.cplanet.toring.domain.DecisionBoard;
import com.cplanet.toring.domain.DecisionChoice;
import com.cplanet.toring.domain.DecisionDupCheck;
import com.cplanet.toring.domain.DecisionReply;
import com.cplanet.toring.domain.enums.ContentsStatus;
import com.cplanet.toring.dto.request.DecisionWriteDto;
import com.cplanet.toring.dto.response.DecisionDetailResponseDto;
import com.cplanet.toring.dto.response.DecisionMainResponseDto;
import com.cplanet.toring.repository.DecisionBoardRepository;
import com.cplanet.toring.repository.DecisionChoiceRepository;
import com.cplanet.toring.repository.DecisionDupCheckRepository;
import com.cplanet.toring.repository.DecisionReplyRepository;
import com.cplanet.toring.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DecisionBoardService {

    final static private Logger logger = LoggerFactory.getLogger(DecisionBoardService.class);

    final private DecisionBoardRepository decisionBoardRepository;
    final private DecisionChoiceRepository decisionChoiceRepository;
    final private DecisionReplyRepository decisionReplyRepository;
    final private MemberService memberService;
    final private DecisionDupCheckRepository decisionDupCheckRepository;

    public DecisionBoardService(DecisionBoardRepository decisionBoardRepository
            , DecisionChoiceRepository decisionChoiceRepository
            , DecisionReplyRepository decisionReplyRepository
            , MemberService memberService
            , DecisionDupCheckRepository decisionDupCheckRepository) {
        this.decisionBoardRepository = decisionBoardRepository;
        this.decisionChoiceRepository = decisionChoiceRepository;
        this.decisionReplyRepository = decisionReplyRepository;
        this.memberService = memberService;
        this.decisionDupCheckRepository = decisionDupCheckRepository;
    }

    // TODO : ?????? ??????????????? ???????????? ?????? ??????
    int PER_PAGE = 3;

    /**
     * ???????????? ???????????? ????????? ?????? ??????
     * @param page
     * @return
     */
    public DecisionMainResponseDto getDecisionBoardMain(int page) {
        Page<DecisionBoard> board = decisionBoardRepository.findDecisionBoardMain(PageRequest.of(page, PER_PAGE));
        logger.debug("Total Size : {}", board.getTotalElements());

        final List<DecisionBoard> content = board.getContent();

        DecisionMainResponseDto responseDto = DecisionMainResponseDto.builder()
                .count(content.size())
                .hasNext(board.hasNext())
                .build();

        List<DecisionMainResponseDto.DecisionMain> mains = new ArrayList<>();
        content.forEach(c -> {
            DecisionMainResponseDto.DecisionMain main = DecisionMainResponseDto.DecisionMain.builder()
                    .id(c.getId())
                    .title(c.getTitle())
                    .build();

            List<String> optionTexts = new ArrayList<>();
            c.getDecisionChoices().forEach(t -> {
                optionTexts.add(t.getOptionText());
            });
            main.setOptionText(optionTexts);

            mains.add(main);
        });
        responseDto.setDecisionMains(mains);

        return responseDto;
    }

    /**
     * ???????????? ????????? ?????? ??????(?????? + ?????? ??????)
     * @param id
     * @return
     */
    public DecisionDetailResponseDto getDecisionBoardDetail(Long id, Long memberId) {

        DecisionBoard board = decisionBoardRepository.findByIdAndAndDisplayStatus_Ok(id).orElse(null);

        Long writerId = board.getMemberId();
        DecisionDetailResponseDto dto = DecisionDetailResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .nickName(memberService.getMemberInfo(writerId).getProfile().getNickname())
                .memberId(writerId)
                .createTime(DateUtils.toHumanizeDateTime(board.getCreateDate()))
                .contents(board.getContents())
                .answer01(board.getDecisionChoices().get(0).getOptionText())
                .answerId01(board.getDecisionChoices().get(0).getId())
                .count01(board.getDecisionChoices().get(0).getCount())
                .answer02(board.getDecisionChoices().get(1).getOptionText())
                .answerId02(board.getDecisionChoices().get(1).getId())
                .count02(board.getDecisionChoices().get(1).getCount())
                .build();

        if(memberId != null && decisionDupCheckRepository.existsByBoardIdAndMemberId(id, memberId)) {
            dto.setIsResponsed(true);
        }

        return dto;
    }


    /**
     * ???????????? ????????? ??? ?????? ??????
     * @param boardId
     * @return
     */
    public List<DecisionReply> getDecisionBoardReplies(Long boardId) {

        List<DecisionReply> replies = decisionReplyRepository.findDecisionRepliesByBoardIdOrderByCreateDate(boardId);
        // ????????? ??????
        replies.forEach(r -> {
            r.setCreated(DateUtils.toHumanizeDateTime(r.getCreateDate()));
        });
        return replies;

    }

    /**
     * ???????????? ????????? ?????? (?????? + ?????? ??????)
     * @param dto
     * @return
     */
    @Transactional
    public DecisionBoard writeDecisionBoard(DecisionWriteDto dto) {
        DecisionBoard savedBoard =  Optional.ofNullable(dto.getDecisionId()).map(d -> {
            // UPDATE
            DecisionBoard board = decisionBoardRepository.findById(dto.getDecisionId()).orElse(null);
            board.setTitle(dto.getTitle());
            board.setContents(dto.getContents());

            for(int i = 0; i< dto.getOptions().size() ; i++) {
                DecisionChoice choice = decisionChoiceRepository.findById(board.getDecisionChoices().get(i).getId()).orElse(null);
                choice.setOptionText(dto.getOptions().get(i));
                board.addDecisionChoice(choice);
            }
            return decisionBoardRepository.save(board);
        }).orElseGet(() -> {
            // INSERT
            DecisionBoard board = DecisionBoard.builder()
                    .memberId(dto.getMemberId())
                    .title(dto.getTitle())
                    .contents(dto.getContents())
                    .build();

            for(int i = 0 ; i < dto.getOptions().size() ; i++) {
                DecisionChoice decisionChoice = DecisionChoice.builder()
                        .optionText(dto.getOptions().get(i))
                        .build();
                board.addDecisionChoice(decisionChoice);
            }
            return decisionBoardRepository.save(board);
        });
        return savedBoard;
    }

    /**
     * ???????????? ?????? ??????/??????
     * @param reply
     * @return
     */
    public DecisionReply writeDecisionReply(DecisionReply reply) {

        DecisionReply savedReply = Optional.ofNullable(reply.getId()).map(r -> {
            // UPDATE
            DecisionReply updatedReply = decisionReplyRepository.findById(r).orElse(null);
            updatedReply.setComment(reply.getComment());
            return decisionReplyRepository.save(updatedReply);
        }).orElseGet(() -> {
            // INSERT
            DecisionReply createdReply = DecisionReply.builder()
                    .comment(reply.getComment())
                    .nickname(reply.getNickname())
                    .memberId(reply.getMemberId())
                    .thumbnail(reply.getThumbnail())
                    .boardId(reply.getBoardId())
                    .build();
            return decisionReplyRepository.save(createdReply);
        });
        return savedReply;
    }

    /**
     * ???????????? ?????? ?????? ?????? (display_status='BLOCK'??? UDPATE ??????.)
     * @param boardId
     * @return
     */
    public DecisionBoard deleteDecisionBoard(Long boardId) {
        // display_status='BLOCK'??? UDPATE ??????.
        DecisionBoard board = decisionBoardRepository.findById(boardId).orElse(null);
        board.setDisplayStatus(ContentsStatus.BLOCK);
        return decisionBoardRepository.save(board);
    }

    /**
     * ???????????? ????????? ?????? ?????? ??????
     * @param replyId
     */
    public void deleteDecisionReply(Long replyId) {
        decisionReplyRepository.delete(decisionReplyRepository.findById(replyId).orElse(null));
    }


    /**
     * ???????????? ?????? ??? ??????, ???????????? UPDATE??????, ?????? ?????? ???????????? ?????? INSERT??? ??????.
     * @param boardId
     * @param memberId
     * @param choiceId
     */
    @Transactional
    public void selectDecisionChoice(Long boardId, Long memberId, Long choiceId) {
        decisionChoiceRepository.updateDecisionChoiceCount(choiceId);
        saveForDupCheck(boardId, memberId, choiceId);
    }

    /**
     * ???????????? ?????? ????????? ?????? ????????????/????????? ??? ?????? ??????
     * @param boardId
     * @param memberId
     * @param choiceId
     */
    private void saveForDupCheck(Long boardId, Long memberId, Long choiceId) {

        DecisionDupCheck dupCheck = DecisionDupCheck.builder()
                .boardId(boardId)
                .memberId(memberId)
                .choiceId(choiceId)
                .build();

        decisionDupCheckRepository.save(dupCheck);


    }
}
