package com.accenture.api.utils;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class KusinaNumberFormat {

	public static final DecimalFormat df2 = new DecimalFormat(".##");
	
	public String convertAndRoudUp(String number) {
		return df2.format(Double.parseDouble(number));
	}
}
