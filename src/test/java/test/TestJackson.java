package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class TestJackson {
    @Test
    public void testJackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestPojo testPojo = new TestPojo();
        testPojo.setStrs(new String[]{"dfsfd", "sdfdsf", "sdfds"});
        testPojo.setArray("sdfdsfds, sdfdsfd, dsfdsfds");
        System.out.println(objectMapper.writeValueAsString(testPojo));
    }

    @Test
    public void testSplit(){
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString().equals(""));
    }
}
