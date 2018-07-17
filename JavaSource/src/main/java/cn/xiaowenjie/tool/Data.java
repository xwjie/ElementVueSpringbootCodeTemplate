package cn.xiaowenjie.tool;

/**
 *  日志里面的文件
 */
public  class Data {
    // 0, 2018-03-29 , 600721 , Buy!, price: 9.54856 , volume: 853
    String date;
    String code;
    boolean buy;
    int price;
    int volume;

    public static Data fromArray(String[] datas) {
        Data d = new Data();

        d.date = datas[1].trim();
        d.code = datas[2].trim();
        d.buy = datas[3].trim().startsWith("Buy");
        d.price = (int) (Float
                .parseFloat(datas[4].trim().substring("price: ".length())) * 100);
        d.volume = Integer.parseInt(datas[5].trim().substring("volume: ".length()))
                * 100;

        return d;
    }
}
