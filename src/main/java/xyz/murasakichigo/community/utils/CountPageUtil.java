package xyz.murasakichigo.community.utils;

public class CountPageUtil {

    /*用于计算分页的方法*/
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
        return new int[]{maxPage,thisPage,queryPage};

    }
}
