package xyz.murasakichigo.community;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class TestForNoSpringboot {

    @Test
    public void main(){
        /*测试，json格式分割*/
//        String json = "name=namee&paswd=paswdd&date=2019-09-04";
//        String[] jsonList = json.split("&");
//        for (String j:jsonList) {
//            String s = j.split("=")[1];
//            System.out.println(s);

//        /*队列去3问题*/
//        ArrayList<Integer> originList = new ArrayList<>();
//        /*获取500个元素的列表*/
//        for (int i = 1; i < 501; i++) {
//            originList.add(i);
//        }
//        checkThird(originList);
//        checkThirdV2(originList);


    }

    private ArrayList<Integer> checkThirdV2(ArrayList<Integer> originList) {
        Iterator<Integer> iterator = originList.iterator();
        int i = 1;
        while (iterator.hasNext()){
            iterator.next();
            if ((i%3) == 0) {
                iterator.remove();
            }
            i++;
        }
        if (originList.size()==4){
            System.out.println(originList);
            return originList;
        }else return checkThirdV2(originList);

    }

    private ArrayList<Integer> checkThird(ArrayList<Integer> originList) {
        /*建立一个保留第1.第2位元素的列表 */
        ArrayList<Integer> removedThirdList = new ArrayList<>();
        for (int i = 0; i < originList.size(); i++) {
            /*当位数+1有余，则添加进保留列表*/
            if ((i+1) %3 != 0) {
                removedThirdList.add(originList.get(i));
            }
        }
        /*当保留列表长度位4时，则打印最后的列表内容*/
        if (removedThirdList.size() == 4) {
            System.out.println(removedThirdList);
            return removedThirdList;
        }else {
            /*否，则继续迭代*/
            System.out.println(removedThirdList);
            return checkThird(removedThirdList);
        }
    }


    public static void main(String[] args) {

    }
}
