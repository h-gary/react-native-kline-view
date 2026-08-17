package com.github.fujianlian.klinechart;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.github.fujianlian.klinechart.container.HTKLineContainerView;

import javax.annotation.Nonnull;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

public class RNKLineView extends SimpleViewManager<HTKLineContainerView> {

	public static String onDrawItemDidTouchKey = "onDrawItemDidTouch";

	public static String onDrawItemCompleteKey = "onDrawItemComplete";

	public static String onDrawPointCompleteKey = "onDrawPointComplete";

	public static String onEndReachedKey = "onEndReached";

    @Nonnull
    @Override
    public String getName() {
        return "RNKLineView";
    }

    @Nonnull
    @Override
    protected HTKLineContainerView createViewInstance(@Nonnull ThemedReactContext reactContext) {
    	HTKLineContainerView containerView = new HTKLineContainerView(reactContext);
    	return containerView;
    }

	@Override
	public Map getExportedCustomDirectEventTypeConstants() {
		return MapBuilder.of(
				onDrawItemDidTouchKey, MapBuilder.of("registrationName", onDrawItemDidTouchKey),
				onDrawItemCompleteKey, MapBuilder.of("registrationName", onDrawItemCompleteKey),
				onDrawPointCompleteKey, MapBuilder.of("registrationName", onDrawPointCompleteKey),
				onEndReachedKey, MapBuilder.of("registrationName", onEndReachedKey)
		);
	}





    @ReactProp(name = "optionList")
    public void setOptionList(final HTKLineContainerView containerView, String optionList) {
        if (optionList == null) {
            return;
        }
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int disableDecimalFeature = JSON.DEFAULT_PARSER_FEATURE & ~Feature.UseBigDecimal.getMask();
                Map optionMap = (Map)JSON.parse(optionList, disableDecimalFeature);
                containerView.configManager.reloadOptionList(optionMap);
                containerView.post(new Runnable() {
                    @Override
                    public void run() {
                        containerView.reloadConfigManager();
                    }
                });
            }
        }).start();
    }

    @ReactProp(name = "priceLines")
    public void setPriceLines(final HTKLineContainerView containerView, ReadableArray priceLines) {
        if (priceLines == null) {
            containerView.setPriceLines(null);
            return;
        }
        containerView.setPriceLines(parsePriceLines(priceLines));
        containerView.postInvalidate();
    }
    
    @ReactProp(name = "doubleTapToFitAll", defaultBoolean = true)
    public void setDoubleTapToFitAll(final HTKLineContainerView containerView, boolean doubleTapToFitAll) {
        containerView.setDoubleTapToFitAll(doubleTapToFitAll);
    }

    private List<HTKLineReferenceLine> parsePriceLines(ReadableArray priceLines) {
        List<HTKLineReferenceLine> lines = new ArrayList<>();
        for (int i = 0; i < priceLines.size(); i++) {
            ReadableMap map = priceLines.getMap(i);
            if (map == null) {
                continue;
            }
            HTKLineReferenceLine line = new HTKLineReferenceLine();
            if (map.hasKey("id") && !map.isNull("id")) {
                line.id = map.getString("id");
            }
            if (map.hasKey("price") && !map.isNull("price")) {
                line.value = (float) map.getDouble("price");
            } else if (map.hasKey("value") && !map.isNull("value")) {
                line.value = (float) map.getDouble("value");
            } else {
                continue;
            }
            if (map.hasKey("color") && !map.isNull("color")) {
                line.color = map.getInt("color");
            }
            if (map.hasKey("width") && !map.isNull("width")) {
                line.width = (float) map.getDouble("width");
            }
            if (map.hasKey("dash") && !map.isNull("dash")) {
                ReadableArray dashArray = map.getArray("dash");
                if (dashArray != null && dashArray.size() > 0) {
                    float[] dash = new float[dashArray.size()];
                    boolean valid = true;
                    for (int d = 0; d < dashArray.size(); d++) {
                        if (dashArray.getType(d) != ReadableType.Number) {
                            valid = false;
                            break;
                        }
                        dash[d] = (float) dashArray.getDouble(d);
                    }
                    if (valid) {
                        line.dash = dash;
                    }
                }
            }
            if (map.hasKey("label") && !map.isNull("label")) {
                line.label = map.getString("label");
            }
            if (map.hasKey("labelPosition") && !map.isNull("labelPosition")) {
                line.labelPosition = map.getString("labelPosition");
            }
            if (map.hasKey("labelColor") && !map.isNull("labelColor")) {
                line.labelColor = map.getInt("labelColor");
            }
            if (map.hasKey("visible") && !map.isNull("visible")) {
                line.visible = map.getBoolean("visible");
            }
            lines.add(line);
        }
        return lines;
    }



}
