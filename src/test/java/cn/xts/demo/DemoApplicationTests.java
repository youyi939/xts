package cn.xts.demo;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
        String token = "a4b30d11bbc9388a0e6916c3b0ea17a1708cf4de8cf50d02b5ebab3d23ed883a";
    }


    /**
     * 返回username
     * @param claims
     * @return 当前token的username
     */
    public static String getUsernameFromToken(Claims claims){
        return claims.getSubject();
    }

}
