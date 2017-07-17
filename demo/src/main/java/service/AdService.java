package service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import model.AdCampaign;




@Component
public class AdService {

	private static List<AdCampaign> Ads = new ArrayList<>();



	public List<AdCampaign> retrieveAllAds() {
		return Ads;
	}

	public AdCampaign retrieveAd(String adId) {
		for (AdCampaign adc : Ads) {
			if (adc.getId().equals(adId)) {
				return adc;
			}
		}
		return null;
	}


	private SecureRandom random = new SecureRandom();

	public AdCampaign addNewAd(AdCampaign adc) {
		

		if (adc == null) {
			return null;
		}

		String randomId = new BigInteger(130, random).toString(32);
		adc.setId(randomId);

		Ads.add(adc);

		return adc;
	}
}
