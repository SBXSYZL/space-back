package com.project.demo.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

/**
 * @Auther: Yzl
 * @Date: 2019/11/26 23:15
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtil {
    //    private int id = 1;
//    @Autowired
//    private RedisArticleDAO redisArticleDAO;
//    @Autowired
//    private ArticleDOMapper articleDOMapper;
//
//    @Test
//    public void test() {
//        ArticleDO articleDO = redisArticleDAO.getArticleDO(id);
//        if (articleDO == null) {
//            articleDO = articleDOMapper.selectByPrimaryKey(id);
//            if (articleDO != null) {
//                String result = redisArticleDAO.putArticleDO(articleDO);
//                System.out.println(result);
//                articleDO = redisArticleDAO.getArticleDO(id);
//                System.out.println(articleDO);
//            }
//        }
//    }
    @Autowired
    StringRedisTemplate template;

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void test() {
        template.opsForValue().set("123", "456", Duration.ofSeconds(30));
        Assert.assertEquals("456", template.opsForValue().get("123"));
    }
}
