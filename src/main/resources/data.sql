INSERT INTO `USERS` (`id`, `name`, `email`, `user_id`, `blog_name`, `profile_intro`, `blog_intro`,
                    `password`)
VALUES (1001, '짱구', 'gu@example.com', 'user_1', '짱구 블로그', null, null,
        '+tqGFYS)83'),
       (1002, '훈', 'hoon@example.com', 'user_2', '훈이 블로그', null, null,
        't5g62TwD!J'),
       (1003, '유리', 'yuri@example.com', 'user_3', '유리 블로그', null, null,
        'q+Z9Wh4G(@'),
       (1004, '맹구', 'maeng@example.com', 'user_4', '맹구 블로그', '안녕하세요', '환영합니다',
        '^y4NQ!qxT#'),
       (1005, '철수', 'chul@example.com', 'user_5', '철수 블로그', '^__^', null,
        '$u03x@Vw0P');

INSERT INTO `SERIES` (`id`, `user_id`, `series_name`)
VALUES (1001, 1001, '동물'),
       (1002, 1002, '공부');

INSERT INTO `POST` (`id`, `user_id`, `series_id`, `title`, `content`, `post_intro`, `is_public`,
                    `post_url`, `post_status`)
VALUES (1001, 1001, 1001, '귀여운 강아지', '강아지는 귀엽다', null, true,
        '/@user_1/귀여운 강아지', 'PUBLISHED'),
       (1002, 1001, null, '맛집 추천 리스트', '여기 맛있습니다','맛집 추천', false,
        '/@user_1/맛집', 'PUBLISHED'),
       (1003, 1002, 1002, '나의 공부 루틴', '스프링 부트에서 이렇게 하면 편해요.', null, true,
        '/@user_2/나의 공부 루틴', 'PUBLISHED'),
       (1004, 1003,null, '귀여운 고양이 이야기', '고양이가 너무 귀엽다!', null, true,
        '/@user_3/귀여운 고양이 이야기', 'PUBLISHED'),
       (1005, 1003, null, '책 스터디 기록', '기록입니다', null, true,
        '/@user_3/책 스터디 기록', 'DRAFT');


INSERT INTO `COMMENT` (`id`, `post_id`, `comment_id`, `user_id`, `content`)
VALUES (1001, 1001, NULL, 1002, '강아지 너무 귀여워요.'),
       (1002, 1003, NULL, 1003, '좋은 정보 감사합니다~'),
       (1003, 1001, 1001, 1003, '정말 공감돼요!');

INSERT INTO `FOLLOW` (`id`, `follower_id`, `followee_id`)
VALUES (1001, 1001, 1002),
       (1002, 1001, 1003),
       (1003, 1001, 1004),
       (1004, 1003, 1004),
       (1005, 1004, 1005);

INSERT INTO `TAG` (`id`, `name`)
VALUES (1001, '일상'),
       (1002, '동물'),
       (1003, '공부');

INSERT INTO `POST_TAG` (`id`, `post_id`, `tag_id`)
VALUES (1001, 1001, 1002),
       (1002, 1003, 1003),
       (1003, 1005, 1003),
       (1004, 1002, 1001);
