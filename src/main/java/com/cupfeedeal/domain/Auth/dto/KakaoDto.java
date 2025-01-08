package com.cupfeedeal.domain.Auth.dto;

import lombok.Getter;

import java.util.Properties;

@Getter
public class KakaoDto {

    public static class OAuthToken{
        private String access_token;
        private String refresh_token;
        private String token_type;
        private int expires_in;
        private String scope;
        private int refresh_expires_in;
    }

    public static class KakaoProfile{
        private Long id;
        private String connected_at;
        private Properties properties;
        private KakaoAccount kakao_account;

        public class Properties{
            private String nickname;
        }

        public class KakaoAccount{
            private String email;
            private Boolean profile_nickname_needs_agreement;
            private Boolean is_email_verified;
            private Boolean has_email;
            private Boolean is_email_valid;
            private Profile profile;

            public class Profile{
                private String nickname;
                private Boolean is_default_nickname;
            }
        }
    }
}
