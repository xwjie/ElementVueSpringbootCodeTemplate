package cn.xiaowenjie.chartbeans;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 *  图表数据的一条数据
 */
@Data
public class Entry extends LinkedHashMap<String, Object> {

    /**
     *  X轴数据
     * @param name
     * @param value
     */
    public void addX(String name, String value){
        this.put(name, value);
    }

    /**
     *  Y轴数据
     * @param name
     * @param value
     */
    public void addY(String name, Number value){
        this.put(name, value);
    }
}
