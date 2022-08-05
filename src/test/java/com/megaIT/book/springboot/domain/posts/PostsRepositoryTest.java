package com.megaIT.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After // Junit 에서 단위 테스트가 끝날 때 마다 수행되는 메소드를 지정 (밑의 postLoad 실행될 때 마다 뒤이어 실행됨)
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void postsLoad(){
        String title = "a";
        String content = "b";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("dolphin@gmail.com")
                .build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);

        System.out.println(posts.getTitle());
        System.out.println(title);

        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}