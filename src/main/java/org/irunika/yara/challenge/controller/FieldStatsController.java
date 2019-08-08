package org.irunika.yara.challenge.controller;

import org.irunika.yara.challenge.util.Constants;
import org.irunika.yara.challenge.model.FieldCondition;
import org.irunika.yara.challenge.service.FieldStatsService;
import org.irunika.yara.challenge.exception.FieldStatsServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldStatsController {

    @Autowired
    @Qualifier(Constants.IN_MEMORY_FIELD_STAT_SERVICE)
    private FieldStatsService fieldStatsService;

    @RequestMapping("/hello")
    public FieldStatsResponse sayHi() {
        return new FieldStatsResponse(fieldStatsService.generateVegetationStats());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hello")
    public void saveFeildStat(@RequestBody FieldCondition fieldCondition) throws FieldStatsServiceException {
        fieldStatsService.saveFieldCondition(fieldCondition);
    }
}
