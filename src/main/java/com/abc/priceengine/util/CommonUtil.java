package com.abc.priceengine.util;

public class CommonUtil {
	
	public static Float getUnitPrice(Float cartonPrice, Integer unitQuantityPerCarton) {
		return cartonPrice / unitQuantityPerCarton * 1.3f;
	}
}
