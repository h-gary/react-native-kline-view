package com.github.fujianlian.klinechart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HTKLineReferenceLine {
    public static final String LABEL_POSITION_LEFT = "left";
    public static final String LABEL_POSITION_RIGHT = "right";

    public String id = "";
    public String key = "";
    public float value = 0f;
    public Integer color = null;
    public Float width = null;
    public float[] dash = null;
    public String label = null;
    public String labelPosition = LABEL_POSITION_RIGHT;
    public Integer labelColor = null;
    public Boolean visible = true;

    public static List<HTKLineReferenceLine> packModelArray(List list) {
        List<HTKLineReferenceLine> modelArray = new ArrayList<>();
        if (list == null) {
            return modelArray;
        }
        for (Object object : list) {
            if (!(object instanceof Map)) {
                continue;
            }
            Map map = (Map) object;
            Object valueObject = map.get("price");
            if (valueObject == null) {
                valueObject = map.get("value");
            }
            if (!(valueObject instanceof Number)) {
                continue;
            }
            HTKLineReferenceLine model = new HTKLineReferenceLine();
            model.value = ((Number) valueObject).floatValue();
            Object idObject = map.get("id");
            if (idObject != null) {
                model.id = idObject.toString();
            }
            Object keyObject = map.get("key");
            if (keyObject != null) {
                model.key = keyObject.toString();
            }
            Object colorObject = map.get("color");
            if (colorObject instanceof Number) {
                model.color = ((Number) colorObject).intValue();
            }
            Object widthObject = map.get("width");
            if (widthObject instanceof Number) {
                model.width = ((Number) widthObject).floatValue();
            }
            Object dashObject = map.get("dash");
            if (dashObject instanceof List) {
                List dashList = (List) dashObject;
                if (!dashList.isEmpty()) {
                    float[] dashArray = new float[dashList.size()];
                    boolean valid = true;
                    for (int i = 0; i < dashList.size(); i++) {
                        Object dashValue = dashList.get(i);
                        if (!(dashValue instanceof Number)) {
                            valid = false;
                            break;
                        }
                        dashArray[i] = ((Number) dashValue).floatValue();
                    }
                    if (valid) {
                        model.dash = dashArray;
                    }
                }
            }
            Object labelObject = map.get("label");
            if (labelObject != null) {
                model.label = labelObject.toString();
            }
            Object labelPositionObject = map.get("labelPosition");
            if (labelPositionObject != null) {
                model.labelPosition = labelPositionObject.toString();
            }
            Object labelColorObject = map.get("labelColor");
            if (labelColorObject instanceof Number) {
                model.labelColor = ((Number) labelColorObject).intValue();
            }
            Object visibleObject = map.get("visible");
            if (visibleObject instanceof Boolean) {
                model.visible = (Boolean) visibleObject;
            }
            modelArray.add(model);
        }
        return modelArray;
    }
}
