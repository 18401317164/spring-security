package com.zjm;

import com.zjm.domain.User;
import com.zjm.mapper.MenuMapper;
import com.zjm.mapper.UserMapper;
import com.zjm.pojo.MapJ;
import com.zjm.utils.JavaScript;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    @Test
    public void userTest() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void test1() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        String encode1 = encoder.encode("123456");
        System.out.println(encode);
        System.out.println(encode1);
        System.out.println(encoder.matches("123456", encode));
        System.out.println(encoder.matches("123456", encode1));
    }
    @Test
    public void testSelectPermsByUserId(){
        List<String> strings = menuMapper.selectPermsByUserId(1L);
        System.out.println(strings );
    }
    @Test
    public void testMath(){
//        String sbt = "(B+D-(A-C)*A)/F";
        String sbt = "A+B+C+D+F";
        List<MapJ> all = new ArrayList<MapJ>();
        all.add(new MapJ("A","2"));
        all.add(new MapJ("B","3"));
        all.add(new MapJ("C","4"));
        all.add(new MapJ("D","5"));
        all.add(new MapJ("F","24"));
        JavaScript js = new JavaScript();
        Double d = js.getMathValue(all, sbt);
        if(d==null){
            System.out.println("无法计算这个表达式");
        }else{
            System.out.println(d);
        }
    }
}
