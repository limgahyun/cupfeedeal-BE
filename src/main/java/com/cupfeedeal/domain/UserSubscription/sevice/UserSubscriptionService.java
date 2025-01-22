package com.cupfeedeal.domain.UserSubscription.sevice;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.enumerate.CupcatTypeEnum;
import com.cupfeedeal.domain.Cupcat.repository.CupcatRepository;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.Cupcat.service.UserCupcatService;
import com.cupfeedeal.domain.User.dto.response.PaymentHistoryResponseDto;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.service.CustomUserDetailService;
import com.cupfeedeal.domain.UserSubscription.dto.request.UserSubscriptionCreateRequestDto;
import com.cupfeedeal.domain.UserSubscription.dto.response.UserSubscriptionListResponseDto;
import com.cupfeedeal.domain.UserSubscription.dto.response.UserSubscriptionUseResponseDto;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import com.cupfeedeal.domain.cafeSubscriptionType.service.CafeSubscriptionTypeService;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSubscriptionService {
    @Autowired
    private final UserSubscriptionRepository userSubscriptionRepository;
    @Autowired
    private final CafeSubscriptionTypeRepository cafeSubscriptionTypeRepository;
    @Autowired
    private final CafeRepository cafeRepository;
    @Autowired
    private final CustomUserDetailService customUserDetailService;
    @Autowired
    private final CafeSubscriptionTypeService cafeSubscriptionTypeService;
    @Autowired
    private final UserCupcatService userCupcatService;
    @Autowired
    private final CupcatRepository cupcatRepository;
    @Autowired
    private final UserCupcatRepository userCupcatRepository;

    public UserSubscription findUserSubscriptionById(Long userSubscriptionId) {
        return userSubscriptionRepository.findById(userSubscriptionId)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_USER_SUBSCRIPTION));
    }

    public List<PaymentHistoryResponseDto> getUserPaymentHistory(CustomUserdetails customUserdetails) {

        User user = customUserdetails.getUser();

        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findAllByUser(user);
        List<PaymentHistoryResponseDto> responseList = new ArrayList<>();

        for (UserSubscription subscription : userSubscriptions) {
            CafeSubscriptionType subscriptionType = subscription.getCafeSubscriptionType();

            Cafe cafe = subscriptionType.getCafe();

            PaymentHistoryResponseDto response = PaymentHistoryResponseDto.builder()
                    .cafeName(cafe.getName()) // 카페 이름
                    .subscriptionPrice(subscriptionType.getPrice())
                    .subscriptionPeriod(subscriptionType.getPeriod())
                    .subscriptionStart(subscription.getSubscriptionStart())
                    .subscriptionDeadline(subscription.getSubscriptionDeadline())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }

    @Transactional
    public void createUserSubscription(CustomUserdetails customUserdetails, UserSubscriptionCreateRequestDto requestDto) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);

        CafeSubscriptionType cafeSubscriptionType = cafeSubscriptionTypeService.findCafeSubscriptionTypeById(requestDto.cafeSubscriptionTypeId());
        // user subscription 저장
        final UserSubscription userSubscription = requestDto.toEntity(user, cafeSubscriptionType);
        userSubscriptionRepository.save(userSubscription);

        Integer newCupcatLevel;
        CupcatTypeEnum cupcatType;

        // user cupcat 생성
        // 현재 cupcat 정보 확인
        Optional<UserCupcat> present_userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtAsc(user);

        if (present_userCupcat.isPresent()) {
            UserCupcat currentCupcat = present_userCupcat.get();

            Integer cupcatLevel = currentCupcat.getCupcat().getLevel();
            cupcatType = currentCupcat.getCupcat().getType();

            // level + 1
            newCupcatLevel = cupcatLevel + 1;
        } else {
            newCupcatLevel = 0;
            cupcatType = CupcatTypeEnum.A;
        }

        // new userCupcat 생성
        Cupcat newCupcat = cupcatRepository.findByLevelAndType(newCupcatLevel, cupcatType);
        userCupcatService.createUserCupcat(user, newCupcat);
    }

    public List<UserSubscriptionListResponseDto> getUserSubscriptions(CustomUserdetails customUserdetails) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);

        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findAllByUser(user);
        return userSubscriptions.stream()
                .map(userSubscription -> convertToResponseDto(userSubscription))
                .toList();
    }

    public UserSubscriptionListResponseDto convertToResponseDto(UserSubscription userSubscription) {

        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();
        Cafe cafe = userSubscription.getCafeSubscriptionType().getCafe();
        Double saved_cups = getSavedCups(cafeSubscriptionType);
        Integer remaining_days = getRemainingDays(userSubscription);

        return UserSubscriptionListResponseDto.from(userSubscription, cafe, cafeSubscriptionType, saved_cups, remaining_days);
    }

    public Integer getRemainingDays(UserSubscription userSubscription) {
        LocalDate currentDateTime = LocalDate.now();
        LocalDate subscriptionDeadline = userSubscription.getSubscriptionDeadline().toLocalDate();

        Integer remaining_days = (int) ChronoUnit.DAYS.between(currentDateTime, subscriptionDeadline);

        return remaining_days;
    }

    @Transactional
    public Double getSavedCups(CafeSubscriptionType cafeSubscriptionType) {
        log.info("BreakDays value before check: {}", cafeSubscriptionType.getBreakDays());

        cafeSubscriptionTypeService.setSubscriptionBreakDays(cafeSubscriptionType);

        return 0.5;
    }

    @Transactional
    public UserSubscriptionUseResponseDto useSubscription(Long userSubscriptionId) {
        UserSubscription userSubscription = findUserSubscriptionById(userSubscriptionId);

        // 이미 사용했거나, 만료된 구독권에 대한 예외처리
        if (userSubscription.getIsUsed() && userSubscription.getSubscriptionStatus() == SubscriptionStatus.VALID) {
            throw new ApplicationException(ExceptionCode.ALREADY_USED_SUBSCRIPTION);
        } else if (userSubscription.getSubscriptionStatus() != SubscriptionStatus.VALID) {
            throw new ApplicationException(ExceptionCode.ALREADY_EXPIRED_SUBSCRIPTION);
        }

        // isUsed = true
        userSubscription.setIsUsed(true);

        // usingCount + 1
        userSubscription.setUsingCount(userSubscription.getUsingCount() + 1);

        userSubscriptionRepository.save(userSubscription);

        // is_getting paw 여부 확인 - 수정 필요
        Boolean is_getting_paw = false;

        return UserSubscriptionUseResponseDto.from(is_getting_paw);
    }
}
