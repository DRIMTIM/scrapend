package org.scrapend.rest;

import org.scrapend.service.ScrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by pelupotter on 22/05/17.
 */

@RestController
public class ScrapTestController {

    @Autowired
    private ScrapService scrapService;

    @RequestMapping( method = GET, value= "/scrap/test")
    public String executeTestSpider() {
        return this.scrapService.spiderTestMethod();
    }

}
