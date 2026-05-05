package com.github.fujianlian.klinechart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTKLinePriceLine {
    public String id;
    public float value;
    public int color;
    public float width;
    public float[] dash;
    public String label;
    public String labelPosition;
    public int labelColor;
    public boolean visible = true;

    public static List<HTKLinePriceLine> packModelArray(
            List modelList,
            int defaultColor,
            int defaultLabelColor,
            float defaultWidth
    ) {
        List<HTKLinePriceLine> lines = new ArrayList<>();
        if (modelList == null) {
            return lines;
        }
        for (Object object : modelList) {
            if (!(object instanceof Map)) {
                continue;
            }
            Map<String, Object> map = (Map<String, Object>) object;
            Object rawValue = map.get("price");
            if (!(rawValue instanceof Number)) {
                rawValue = map.get("value");
            }
            if (!(rawValue instanceof Number)) {
                continue;
            }
            HTKLinePriceLine entry = new HTKLinePriceLine();
            Object idObj = map.get("id");
            entry.id = idObj != null ? idObj.toString() : "";
            entry.value = ((Number) rawValue).floatValue();
            Object colorObj = map.get("color");
            entry.color = colorObj instanceof Number ? ((Number) colorObj).intValue() : defaultColor;
            Object widthObj = map.get("width");
            entry.width = widthObj instanceof Number ? ((Number) widthObj).floatValue() : defaultWidth;
            Object dashObj = map.get("dash");
            if (dashObj instanceof List) {
                List dashList = (List) dashObj;
                float[] dash = new float[dashList.size()];
                for (int i = 0; i < dashList.size(); i++) {
                    Object dashValue = dashList.get(i);
                    dash[i] = dashValue instanceof Number ? ((Number) dashValue).floatValue() : 0f;
                }
                entry.dash = dash;
            } else {
                entry.dash = new float[0];
            }
            Object labelObj = map.get("label");
            entry.label = labelObj != null ? labelObj.toString() : "";
            Object labelPositionObj = map.get("labelPosition");
            entry.labelPosition = labelPositionObj != null ? labelPositionObj.toString() : "right";
            Object labelColorObj = map.get("labelColor");
            entry.labelColor = labelColorObj instanceof Number ? ((Number) labelColorObj).intValue() : defaultLabelColor;
            Object visibleObj = map.get("visible");
            entry.visible = !(visibleObj instanceof Boolean) || (Boolean) visibleObj;

            lines.add(entry);
        }
        return lines;
    }
}
