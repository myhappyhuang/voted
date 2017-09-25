import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需要统计的字段
 * Created by huangjinlong7 on 2017/9/22.
 */
public class GroupField {
    //存放统计的字段与其对应的统计值
    private Map<String, Integer> countMap = new HashMap<String, Integer>();

    public Map<String, Integer> getCountMap() {
        return countMap;
    }

    public void setCountMap(Map<String, Integer> countMap) {
        this.countMap = countMap;
    }

    public void addValue(String value){
        if (value == null || value == "") {
            return;
        }
        if (countMap.get(value) == null) {
            countMap.put(value, 1);
        } else {
            countMap.put(value, countMap.get(value) + 1);
        }
    }
}
