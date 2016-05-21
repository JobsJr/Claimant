package com.example.rajeevkr.wheresmybus.parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by rajeevkr on 5/22/16.
 */

public class PlatformParser {
    private Element mRouteElements = null;

    public void startDocParsing(String response) {
        Document doc = Jsoup.parse(response);
        Elements table = doc.getElementsByTag("table");
        Element row = null;
        for (int i = 0; i < table.size(); i++) {
            row = table.get(i);
            if (row.attr("class").equalsIgnoreCase("routes")) {
                ParseRoutesTable(row);
                break;
            }

        }
    }

    private void ParseRoutesTable(Element row) {
        mRouteElements = row;
        Elements platformElements = mRouteElements.getElementsByTag("tr");
        if (platformElements.size() > 0) {
            Log.d("Rajeev", "rowFound!!!!!");
            Element routeInfoElement = null;
            for (int i = 1; i <= 491; i++) {
                routeInfoElement = platformElements.get(i);
                parsePlatforms(routeInfoElement);
            }
        } else {
            //TODO: Handle error here
        }
    }

    private void parsePlatforms(Element routeInfoElement) {
        Elements busElement = routeInfoElement.getElementsByTag("td");
        Log.d("Rajeev", "id: " + busElement.get(0).text() +
                "Bus: " + busElement.get(1).text() +
                "Routes Addr: " + busElement.get(2).text() +
                "Platform: " + busElement.get(3).text());
    }

}
