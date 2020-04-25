package cn.zzuisa;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestData {


    @Ignore
    @Test
    public void fun() throws IOException {

        Connection cn = Jsoup.connect("https://itbbs.pconline.com.cn/diy/f250_3.html").timeout(50000);

        Document dc = cn.get();
        Elements select = dc.select("#JLazyList").get(0).select(".topic-tit");
        Iterator<Element> ir = select.iterator();
        while (ir.hasNext()) {
            Element next = ir.next();

        }


    }

}
