package xyz.murasakichigo.community.utils;

public class CountPaging {

    public int[] countPaging(Integer questionCount,String page){
        int maxPage = (questionCount/10)+1 ;
        if(Integer.valueOf(page) > maxPage) {
            page = Integer.toString(maxPage);
        }
        else if (Integer.valueOf(page) < 1){
            page = "1";
        }
        Integer thisPage = Integer.valueOf(page);
        int queryPage = (thisPage-1)*10;
        int[] result= {maxPage,thisPage,queryPage};
        return result;

    }
}
