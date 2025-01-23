package com.cupfeedeal.domain.UserCafeLike.dto.response;

import com.cupfeedeal.domain.UserCafeLike.entity.UserCafeLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCafeLikeResponseDto {
    private Long cafeId;

    public static UserCafeLikeResponseDto from(UserCafeLike usercafeLike) {
        return UserCafeLikeResponseDto.builder()
                .cafeId(usercafeLike.getCafe().getId())
                .build();
    }

}
