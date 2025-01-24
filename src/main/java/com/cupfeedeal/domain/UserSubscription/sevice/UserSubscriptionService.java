package com.cupfeedeal.domain.UserSubscription.sevice;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.enumerate.CupcatTypeEnum;
import com.cupfeedeal.domain.Cupcat.repository.CupcatRepository;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.Cupcat.service.CupcatTypeUtilService;
import com.cupfeedeal.domain.Cupcat.service.UserCupcatService;
import com.cupfeedeal.domain.User.dto.response.PaymentHistoryResponseDto;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.domain.User.service.CustomUserDetailService;
import com.cupfeedeal.domain.UserSubscription.dto.request.UserSubscriptionCreateRequestDto;
import com.cupfeedeal.domain.UserSubscription.dto.response.*;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.request.CafeSubscriptionTypeInfoRequestDto;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionInfoResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionListResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import com.cupfeedeal.domain.cafeSubscriptionType.service.CafeSubscriptionTypeService;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final CustomUserDetailService customUserDetailService;
    private final CafeSubscriptionTypeService cafeSubscriptionTypeService;
    private final UserCupcatService userCupcatService;
    private final CupcatRepository cupcatRepository;
    private final UserCupcatRepository userCupcatRepository;
    private final CafeSubscriptionTypeRepository cafeSubscriptionTypeRepository;
    private final CafeService cafeService;
    private final UserRepository userRepository;
    private final CupcatTypeUtilService cupcatTypeUtilService;

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

    /*
    user subscription 생성
     */
    @Transactional
    public void createUserSubscription(CustomUserdetails customUserdetails, UserSubscriptionCreateRequestDto requestDto) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);
        CafeSubscriptionType cafeSubscriptionType = cafeSubscriptionTypeService.findCafeSubscriptionTypeById(requestDto.cafeSubscriptionTypeId());
        Cafe cafe = cafeSubscriptionType.getCafe();

        // 구독 가능 여부 확인
        List<SubscriptionStatus> statuses = Arrays.asList(SubscriptionStatus.VALID, SubscriptionStatus.NOTYET);
        if (userSubscriptionRepository.countByUserAndSubscriptionStatusIsValidOrNotYet(user, statuses) == 3){
            throw new ApplicationException(ExceptionCode.ALREADY_FULL_SUBSCRIPTION);
        } else if (userSubscriptionRepository.existsByUserAndCafeSubscriptionTypeAndStatus(user, cafeSubscriptionType, statuses)) {
            throw new ApplicationException(ExceptionCode.ALREADY_SUBSCRIBED);
        }

        // user level + 1
        user.setUser_level(user.getUser_level() + 1);
        userRepository.save(user);

        // subscription deadline 계산
        LocalDateTime subscriptionDeadline = requestDto.subscriptionStart()
                .plusDays(cafeSubscriptionType.getPeriod())
                .atTime(23, 59, 59);

        // subscription status 계산
        SubscriptionStatus status = SubscriptionStatus.VALID;
        if (LocalDate.now().isBefore(requestDto.subscriptionStart())) {
            status = SubscriptionStatus.NOTYET;
        }

        // user subscription 저장
        final UserSubscription userSubscription = requestDto.toEntity(user, cafeSubscriptionType, subscriptionDeadline, status);
        userSubscriptionRepository.save(userSubscription);

        Integer newCupcatLevel;
        CupcatTypeEnum cupcatType;

        // user cupcat 생성
        // 현재 cupcat 정보 확인
        Optional<UserCupcat> present_userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtDesc(user);

        if (present_userCupcat.isPresent()) {
            UserCupcat currentCupcat = present_userCupcat.get();

            Integer cupcatLevel = currentCupcat.getCupcat().getLevel();
            cupcatType = currentCupcat.getCupcat().getType();

            // level + 1
            newCupcatLevel = cupcatLevel + 1;

            // cupcat level이 이미 5인 경우
            if (cupcatLevel == 6) {
                newCupcatLevel = 1;
                cupcatType = cupcatTypeUtilService.getRandomCupcatType();
            }
        } else {
            newCupcatLevel = 1;
            cupcatType = cupcatTypeUtilService.getRandomCupcatType();
        }

        // new userCupcat 생성
        String cafeName = cafe.getName();

        Cupcat newCupcat = cupcatRepository.findByLevelAndType(newCupcatLevel, cupcatType);
        userCupcatService.createUserCupcat(user, newCupcat, cafeName);
    }

    /*
    현재 구독중인 userSubscription list 조회
     */
    public UserSubscriptionValidListResponseDto getUserSubscriptions(CustomUserdetails customUserdetails) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);

        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findByUserAndSubscriptionStatusIsValid(user);
        List<UserSubscriptionListResponseDto> userSubscriptionList =  userSubscriptions.stream()
                .map(userSubscription -> convertToListResponseDto(userSubscription))
                .toList();

        Integer paw_count = user.getPawCount();

        return UserSubscriptionValidListResponseDto.from(paw_count, userSubscriptionList);
    }

    /*
    userSubscription 정보를 UserSubscriptionListResponseDto로 변환
     */
    public UserSubscriptionListResponseDto convertToListResponseDto(UserSubscription userSubscription) {

        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();

        Cafe cafe = userSubscription.getCafeSubscriptionType().getCafe();
        Integer using_count = userSubscription.getUsingCount();

        Double saved_cups = getSavedCups(cafeSubscriptionType, using_count);
        Integer remaining_days = getRemainingDays(userSubscription);

        return UserSubscriptionListResponseDto.from(userSubscription, cafe, cafeSubscriptionType, saved_cups, remaining_days);
    }

    /*
    남은 구독 사용 가능 일수 계산
     */
    public Integer getRemainingDays(UserSubscription userSubscription) {
        LocalDate currentDateTime = LocalDate.now();
        LocalDate subscriptionDeadline = userSubscription.getSubscriptionDeadline().toLocalDate();

        Integer remaining_days = (int) ChronoUnit.DAYS.between(currentDateTime, subscriptionDeadline);

        return remaining_days;
    }

    /*
    아낀 잔 수 계산
     */
    public Double getSavedCups(CafeSubscriptionType cafeSubscriptionType, Integer using_count) {
        List<Integer> breakDays = cafeSubscriptionType.getBreakDays();

        for (int i = 0; i < breakDays.size(); i++) {
            boolean withinRange = breakDays.get(i) <= using_count
                    && ( i+1 == breakDays.size() || (breakDays.get(i+1) > using_count));

            if (withinRange) {
                return (i+1) / 2.0;
            }
        }

        return 0.0;
    }

    /*
    구독권 사용 (isUsed update)
     */
    @Transactional
    public UserSubscriptionUseResponseDto useSubscription(Long userSubscriptionId) {
        UserSubscription userSubscription = findUserSubscriptionById(userSubscriptionId);
        User user = userSubscription.getUser();

        // 이미 사용했거나, 사용 전 또는 만료된 구독권에 대한 예외처리
        if (userSubscription.getIsUsed() && userSubscription.getSubscriptionStatus() == SubscriptionStatus.VALID) {
            throw new ApplicationException(ExceptionCode.ALREADY_USED_SUBSCRIPTION);
        } else if (userSubscription.getSubscriptionStatus() == SubscriptionStatus.NOTYET) {
            throw new ApplicationException(ExceptionCode.BEFORE_SUBSCRIPTION_START);
        } else if (userSubscription.getSubscriptionStatus() != SubscriptionStatus.VALID) {
            throw new ApplicationException(ExceptionCode.ALREADY_EXPIRED_SUBSCRIPTION);
        }

        // isUsed = true
        userSubscription.setIsUsed(true);

        // usingCount + 1
        userSubscription.setUsingCount(userSubscription.getUsingCount() + 1);

        userSubscriptionRepository.save(userSubscription);

        // is_getting_paw 여부 확인
        Boolean isGettingPaw = isGettingPaw(userSubscription);

        // pawCount + 1
        if (isGettingPaw) {
            user.setPawCount(user.getPawCount() + 1);
            userRepository.save(user);
        }

        return UserSubscriptionUseResponseDto.from(isGettingPaw);
    }

    /*
    is_getting_paw 여부 확인
     */
    public Boolean isGettingPaw(UserSubscription userSubscription) {
        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();

        Boolean isGettingPaw = !cafeSubscriptionType.getBreakDays().isEmpty()
                && cafeSubscriptionType.getBreakDays().get(0).equals(userSubscription.getUsingCount());

        return isGettingPaw;
    }

    /*
    모든 userSubscription list 조회 (만료 포함)
     */
    public List<UserSubscriptionManageListResponseDto> getAllUserSubscriptions(CustomUserdetails customUserdetails) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);
        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findAllByUser(user);

        return userSubscriptions.stream()
                .map(userSubscription -> convertToManageListResponseDto(userSubscription))
                .toList();
    }

    /*
    userSubscription 정보를 UserSubscriptionManageListResponseDto로 변환
     */
    public UserSubscriptionManageListResponseDto convertToManageListResponseDto(UserSubscription userSubscription) {

        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();
        Cafe cafe = userSubscription.getCafeSubscriptionType().getCafe();

        return UserSubscriptionManageListResponseDto.from(userSubscription, cafe, cafeSubscriptionType);
    }

    /*
    cafe의 cafeSubscriptionType info 조회
     */
    public CafeSubscriptionInfoResponseDto getCafeSubscriptionType(CustomUserdetails customUserdetails, CafeSubscriptionTypeInfoRequestDto cafeSubscriptionTypeInfo) {
        Long id = cafeSubscriptionTypeInfo.id();
        Boolean isExtension = cafeSubscriptionTypeInfo.isExtension();

        if (!isExtension) {
            return getCafeSubscriptionTypeWithoutExtension(id);
        } else {
            return getCafeSubscriptionTypeWithExtension(customUserdetails, id);
        }
    }

    /*
    구독중이 아닌 경우 cafeSubscriptionType 조회
     */
    public CafeSubscriptionInfoResponseDto getCafeSubscriptionTypeWithoutExtension(Long cafeId) {
        Cafe cafe = cafeService.findCafeById(cafeId);
        List<CafeSubscriptionType> cafeSubscriptionTypeList = cafeSubscriptionTypeRepository.findAllByCafeId(cafeId);

        // cafeSubscriptionType list를 dto로 변환
        List<CafeSubscriptionListResponseDto> cafeSubscriptionListResponseDtoList = cafeSubscriptionTypeList.stream()
                .map(CafeSubscriptionListResponseDto::from)
                .toList();

        return CafeSubscriptionInfoResponseDto.from(cafe, null, cafeSubscriptionListResponseDtoList);
    }

    /*
    구독중인 경우 cafeSubscriptionType 조회
     */
    public CafeSubscriptionInfoResponseDto getCafeSubscriptionTypeWithExtension(CustomUserdetails customUserdetails, Long userSubscriptionId) {
        UserSubscription userSubscription = findUserSubscriptionById(userSubscriptionId);
        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();
        Cafe cafe = cafeSubscriptionType.getCafe();

        List<CafeSubscriptionType> cafeSubscriptionTypeList = cafeSubscriptionTypeRepository.findAllByCafe(cafe);

        // cafeSubscriptionType list를 dto로 변환
        List<CafeSubscriptionListResponseDto> cafeSubscriptionListResponseDtoList = cafeSubscriptionTypeList.stream()
                .map(CafeSubscriptionListResponseDto::from)
                .toList();

        // 구독권 정보를 dto로 변환
        UserSubscriptionInfoResponseDto userSubscriptionInfo = UserSubscriptionInfoResponseDto.from(userSubscription, cafeSubscriptionType);

        // 인증되지 않은 사용자 로직
        if (customUserdetails == null) {
            return CafeSubscriptionInfoResponseDto.from(cafe, null, cafeSubscriptionListResponseDtoList);
        }

        return CafeSubscriptionInfoResponseDto.from(cafe, userSubscriptionInfo, cafeSubscriptionListResponseDtoList);
    }

    /*
    구독 취소
     */
    @Transactional
    public void cancelSubscription(Long userSubscriptionId) {
        UserSubscription userSubscription = findUserSubscriptionById(userSubscriptionId);
        User user = userSubscription.getUser();
        CafeSubscriptionType cafeSubscriptionType = userSubscription.getCafeSubscriptionType();

        // 상태 취소로 변경
        userSubscription.setSubscriptionStatus(SubscriptionStatus.CANCELED);
        userSubscriptionRepository.save(userSubscription);

        // 해당 구독권으로 발자국이 찍힌 경우 발자국 count - 1
        Boolean deletePaw = !cafeSubscriptionType.getBreakDays().isEmpty()
                && cafeSubscriptionType.getBreakDays().get(0) <= (userSubscription.getUsingCount());

        if (deletePaw) {
            user.setPawCount(user.getPawCount() - 1);
            userRepository.save(user);
        }
    }
}
