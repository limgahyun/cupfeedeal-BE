package com.cupfeedeal.domain.UserSubscription.sevice;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.enumerate.CupcatLevelEnum;
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
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import com.cupfeedeal.domain.cafeSubscriptionType.service.CafeSubscriptionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // new cupcat 생성
        Cupcat newCupcat = cupcatRepository.findByLevelAndType(newCupcatLevel, cupcatType);
        userCupcatService.createUserCupcat(user, newCupcat);
    }
}
