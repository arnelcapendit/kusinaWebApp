
package com.accenture.api.utils;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author marlon.naraja
 */
@Component
public class KusinaConstantUtils {

    
    public List< String> getVisualizationList() {

        return Arrays.asList(
                "generationTime",
                "hitsCount",
                "visitorCount",
                "visitsCount",
                "topPageVisits",
                "topCountry",
                "topDevices",
                "topBrowser",
                "visitOvertime");
    }

    public List< String> removeSettingList() {

        return Arrays.asList(
                "access_list",
                "utc_timezone_list",
                "chart_type_list",
                "theme_list");
    }

    public List< String> removeUserPropList() {

        return Arrays.asList(
                "created_date",
                "last_update_date",
                "last_update_by",
                "access_expiration_date");

    }

    public List< String> removePreferencesList() {

        return Arrays.asList(
        		"order",
                "created_date",
                "last_update_date",
                "last_update_by",
                "access_expiration_date");

    }
}
