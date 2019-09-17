package xyz.murasakichigo.community;


import org.junit.Test;

public class TestForNoSpringboot {

    @Test
    public void main(){
        String json = "name=namee&paswd=paswdd&date=2019-09-04";

        String[] jsonList = json.split("&");
        for (String j:jsonList) {
            String s = j.split("=")[1];
            System.out.println(s);

        }
    }
}
